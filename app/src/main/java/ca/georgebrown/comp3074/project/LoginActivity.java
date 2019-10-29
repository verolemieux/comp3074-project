package ca.georgebrown.comp3074.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.DatabaseAccess.UserDBAccess;
import ca.georgebrown.comp3074.project.User.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtEmail = findViewById(R.id.txtEmail);
        final EditText txtPassword = findViewById(R.id.txtPassword);
        Button login = findViewById(R.id.btnLogin);
        final TextView txtError = findViewById(R.id.txtError);


        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                UserDBAccess userDB = new UserDBAccess();
                String email = txtEmail.getText().toString();
                String pw = txtPassword.getText().toString();
                if(userDB.ValidateUser(email, pw))
                {
                    //dummy test user
                    User validatedUser = new User(email, "Admin", pw, "January 1st 2020", "123-456-7890");
                    Intent homeIntent = new Intent(v.getContext(), Home.class);
                    homeIntent.putExtra("ValidatedUser", validatedUser);
                    startActivity(homeIntent);
                }
                else
                {
                    txtError.setText("Wrong username and password!");
                }
            }
        });
    }
}

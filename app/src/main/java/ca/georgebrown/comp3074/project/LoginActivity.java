package ca.georgebrown.comp3074.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.DatabaseAccess.UserDBAccess;
import ca.georgebrown.comp3074.project.User.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton about = findViewById(R.id.btnAbout);
        final EditText txtEmail = findViewById(R.id.txtEmail);
        final EditText txtPassword = findViewById(R.id.txtPassword);
        Button login = findViewById(R.id.btnLogin);
        Button register = findViewById(R.id.btnRegister);
        final TextView txtError = findViewById(R.id.txtError);

        about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(v.getContext(), AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

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
                    Intent homeIntent = new Intent(v.getContext(), HomeActivity.class);
                    homeIntent.putExtra("ValidatedUser", validatedUser);
                    startActivity(homeIntent);
                }
                else
                {
                    txtError.setText("Wrong username or password!");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(v.getContext(), RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}

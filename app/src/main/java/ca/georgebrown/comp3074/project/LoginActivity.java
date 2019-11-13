package ca.georgebrown.comp3074.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ca.georgebrown.comp3074.project.DatabaseAccess.ItemsDAO;
import ca.georgebrown.comp3074.project.DatabaseAccess.UserDBAccess;
import ca.georgebrown.comp3074.project.DatabaseAccess.UsersDAO;
import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.User.User;

public class LoginActivity extends AppCompatActivity {

    Context loginContext = this;
    EditText txtEmail;
    EditText txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton about = findViewById(R.id.btnAbout);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        Button login = findViewById(R.id.btnLogin);
        Button register = findViewById(R.id.btnRegister);
        final TextView txtError = findViewById(R.id.txtError);
        final UsersDAO usersDAO = new UsersDAO(this);

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
                if(email.equals("")||pw.equals("")){
                    txtError.setText("Fields cannot be empty");
                }else{
                    User validatedUser = usersDAO.validateUser(email,pw);
                    if(validatedUser == null){
                        txtError.setText("Credentials are invalid");
                    }else{
                        Intent homeIntent = new Intent(v.getContext(), Home.class);
                        homeIntent.putExtra("ValidatedUser", validatedUser);
                        startActivity(homeIntent);
                    }
                }
                /*if(userDB.ValidateUser(email, pw))
                {
                    //dummy test user
                    //User validatedUser = new User(email, "Admin", pw, "January 1st 2020", "123-456-7890");

                    Intent homeIntent = new Intent(v.getContext(), Home.class);
                    homeIntent.putExtra("ValidatedUser", validatedUser);

                    //Adding dummy content to DB
                    //ItemsDAO itemAccess = new ItemsDAO(loginContext);
                    //Item i = new Item(1, "Banana", "A red fruit");
                    //itemAccess.addItem(i, validatedUser);
                    //itemAccess.deleteItem(1, validatedUser);
                    //itemAccess.editItem(i, validatedUser, 1);

                    startActivity(homeIntent);
                }
                else
                {
                    txtError.setText("Wrong username or password!");
                }*/
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

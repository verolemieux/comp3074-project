package ca.georgebrown.comp3074.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< Updated upstream
import ca.georgebrown.comp3074.project.DatabaseAccess.UserDao;
=======
import ca.georgebrown.comp3074.project.DatabaseAccess.UsersDAO;
>>>>>>> Stashed changes

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText txtName = findViewById(R.id.txtName);
        final EditText txtEmail = findViewById(R.id.txtEmail);
        final EditText txtPassword = findViewById(R.id.txtPassword);
        final TextView error_msg = findViewById(R.id.error_msg_registration);
<<<<<<< Updated upstream
        final UserDao userDao = new UserDao(this);
=======
        final UsersDAO userDao = new UsersDAO(this);
>>>>>>> Stashed changes
        Button register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(isEmpty(txtName)){error_msg.setText("Name cannot be empty");}
                else if(isEmpty(txtEmail)){error_msg.setText("Email cannot be empty");}
                else if(isEmpty(txtPassword)){error_msg.setText("Password cannot be empty");}
                else {
                    long id = userDao.addUser(
                            txtEmail.getText().toString(),
                            txtName.getText().toString(),
                            txtPassword.getText().toString()
                    );
                    Toast.makeText(v.getContext(), "User added with id = " + id + ", name: " + txtName.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });
    }
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    public boolean isEmpty(EditText et){
        return et.getText().toString().equals("");
    }
}

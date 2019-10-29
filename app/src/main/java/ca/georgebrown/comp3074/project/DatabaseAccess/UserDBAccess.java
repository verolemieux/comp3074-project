package ca.georgebrown.comp3074.project.DatabaseAccess;

import java.io.Serializable;

public class UserDBAccess implements Serializable {

    //connection string parameters

    public UserDBAccess()
    {
    }

    public boolean ValidateUser(String email, String pw)
    {
        return (email.equals("admin@findyourstuff.com") && pw.equals("password"));
    }
}

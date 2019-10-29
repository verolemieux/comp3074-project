package ca.georgebrown.comp3074.project.DatabaseAccess;

public class UserDBAccess {

    //connection string parameters

    public UserDBAccess()
    {
    }

    public boolean ValidateUser(String email, String pw)
    {
        return (email.equals("admin@findyourstuff.com") && pw.equals("password"));
    }
}

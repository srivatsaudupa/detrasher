package edu.scu.detrasher;

/**
 * Created by Srivatsa on 21-05-2017.
 */

public class User {

    String _user_name;
    String _user_full_name;
    String _user_password;
    int _user_role_no;

    /* Constructor */
    public User()
    {
        // Default
    }
    /* Default constructor */
    /* Overloaded */
    public User(String user_name, String user_full_name, String user_password, int user_role_no)
    {
        this._user_name = user_name;
        this._user_full_name = user_full_name;
        this._user_password = user_password;
        this._user_role_no = user_role_no;
    }
    /* Get and set method */
    public String get_user_name()
    {
        return this._user_name;
    }
    public String get_user_full_name()
    {
        return this._user_full_name;
    }
    public String get_user_password()
    {
        return this._user_password;
    }
    public int get_user_role_no()
    {
        return this._user_role_no;
    }
    public void set_user_name(String value)
    {
        this._user_name = value;
    }
    public void set_user_fullname(String value)
    {
        this._user_full_name = value;
    }
    public void set_user_password(String value)
    {
        this._user_password = value;
    }
    public void set_user_role_no(int value)
    {
        this._user_role_no = value;
    }
}

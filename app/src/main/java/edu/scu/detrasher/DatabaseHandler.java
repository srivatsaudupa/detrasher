package edu.scu.detrasher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Srivatsa on 21-05-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    /* Database meta data */
    /* Database Name and Version */
    private static final String DATABASE_NAME = "Detrasher";
    private static final int DATABASE_VERSION = 1;

    /* Database tables */
    private static final String TABLE_USERS = "dt_users";
    private static final String TABLE_ROLES = "dt_roles";
    private static final String TABLE_LOCATION = "dt_location";
    private static final String TABLE_TASKS = "dt_tasks";

    /* Table Columns */
    private static final String USER_ID ="user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_FULLNAME = "user_fullname";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_ROLE_NO = "user_role_no";
    private static final String USER_ROLE_DESCR = "user_role_descr";

    /* Location table columns */
    private static final String LOCATION_ID ="location_id";
    private static final String LOCATION_NAME ="location_name";
    private static final String LOCATION_FLOOR ="location_floor";
    private static final String LOCATION_TRASH_ID = "location_trash_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Create user table */
        /* Contains User ID, Full Name and Role value */
        String sqlStatement_user = "CREATE TABLE "+TABLE_USERS+" ("+USER_ID+" INTEGER PRIMARY KEY, "+USER_NAME+" TEXT, "+USER_FULLNAME+" TEXT, "+USER_PASSWORD+" TEXT,"+USER_ROLE_NO+" INTEGER)";
        String sqlStatement_roles = "CREATE TABLE "+TABLE_ROLES+" ("+USER_ROLE_NO+" INTEGER PRIMARY KEY, "+USER_ROLE_DESCR+" TEXT)";
        String sqlStatement_location = "CREATE TABLE "+TABLE_LOCATION+" ("+LOCATION_ID+" INTEGER PRIMARY KEY, "+ LOCATION_NAME+" TEXT, "+ LOCATION_FLOOR +" INTEGER, "+LOCATION_TRASH_ID+" INTEGER)";
        db.execSQL(sqlStatement_user);
        db.execSQL(sqlStatement_roles);
        db.execSQL(sqlStatement_location);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROLES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TASKS);
        /* Create tables */
        onCreate(db);
    }

    /* Utility methods */
    /* 1. Create user data */
    public void createUsers()
    {
        User user1 = new User("jack", "Jack Smith", "jack", 1);
        User user2 = new User("john", "John Doe", "john", 2);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues user_values = new ContentValues();
        user_values.put(USER_NAME, user1.get_user_name());
        user_values.put(USER_FULLNAME, user1.get_user_full_name());
        user_values.put(USER_PASSWORD, user1.get_user_password());
        user_values.put(USER_ROLE_NO, user1.get_user_role_no());

        /* Insert data to table */
        db.insert(TABLE_USERS, null, user_values);

        user_values.put(USER_NAME, user2.get_user_name());
        user_values.put(USER_FULLNAME, user2.get_user_full_name());
        user_values.put(USER_PASSWORD, user2.get_user_password());
        user_values.put(USER_ROLE_NO, user2.get_user_role_no());

        /* Insert data to table */
        db.insert(TABLE_USERS, null, user_values);

        /* Close Database */
        db.close();

        /* Create Roles */
        this.createRoles();
    }
    /* 2. Create Role Data */
    public void createRoles()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues role_values = new ContentValues();
        role_values.put(USER_ROLE_NO, 1);
        role_values.put(USER_ROLE_DESCR, "Manager");

        /* Insert Data to table */
        db.insert(TABLE_ROLES, null, role_values);

        role_values.put(USER_ROLE_NO, 2);
        role_values.put(USER_ROLE_DESCR, "Staff");

        /* Insert Data to table */
        db.insert(TABLE_ROLES, null, role_values);

        db.close();
    }

    /* 3. User Authenticate */
    public User AuthenticationController(User user_data)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        /* Create a cursor to read data */
        Cursor userCursor = db.query(TABLE_USERS, new String[] {USER_NAME, USER_FULLNAME, USER_PASSWORD, USER_ROLE_NO}, USER_NAME+"=?", new String[] {user_data.get_user_name()}, null,null,null,null);
        if(userCursor == null)
            return null;
        userCursor.moveToFirst();
        String upass = userCursor.getString(2);
        if(upass.equals(user_data.get_user_password())) {
            user_data.set_user_name(userCursor.getString(0));
            user_data.set_user_fullname(userCursor.getString(1));
            user_data.set_user_role_no(Integer.parseInt(userCursor.getString(3)));
            return user_data;
        }
        else
            return null;
    }

    /* 4. Create Location data */
    public void createLocations()
    {
        Location loc1 = new Location("Learning Commons", 1, 1);
        this.dbLocInsert(loc1);
        Location loc2 = new Location("Learning Commons", 1, 2);
        this.dbLocInsert(loc2);
        Location loc3 = new Location("Learning Commons", 2, 1);
        this.dbLocInsert(loc3);
        Location loc4 = new Location("Engineering", 3, 1);
        this.dbLocInsert(loc4);
        Location loc5 = new Location("Engineering", 3, 2);
        this.dbLocInsert(loc5);
        Location loc6 = new Location("Engineering", 3, 1);
        this.dbLocInsert(loc6);
    }
    /* DB Location insert method */
    public void dbLocInsert(Location location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues locValues = new ContentValues();
        locValues.put(LOCATION_NAME, location.get_location_name());
        locValues.put(LOCATION_FLOOR, location.get_location_floor());
        locValues.put(LOCATION_TRASH_ID, location.get_location_trash_id());
        db.insert(TABLE_LOCATION, null, locValues);
        db.close();
    }
}

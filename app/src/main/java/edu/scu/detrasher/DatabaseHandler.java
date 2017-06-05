package edu.scu.detrasher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    private static final String LOCATION_TRASH_LEVEL = "location_trash_level";

    /* Task table attributes */
    private static final String TASK_ID = "task_id";
    private static final String TASK_STATUS = "task_status";

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
        String sqlStatement_location = "CREATE TABLE "+TABLE_LOCATION+" ("+LOCATION_ID+" INTEGER PRIMARY KEY, "+ LOCATION_NAME+" TEXT, "+ LOCATION_FLOOR +" INTEGER, "+LOCATION_TRASH_ID+" INTEGER, "+LOCATION_TRASH_LEVEL+" INTEGER)";
        String sqlStatement_task = "CREATE TABLE "+TABLE_TASKS+" ("+TASK_ID+" INTEGER PRIMARY KEY, "+ LOCATION_ID+" INTEGER, "+ USER_ID +" INTEGER, "+TASK_STATUS+" TEXT, "+LOCATION_TRASH_LEVEL+" INTEGER)";
        db.execSQL(sqlStatement_user);
        db.execSQL(sqlStatement_roles);
        db.execSQL(sqlStatement_location);
        db.execSQL(sqlStatement_task);

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
        User user1 = new User(1, "jack", "Jack Smith", "jack", 1);
        User user2 = new User(2, "john", "John Doe", "john", 2);
        User user3 = new User(3, "jane", "Jane Mars", "jane", 2);
        User user4 = new User(4, "charles", "Charles Martin", "charles", 1);
        SQLiteDatabase db_user = this.getWritableDatabase();

        ContentValues user_values = new ContentValues();
        user_values.put(USER_ID, user1.get_user_id());
        user_values.put(USER_NAME, user1.get_user_name());
        user_values.put(USER_FULLNAME, user1.get_user_full_name());
        user_values.put(USER_PASSWORD, user1.get_user_password());
        user_values.put(USER_ROLE_NO, user1.get_user_role_no());

        /* Insert data to table */
        db_user.insert(TABLE_USERS, null, user_values);

        user_values.put(USER_ID, user2.get_user_id());
        user_values.put(USER_NAME, user2.get_user_name());
        user_values.put(USER_FULLNAME, user2.get_user_full_name());
        user_values.put(USER_PASSWORD, user2.get_user_password());
        user_values.put(USER_ROLE_NO, user2.get_user_role_no());

        /* Insert data to table */
        db_user.insert(TABLE_USERS, null, user_values);

        user_values.put(USER_ID, user3.get_user_id());
        user_values.put(USER_NAME, user3.get_user_name());
        user_values.put(USER_FULLNAME, user3.get_user_full_name());
        user_values.put(USER_PASSWORD, user3.get_user_password());
        user_values.put(USER_ROLE_NO, user3.get_user_role_no());

        /* Insert data to table */
        db_user.insert(TABLE_USERS, null, user_values);

        user_values.put(USER_ID, user4.get_user_id());
        user_values.put(USER_NAME, user4.get_user_name());
        user_values.put(USER_FULLNAME, user4.get_user_full_name());
        user_values.put(USER_PASSWORD, user4.get_user_password());
        user_values.put(USER_ROLE_NO, user4.get_user_role_no());

        /* Insert data to table */
        db_user.insert(TABLE_USERS, null, user_values);

        db_user.close();

        /* Create Roles */
        this.createRoles();
    }
    /* 2. Create Role Data */
    public void createRoles()
    {
        SQLiteDatabase db_roles = this.getWritableDatabase();

        ContentValues role_values = new ContentValues();
        role_values.put(USER_ROLE_NO, 1);
        role_values.put(USER_ROLE_DESCR, "Manager");

        /* Insert Data to table */
        db_roles.insert(TABLE_ROLES, null, role_values);

        role_values.put(USER_ROLE_NO, 2);
        role_values.put(USER_ROLE_DESCR, "Staff");

        /* Insert Data to table */
        db_roles.insert(TABLE_ROLES, null, role_values);

        db_roles.close();

    }

    /* 3. User Authenticate */
    public User AuthenticationController(User user_data)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        /* Create a cursor to read data */

            Cursor userCursor = db.query(TABLE_USERS, new String[]{USER_ID, USER_NAME, USER_FULLNAME, USER_PASSWORD, USER_ROLE_NO}, USER_NAME + "=?", new String[]{user_data.get_user_name()}, null, null, null, null);
        try {
            if (userCursor == null)
                return null;
            userCursor.moveToFirst();
            String upass = userCursor.getString(3);
            if (upass.equals(user_data.get_user_password())) {
                user_data.set_user_id(Integer.parseInt(userCursor.getString(0)));
                user_data.set_user_name(userCursor.getString(1));
                user_data.set_user_fullname(userCursor.getString(2));
                user_data.set_user_password(userCursor.getString(3));
                user_data.set_user_role_no(Integer.parseInt(userCursor.getString(4)));
                return user_data;
            } else
                return null;
        }
        finally {
            userCursor.close();
        }
    }

    /* 4. Create Location data */
    public void createLocations()
    {
        Location loc1 = new Location("Learning Commons", 1, 1, 6);
        this.dbLocInsert(loc1);
        Location loc2 = new Location("Learning Commons", 1, 2, 10);
        this.dbLocInsert(loc2);
        Location loc3 = new Location("Learning Commons", 2, 1, 15);
        this.dbLocInsert(loc3);
        Location loc4 = new Location("Engineering", 3, 1, 21);
        this.dbLocInsert(loc4);
        Location loc5 = new Location("Engineering", 3, 2, 25);
        this.dbLocInsert(loc5);
        Location loc6 = new Location("Engineering", 2, 1, 30);
        this.dbLocInsert(loc6);
    }
    /* DB Location insert method */
    public void dbLocInsert(Location location)
    {
        SQLiteDatabase db_loc = this.getWritableDatabase();
        ContentValues locValues = new ContentValues();
        locValues.put(LOCATION_NAME, location.get_location_name());
        locValues.put(LOCATION_FLOOR, location.get_location_floor());
        locValues.put(LOCATION_TRASH_ID, location.get_location_trash_id());
        locValues.put(LOCATION_TRASH_LEVEL, location.get_location_trash_level());
        db_loc.insert(TABLE_LOCATION, null, locValues);
        db_loc.close();
    }
    /* Create task */
    public void createTask()
    {
        this.createLocations();
        SQLiteDatabase db_task = this.getWritableDatabase();
        Task task1 = new Task(1, 1, 2, 1, 6);
        Task task2 = new Task(2, 2, 2, 1, 10);
        Task task3 = new Task(3, 3, 3, 1, 15);
        Task task4 = new Task(4, 4, 2, 2, 30);
        Task task5 = new Task(5, 5, 2, 2, 30);
        Task task6 = new Task(6, 6, 3, 2, 30);

        ContentValues taskVals = new ContentValues();
        taskVals.put(TASK_ID, task1.get_task_id());
        taskVals.put(LOCATION_ID, task1.get_task_location_id());
        taskVals.put(USER_ID, task1.get_task_user_id());
        taskVals.put(TASK_STATUS, task1.get_task_completion_status());
        taskVals.put(LOCATION_TRASH_LEVEL, task1.get_task_trash_level());

        db_task.insert(TABLE_TASKS, null, taskVals);

        taskVals.put(TASK_ID, task2.get_task_id());
        taskVals.put(LOCATION_ID, task2.get_task_location_id());
        taskVals.put(USER_ID, task2.get_task_user_id());
        taskVals.put(TASK_STATUS, task2.get_task_completion_status());
        taskVals.put(LOCATION_TRASH_LEVEL, task2.get_task_trash_level());

        db_task.insert(TABLE_TASKS, null, taskVals);

        taskVals.put(TASK_ID, task3.get_task_id());
        taskVals.put(LOCATION_ID, task3.get_task_location_id());
        taskVals.put(USER_ID, task3.get_task_user_id());
        taskVals.put(TASK_STATUS, task3.get_task_completion_status());
        taskVals.put(LOCATION_TRASH_LEVEL, task3.get_task_trash_level());

        db_task.insert(TABLE_TASKS, null, taskVals);

        taskVals.put(TASK_ID, task4.get_task_id());
        taskVals.put(LOCATION_ID, task4.get_task_location_id());
        taskVals.put(USER_ID, task4.get_task_user_id());
        taskVals.put(TASK_STATUS, task4.get_task_completion_status());
        taskVals.put(LOCATION_TRASH_LEVEL, task4.get_task_trash_level());

        db_task.insert(TABLE_TASKS, null, taskVals);

        taskVals.put(TASK_ID, task5.get_task_id());
        taskVals.put(LOCATION_ID, task5.get_task_location_id());
        taskVals.put(USER_ID, task5.get_task_user_id());
        taskVals.put(TASK_STATUS, task5.get_task_completion_status());
        taskVals.put(LOCATION_TRASH_LEVEL, task5.get_task_trash_level());

        db_task.insert(TABLE_TASKS, null, taskVals);

        taskVals.put(TASK_ID, task6.get_task_id());
        taskVals.put(LOCATION_ID, task6.get_task_location_id());
        taskVals.put(USER_ID, task6.get_task_user_id());
        taskVals.put(TASK_STATUS, task6.get_task_completion_status());
        taskVals.put(LOCATION_TRASH_LEVEL, task6.get_task_trash_level());

        db_task.insert(TABLE_TASKS, null, taskVals);

        db_task.close();
    }

    /************************** Data Manipulations *************************/
    /* Method to populate location data */
    public ArrayList<Location> populateLocationData()
    {
        ArrayList<Location> locationData = new ArrayList<Location>();
        SQLiteDatabase db = this.getReadableDatabase();
        /* Create a cursor to read data */
        Cursor locationCursor = db.query(TABLE_LOCATION, new String[] {LOCATION_ID, LOCATION_NAME, LOCATION_FLOOR, LOCATION_TRASH_ID, LOCATION_TRASH_LEVEL}, null, null, null,null,null,null);
        try {
            if (locationCursor == null)
                return null;
            locationCursor.moveToFirst();
            do {
                Location locData = new Location();
                locData.set_location_id(Integer.parseInt(locationCursor.getString(0)));
                locData.set_location_name(locationCursor.getString(1));
                locData.set_location_floor(Integer.parseInt(locationCursor.getString(2)));
                locData.set_location_trash_id(Integer.parseInt(locationCursor.getString(3)));
                locData.set_location_trash_level(Integer.parseInt(locationCursor.getString(4)));
                locationData.add(locData);
            }while (locationCursor.moveToNext());
        }
        finally {
            locationCursor.close();
            db.close();
        }
        return locationData;
    }

    /* Profile settings methods */
    /* Method 1: Fetch user data */
    public User fetchUserData(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        /* Create a cursor to read data */
        Cursor userProfileCursor = db.query(TABLE_USERS, new String[] {USER_ID, USER_NAME, USER_FULLNAME, USER_PASSWORD, USER_ROLE_NO}, USER_ID+"=?", new String[] {userId+""}, null,null,null,null);
        User userData = new User();
        try {
            if (userProfileCursor == null)
                return null;
            userProfileCursor.moveToFirst();
            userData.set_user_id(Integer.parseInt(userProfileCursor.getString(0)));
            userData.set_user_name(userProfileCursor.getString(1));
            userData.set_user_fullname(userProfileCursor.getString(2));
            userData.set_user_password(userProfileCursor.getString(3));
            userData.set_user_role_no(Integer.parseInt(userProfileCursor.getString(4)));
        }
        finally {
            userProfileCursor.close();
            db.close();
        }
        userData = this.fetchRoleData(userData);
        return userData;
    }
    /* Method 2: Update user profile */
    public int updateUserData(User userData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(USER_FULLNAME, userData.get_user_full_name());
        updateValues.put(USER_PASSWORD, userData.get_user_password());
        int update = db.update(TABLE_USERS, updateValues, USER_ID+"="+userData.get_user_id(), null);
        db.close();
        return update;
    }
    public User fetchRoleData(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor userRoleCursor = db.query(TABLE_ROLES, new String[] {USER_ROLE_DESCR}, USER_ROLE_NO+"=?", new String[] {user.get_user_role_no()+""}, null,null,null,null);
        if(userRoleCursor == null)
            return null;
        userRoleCursor.moveToFirst();
        user.set_user_role_descr(userRoleCursor.getString(0));
        db.close();
        return user;
    }

    public ArrayList<Task> fetchAssignedTaskData(int userId, int userRole)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Task> retObj = new ArrayList<Task>();
        String query = "", countQuery ="";
        if(userRole == 1)
        {
            /* Create a cursor to read data */
            query = "SELECT T."+TASK_ID+", T."+LOCATION_ID+", T."+USER_ID+", T."+TASK_STATUS+", T."+LOCATION_TRASH_LEVEL+" FROM "+TABLE_TASKS+" T ORDER BY T."+TASK_STATUS+", T."+LOCATION_ID;
            countQuery = "SELECT Count(T."+TASK_ID+") FROM "+TABLE_TASKS+" T";
        }
        else
        {
            query = "SELECT T."+TASK_ID+", T."+LOCATION_ID+", T."+USER_ID+", T."+TASK_STATUS+", T."+LOCATION_TRASH_LEVEL+" FROM "+TABLE_TASKS+" T WHERE T."+USER_ID+" = "+userId+" ORDER BY T."+TASK_STATUS+", T."+LOCATION_ID;
            countQuery = "SELECT Count(T."+TASK_ID+") FROM "+TABLE_TASKS+" T WHERE T."+USER_ID+" = "+userId;
        }
        Cursor taskCursor = db.rawQuery(query, null);
        try {
            if (taskCursor == null)
                return null;
            if(taskCursor.moveToFirst()) {
                do {
                    Task taskData = new Task();
                    taskData.set_task_id(Integer.parseInt(taskCursor.getString(0)));
                    taskData.set_task_location_id(Integer.parseInt(taskCursor.getString(1)));
                    Cursor locDetails = db.query(TABLE_LOCATION, new String[]{LOCATION_NAME, LOCATION_FLOOR, LOCATION_TRASH_ID}, LOCATION_ID+"=?", new String[]{taskData.get_task_location_id()+""}, null, null, null, null);
                    locDetails.moveToFirst();
                    taskData.set_task_location_desc(locDetails.getString(0) + " - L" +locDetails.getString(1) + " - Trash No. " +locDetails.getString(2) );
                    locDetails.close();
                    taskData.set_task_user_id(Integer.parseInt(taskCursor.getString(2)));
                    Cursor userDetails = db.query(TABLE_USERS, new String[]{USER_FULLNAME}, USER_ID+"=?", new String[]{taskData.get_task_user_id()+""}, null, null, null, null);
                    userDetails.moveToFirst();
                    taskData.set_task_staff_name(userDetails.getString(0));
                    userDetails.close();
                    taskData.set_task_completion_status(Integer.parseInt(taskCursor.getString(3)));
                    taskData.set_task_trash_level(Integer.parseInt(taskCursor.getString(4)));
                    retObj.add(taskData);
                }while (taskCursor.moveToNext());
            }
        }
        finally {
            taskCursor.close();
            db.close();
        }
        return retObj;
    }

    /* Update Task */
    public int updateTask(Task taskObj)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(TASK_STATUS, 2);
        updateValues.put(LOCATION_TRASH_LEVEL, 30);
        int update = db.update(TABLE_TASKS, updateValues, TASK_ID+"="+taskObj.get_task_id(), null);
        if(update!=0) {
            ContentValues locUpdate = new ContentValues();
            locUpdate.put(LOCATION_TRASH_LEVEL, 30);
            update = db.update(TABLE_LOCATION, locUpdate, LOCATION_ID + "=" + taskObj.get_task_location_id(), null);
        }
        db.close();
        return update;
    }
}

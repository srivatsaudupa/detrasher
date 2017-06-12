package edu.scu.detrasher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetrasherTrashManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_trash_manager);
        /* Tool bar handler */
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);
        /* Set default layout */
        /* Textviews */
        final TextView locationIdView = (TextView)this.findViewById(R.id.trash_loc_content);
        final TextView trashIdView = (TextView)this.findViewById(R.id.trash_id_content);
        final TextView trashLevelView = (TextView)this.findViewById(R.id.trash_level_content);
        final ImageView levelImageView = (ImageView) this.findViewById(R.id.trash_level_icon);
        /* fetch User Data */
        Intent thisIntent = getIntent();
        final int userId = thisIntent.getIntExtra("userId", 0);
        final int userRole = thisIntent.getIntExtra("userRole", 0);
        final String location_descr = thisIntent.getStringExtra("loc_descr");
        final int location_id = thisIntent.getIntExtra("location_id", 0);
        final int trash_id = thisIntent.getIntExtra("trash_id", 0);
        final int trash_level = thisIntent.getIntExtra("trash_level", 0);

        locationIdView.setText(location_descr);
        trashIdView.setText(trash_id+"");
        trashLevelView.setText(trash_level+"");

        int imageId = 0;
        if(trash_level <= 6)
        {
            imageId = R.drawable.measure_v_high;
        }
        else if(trash_level > 6 && trash_level <= 12)
        {
            imageId = R.drawable.measure_high;
        }
        else if(trash_level > 12 && trash_level <= 18)
        {
            imageId = R.drawable.measure_medium;
        }
        else if(trash_level > 18 && trash_level <= 24)
        {
            imageId = R.drawable.measure_low;
        }
        else
        {
            imageId = R.drawable.measure_v_low;
        }
        levelImageView.setImageResource(imageId);

        /* Populate drop down list of users */
        final Spinner userDropDown = (Spinner) findViewById(R.id.user_spinner);
        ArrayList<String> userData = this.fetchUserData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, userData);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Button assignTask = (Button)findViewById(R.id.assignTask);
        /* Check if a task has already been assigned to this trash */
        Task checkTask = new Task(1, location_id, 1, 1, trash_level);
        if(isTaskExits(checkTask) == 0)
        {
            assignTask.setEnabled(false);
            assignTask.setText("Task already assigned");
            userDropDown.setEnabled(false);
            userDropDown.setClickable(false);
        }
        userDropDown.setAdapter(dataAdapter);


        assignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = String.valueOf(userDropDown.getSelectedItem());
                int user_id = Integer.parseInt(user.substring(0,1));
                Task newTask = new Task(1, location_id, user_id, 1, trash_level);
                if(createNewTask(newTask) == 0)
                {
                    Toast.makeText(getApplicationContext(), "Server could not update", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Task assigned to "+user, Toast.LENGTH_SHORT).show();
                    assignTask.setEnabled(false);
                    assignTask.setText("Task Assigned");
                    userDropDown.setEnabled(false);
                }
            }
        });

        /* Begin Database sync process */
        dbSync(location_id);
        final Handler syncHandler = new Handler();
        syncHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new DatabaseSyncTask(location_id).execute();
                syncHandler.postDelayed(this, 10000);
            }
        }, 10000);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detrasher_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                // If logout selected

                new AlertDialog.Builder(this)
                        .setTitle("Logging out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent logout = new Intent(DetrasherTrashManagerActivity.this, DetrashLoginActivity.class);
                                logout.putExtra("userId", 1);
                                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK );
                                logout.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(logout);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;

            case R.id.home:
                // If home selected
                Intent thisIntent = getIntent();
                int role = thisIntent.getIntExtra("userRole",0);
                Intent homeIntent;
                if(role == 1) {
                    homeIntent = new Intent(DetrasherTrashManagerActivity.this, DetrasherMainActivity.class);
                }
                else {
                    homeIntent = new Intent(DetrasherTrashManagerActivity.this, DetrasherStaffActivity.class);
                }
                homeIntent.putExtra("userId", thisIntent.getIntExtra("userId",0));
                homeIntent.putExtra("userRole", role);
                startActivity(homeIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    /* Method to fetch user Data */
    public ArrayList<String> fetchUserData()
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        ArrayList<String> userData = dbHandler.fetchStaffData();
        return userData;
    }

    /* Method to update user password */
    public long createNewTask(Task newTask)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        return  dbHandler.createTask(newTask);
    }

    /* Method to check if there is already a tack for this item */
    public long isTaskExits(Task newTask)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        return  dbHandler.isTaskAssigned(newTask);
    }

    public class DatabaseSyncTask extends AsyncTask<Integer, Void, Integer> {
        private int location_id;
        public DatabaseSyncTask(int location_id)
        {
            this.location_id = location_id;
        }
        @Override
        protected Integer doInBackground(Integer... params) {
            DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
            Integer variableValues = dbHandler.fetchLocationData(location_id);
            return variableValues;
        }

        @Override
        protected void onPostExecute(Integer trash_level) {
            // Update your views here
            TextView trashLevelView = (TextView)findViewById(R.id.trash_level_content);
            ImageView levelImageView = (ImageView) findViewById(R.id.trash_level_icon);
            trashLevelView.setText(trash_level+"");
            int imageId = 0;
            if(trash_level <= 6)
            {
                imageId = R.drawable.measure_v_high;
            }
            else if(trash_level > 6 && trash_level <= 12)
            {
                imageId = R.drawable.measure_high;
            }
            else if(trash_level > 12 && trash_level <= 18)
            {
                imageId = R.drawable.measure_medium;
            }
            else if(trash_level > 18 && trash_level <= 24)
            {
                imageId = R.drawable.measure_low;
            }
            else
            {
                imageId = R.drawable.measure_v_low;
            }
            levelImageView.setImageResource(imageId);
            Spinner userDropDown = (Spinner) findViewById(R.id.user_spinner);
            Button assignTask = (Button)findViewById(R.id.assignTask);
            if(trash_level > 18)
            {
                assignTask.setEnabled(false);
                assignTask.setText("No Assignment needed");
                userDropDown.setEnabled(false);
                userDropDown.setClickable(false);
            }
            else {
                Task checkTask = new Task(1, this.location_id, 1, 1, trash_level);
                if(isTaskExits(checkTask) != 0) {
                    assignTask.setEnabled(true);
                    assignTask.setText("Assign Task");
                    userDropDown.setEnabled(true);
                    userDropDown.setClickable(true);
                }
                else {
                    assignTask.setEnabled(false);
                    assignTask.setText("Task already assigned");
                    userDropDown.setEnabled(false);
                    userDropDown.setClickable(false);
                }
            }
        }
    }
    /* Method to sync db */
    public void dbSync(int location_id)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        dbHandler.dbSync();
    }
}

package edu.scu.detrasher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class DetrasherTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_task_view);

        /* Tool bar handler */
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);

        /* Get Session Data */
        Intent thisIntent = getIntent();
        final int userId = thisIntent.getIntExtra("userId", 0);
        final int userRole = thisIntent.getIntExtra("userRole", 0);

        /* To obtain data from DB */
        ArrayList<Task> taskList = fetchTasks(userId, userRole);
        if(taskList.isEmpty())
        {
            Task emptyTask = new Task();
            emptyTask.set_task_location_desc("No tasks assigned");
            emptyTask.set_task_staff_name("All is well");
            emptyTask.set_task_trash_level(0);
            emptyTask.set_task_user_id(userId);
            emptyTask.set_task_id(0);
            emptyTask.set_task_completion_status(1);
            taskList.add(emptyTask);
        }
        TaskListAdapter custAdapter = new TaskListAdapter(taskList, getApplicationContext(), userRole);

        ListView listView = (ListView)findViewById(R.id.taskList);

        listView.setAdapter(custAdapter);

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
                                Intent logout = new Intent(DetrasherTaskActivity.this, DetrashLoginActivity.class);
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
                    homeIntent = new Intent(DetrasherTaskActivity.this, DetrasherMainActivity.class);
                }
                else {
                    homeIntent = new Intent(DetrasherTaskActivity.this, DetrasherStaffActivity.class);
                }
                homeIntent.putExtra("userId", thisIntent.getIntExtra("userId",0));
                homeIntent.putExtra("userRole", role);
                startActivity(homeIntent);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /* Fetch data from DB */
    public ArrayList<Task> fetchTasks(int userId, int userRole)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        return dbHandler.fetchAssignedTaskData(userId, userRole);
    }

    @Override
    public void onBackPressed() {
        int role = getIntent().getIntExtra("userRole", 0);
        Intent taskIntent;
        if(role==1)
        {
             taskIntent = new Intent(this, DetrasherMainActivity.class);
        }
        else {
             taskIntent = new Intent(this, DetrasherStaffActivity.class);
        }
        taskIntent.putExtra("userId", getIntent().getIntExtra("userId", 0));
        taskIntent.putExtra("userRole", role);
        startActivity(taskIntent);
        finish();
    }
}

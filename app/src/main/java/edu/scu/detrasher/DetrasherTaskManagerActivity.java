package edu.scu.detrasher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetrasherTaskManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_task_manager);
        /* Tool bar handler */
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);
        /* Set default layout */
        /* Textviews */
        final TextView locationContentView = (TextView)this.findViewById(R.id.task_loc_content);
        final TextView trashLevelView = (TextView)this.findViewById(R.id.task_trash_level_content);
        final TextView trashAssignedToView = (TextView)this.findViewById(R.id.task_assigned_to_content);
        final ImageView levelImageView = (ImageView) this.findViewById(R.id.trash_level_icon);
        final Button completeTaskBtn = (Button) this.findViewById(R.id.complete_task);
        /* fetch User Data */
        Intent thisIntent = getIntent();
        final int taskId = thisIntent.getIntExtra("task_id", 0);
        final int locationId = thisIntent.getIntExtra("location_id", 0);
        final String location_descr = thisIntent.getStringExtra("loc_descr");
        final String assignedTo = thisIntent.getStringExtra("assigned_to");
        final int trash_level = thisIntent.getIntExtra("trash_level", 0);

        locationContentView.setText(location_descr);
        trashLevelView.setText("Current Trash Level "+trash_level);
        trashAssignedToView.setText("Task assigned to " +assignedTo);

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

        completeTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task taskObj = new Task();
                taskObj.set_task_location_id(locationId);
                taskObj.set_task_id(taskId);
                taskObj.set_task_trash_level(trash_level);
                if(completeTask(taskObj)==0)
                {
                    Toast.makeText(getApplicationContext(), "Server could not update", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Task complete", Toast.LENGTH_SHORT).show();
                    completeTaskBtn.setEnabled(false);
                    completeTaskBtn.setText("Task Completed");
                }
            }
        });
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
                                Intent logout = new Intent(DetrasherTaskManagerActivity.this, DetrashLoginActivity.class);
                                logout.putExtra("userId", 1);
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
                    homeIntent = new Intent(DetrasherTaskManagerActivity.this, DetrasherMainActivity.class);
                }
                else {
                    homeIntent = new Intent(DetrasherTaskManagerActivity.this, DetrasherStaffActivity.class);
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

    /* Complete task */
    public int completeTask(Task taskObj)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        return dbHandler.updateTask(taskObj);
    }

    @Override
    public void onBackPressed() {
        Intent taskIntent = new Intent(this, DetrasherTaskActivity.class);
        taskIntent.putExtra("userId", getIntent().getIntExtra("userId", 0));
        taskIntent.putExtra("userRole", getIntent().getIntExtra("userRole", 0));
        startActivity(taskIntent);
        finish();
    }
}

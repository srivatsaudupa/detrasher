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
import android.widget.ImageView;

public class DetrasherStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_staff);
        /* Tool bar handler */
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);
        /* Recycler Icon */
        ImageView taskManager = (ImageView) findViewById(R.id.taskManager);

        /* Junk manager icon */
        final ImageView settings = (ImageView) findViewById(R.id.settings);

        /* session data */
        Intent thisIntent = getIntent();
        final int userId = thisIntent.getIntExtra("userId", 0);
        final int userRole = thisIntent.getIntExtra("userRole", 0);

        /* Click listeners */
        /* Recycle Bin */
        taskManager.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent taskIntent = new Intent(DetrasherStaffActivity.this, DetrasherTaskActivity.class);
                taskIntent.putExtra("userId", userId);
                taskIntent.putExtra("userRole", userRole);
                startActivity(taskIntent);
            }
        });

        /* Junk Manager */
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(DetrasherStaffActivity.this, DetrasherProfileActivity.class);
                settingsIntent.putExtra("userId", userId);
                settingsIntent.putExtra("userRole", userRole);
                startActivity(settingsIntent);
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
                                Intent logout = new Intent(DetrasherStaffActivity.this, DetrashLoginActivity.class);
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
                    homeIntent = new Intent(DetrasherStaffActivity.this, DetrasherMainActivity.class);
                }
                else {
                    homeIntent = new Intent(DetrasherStaffActivity.this, DetrasherStaffActivity.class);
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
}

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
import android.widget.Toast;

public class DetrasherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_main);
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);

        /* Session data */
        Intent thisIntent = getIntent();
        final int userId = thisIntent.getIntExtra("userId", 0);
        final int userRole = thisIntent.getIntExtra("userRole", 0);
        /* Trash can Icon */
        ImageView trashManager = (ImageView) findViewById(R.id.trashManager);

        /* Solid Waste Icon */
        ImageView staffManager = (ImageView) findViewById(R.id.staffManager);

        /* Recycler Icon */
        ImageView taskManager = (ImageView) findViewById(R.id.taskManager);

        /* Junk manager icon */
        ImageView settings = (ImageView) findViewById(R.id.settings);

        /* Click listeners */
        /* Trash Can */
        trashManager.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent trashManage = new Intent(DetrasherMainActivity.this, DetrasherTrashActivity.class);
                trashManage.putExtra("userId", userId);
                trashManage.putExtra("userRole", userRole);
                startActivity(trashManage);
            }
        });

        /* Solid Waste */
        staffManager.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Staff Manager", Toast.LENGTH_SHORT).show();
            }
        });

        /* Recycle Bin */
        taskManager.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent taskIntent = new Intent(DetrasherMainActivity.this, DetrasherTaskActivity.class);
                taskIntent.putExtra("userId", userId);
                taskIntent.putExtra("userRole", userRole);
                startActivity(taskIntent);
            }
        });

        /* Junk Manager */
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(DetrasherMainActivity.this, DetrasherProfileActivity.class);
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
                                Intent logout = new Intent(DetrasherMainActivity.this, DetrashLoginActivity.class);
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
                    homeIntent = new Intent(DetrasherMainActivity.this, DetrasherMainActivity.class);
                }
                else {
                    homeIntent = new Intent(DetrasherMainActivity.this, DetrasherStaffActivity.class);
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
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logging out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(DetrasherMainActivity.this, DetrashLoginActivity.class);
                        logout.putExtra("userId", 1);
                        startActivity(logout);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

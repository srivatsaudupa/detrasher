package edu.scu.detrasher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
    public User fetchUserData(int userId)
    {
        User userData = new User();
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        userData = dbHandler.fetchUserData(userId);
        return userData;
    }

    /* Method to update user password */
    public int updateProfile(User user)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        return  dbHandler.updateUserData(user);
    }
}

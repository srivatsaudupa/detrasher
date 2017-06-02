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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.toggle;

public class DetrasherProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_profile_settings);
        /* Tool bar handler */
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);
        /* Set default layout */
        /* Textviews */
        final TextView userNameView = (TextView)this.findViewById(R.id.username_content);
        final TextView userFullnameView = (TextView)this.findViewById(R.id.userfullname_content);
        final TextView userRoleView = (TextView)this.findViewById(R.id.userrole_content);

        /* Change Password EditText boxes */
        final EditText oldPasswordET = (EditText)this.findViewById(R.id.userOldPassword);
        final EditText newPasswordET = (EditText)this.findViewById(R.id.userNewPassword);
        final EditText cnfPasswordET = (EditText)this.findViewById(R.id.userCnfPassword);

        /* fetch User Data */
        Intent thisIntent = getIntent();
        final int userId = thisIntent.getIntExtra("userId", 0);
        final User userData = this.fetchUserData(userId);

        /* populate User Data */
        userNameView.setText(userData.get_user_name());
        userFullnameView.setText(userData.get_user_full_name());
        userRoleView.setText(userData.get_user_role_descr());

        /* Buttons */
        final Button changePasswordUtil = (Button)this.findViewById(R.id.changePasswordUtility);
        final Button changePasswordSub = (Button)this.findViewById(R.id.changePasswordSubmit);

        changePasswordUtil.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent changePasswordIntent = new Intent(DetrasherProfileActivity.this, DetrasherProfilePwdActivity.class);
                changePasswordIntent.putExtra("userId", userId);
                startActivity(changePasswordIntent);
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
                                Intent logout = new Intent(DetrasherProfileActivity.this, DetrashLoginActivity.class);
                                startActivity(logout);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;

            case R.id.home:
                // If home selected
                Intent homeIntent = new Intent(DetrasherProfileActivity.this, DetrasherProfileActivity.class);
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

    /* Method to toggle view */
    public void toggleView(boolean toggle)
    {
        View parentView = (View)this.findViewById(R.id.parent_layout);
        int layout = toggle ? R.id.profile_main : R.id.profile_chg_pwd;
        ViewGroup parent = (ViewGroup) parentView.getParent();
        int index =  parent.indexOfChild(parentView);
        parent.removeView(parentView);
        parentView = getLayoutInflater().inflate(layout, parent, false);
        parent.addView(parentView, index);
    }
}

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
import android.widget.EditText;
import android.widget.Toast;

public class DetrasherProfilePwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_profile_settings_pwd);
        /* Tool bar handler */
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
        appToolBar.setTitleTextColor(0xFFFFFFFF);
        /* Set default layout */

        /* Change Password EditText boxes */
        final EditText oldPasswordET = (EditText)this.findViewById(R.id.userOldPassword);
        final EditText newPasswordET = (EditText)this.findViewById(R.id.userNewPassword);
        final EditText cnfPasswordET = (EditText)this.findViewById(R.id.userCnfPassword);

        /* fetch User Data */
        Intent thisIntent = getIntent();
        final int userId = thisIntent.getIntExtra("userId", 0);
        final User userData = this.fetchUserData(userId);

        /* Buttons */
        final Button changePasswordSub = (Button)this.findViewById(R.id.changePasswordSubmit);

        changePasswordSub.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                /* Edit Views */
                String oldPassword = oldPasswordET.getText().toString();
                String newPassword = newPasswordET.getText().toString();
                String cnfPassword = cnfPasswordET.getText().toString();
                /* Validations */
                if(oldPassword.isEmpty())
                {
                    oldPasswordET.setError("Old Password is needed");
                }
                else if(!oldPassword.equals(userData.get_user_password()))
                {
                    oldPasswordET.setError("Wrong Password");
                }
                else if(newPassword.isEmpty())
                {
                    newPasswordET.setError("Enter new Password");
                }
                else if(cnfPassword.isEmpty())
                {
                    cnfPasswordET.setError("Reenter Password");
                }
                else if(!cnfPassword.equals(newPassword))
                {
                    cnfPasswordET.setError("Passwords do not match");
                }
                else
                {
                    userData.set_user_password(newPassword);
                    int update = DetrasherProfilePwdActivity.this.updateProfile(userData);
                    if(update == 0) {
                        Toast.makeText(getApplicationContext(), "Password could not be changed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        Intent backIntent = new Intent(DetrasherProfilePwdActivity.this, DetrasherProfileActivity.class);
                        backIntent.putExtra("userId", userId);
                        startActivity(backIntent);
                        finish();
                    }
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
                                Intent logout = new Intent(DetrasherProfilePwdActivity.this, DetrashLoginActivity.class);
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
                     homeIntent = new Intent(DetrasherProfilePwdActivity.this, DetrasherMainActivity.class);
                }
                else {
                     homeIntent = new Intent(DetrasherProfilePwdActivity.this, DetrasherStaffActivity.class);
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

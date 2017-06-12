package edu.scu.detrasher;

/**
 * Created by Srivatsa on 14-05-2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static edu.scu.detrasher.R.id.userId;

public class DetrashLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detrash_login);
        Toolbar appToolBar = (Toolbar) findViewById(R.id.detrasher_toolbar);
        setSupportActionBar(appToolBar);
        appToolBar.setTitleTextColor(0xFFFFFFFF);
        /* Button click event */
        Button logIn = (Button) findViewById(R.id.loginButton);
        final EditText userIdEditor = (EditText)findViewById(userId);
        final EditText userPasswordEditor = (EditText)findViewById(R.id.userPassword);

        Intent thisIntent = getIntent();
        if(!thisIntent.hasExtra("userId"))
        {
            this.createData();
        }

        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userId = userIdEditor.getText().toString();
                String userPassword = userPasswordEditor.getText().toString();
                if(userId.isEmpty())
                {
                    userIdEditor.setError("Username is needed");
                }
                else if(userPassword.isEmpty())
                {
                    userPasswordEditor.setError("Password is needed");
                }
                else {
                    User returnObj = authentication(userId, userPassword);
                    if (returnObj == null) {
                        Toast.makeText(getApplicationContext(), "Invalid Login!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent lSuccess;
                        Toast.makeText(getApplicationContext(), "Welcome! " + returnObj.get_user_full_name(), Toast.LENGTH_SHORT).show();
                        if (returnObj.get_user_role_no() == 1) {
                            lSuccess = new Intent(DetrashLoginActivity.this, DetrasherMainActivity.class);
                            lSuccess.putExtra("userId", returnObj.get_user_id());
                            lSuccess.putExtra("userRole", returnObj.get_user_role_no());
                        } else {
                            lSuccess = new Intent(DetrashLoginActivity.this, DetrasherStaffActivity.class);
                            lSuccess.putExtra("userId", returnObj.get_user_id());
                            lSuccess.putExtra("userRole", returnObj.get_user_role_no());
                        }
                        startActivity(lSuccess);
                        finish();
                    }
                }
            }
        });
    }
    private User authentication(String userID, String userPassword){
        User user_data = new User(0, userID, "", userPassword, 0);
        DatabaseHandler dbConnector = new DatabaseHandler(getApplicationContext());
        return dbConnector.AuthenticationController(user_data);
    }

    private void createData()
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());


        /* Add data to db created */
        dbHandler.createUsers();
        dbHandler.createLocations();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
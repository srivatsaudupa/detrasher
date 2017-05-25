package edu.scu.detrasher;

/**
 * Created by Srivatsa on 14-05-2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        /* Button click event */
        Button logIn = (Button) findViewById(R.id.loginButton);
        final EditText userIdEditor = (EditText)findViewById(userId);
        final EditText userPasswordEditor = (EditText)findViewById(R.id.userPassword);

        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userId = userIdEditor.getText().toString();
                String userPassword = userPasswordEditor.getText().toString();
                User returnObj = authentication(userId, userPassword);
                if(returnObj == null)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Login!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Welcome! "+returnObj.get_user_full_name(), Toast.LENGTH_SHORT).show();
                    Intent lSuccess = new Intent(DetrashLoginActivity.this, DetrasherMainActivity.class);
                    startActivity(lSuccess);
                    finish();
                }
            }
        });
    }
    private User authentication(String userID, String userPassword){
        User user_data = new User(userID, "", userPassword, 0);
        DatabaseHandler dbConnector = new DatabaseHandler(getApplicationContext());
        dbConnector.createUsers();
        return dbConnector.AuthenticationController(user_data);
    }
}
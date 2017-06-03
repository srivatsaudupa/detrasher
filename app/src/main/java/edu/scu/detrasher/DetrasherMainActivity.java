package edu.scu.detrasher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
                Toast.makeText(getApplicationContext(), "Trash Manager", Toast.LENGTH_SHORT).show();
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
}

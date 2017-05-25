package edu.scu.detrasher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DetrasherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detrasher_main);
        /* Trash can Icon */
        ImageView trashCan = (ImageView) findViewById(R.id.trashCan);

        /* Solid Waste Icon */
        ImageView solidWasteManager = (ImageView) findViewById(R.id.sWasteLocator);

        /* Recycler Icon */
        ImageView recycleBin = (ImageView) findViewById(R.id.recycleBin);

        /* Junk manager icon */
        ImageView junkManager = (ImageView) findViewById(R.id.junkManager);

        /* Click listeners */
        /* Trash Can */
        trashCan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Trash Can Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        /* Solid Waste */
        solidWasteManager.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Solid Waste Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        /* Recycle Bin */
        recycleBin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Recycle Bin Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });

        /* Junk Manager */
        junkManager.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Junk Manager Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

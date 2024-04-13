package com.example.lostandfound;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RemoveItemActivity extends Activity {
    private Button buttonRemove;
    private DatabaseHelper databaseHelper;
    private long advertId; // You need to pass this ID when starting this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_item);

        databaseHelper = new DatabaseHelper(this);
        buttonRemove = findViewById(R.id.buttonRemove);

        // Assuming you passed the advert ID as an extra in the intent
        advertId = getIntent().getLongExtra("ADVERT_ID", -1);

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAdvert();
            }
        });
    }

    private void removeAdvert() {
        if (advertId != -1) {
            boolean deleted = databaseHelper.deleteAdvert(advertId);
            if (deleted) {
                Toast.makeText(this, "Advert removed successfully", Toast.LENGTH_SHORT).show();
                // Finish activity and possibly refresh the list in ShowAllAdsActivity
                finish();
            } else {
                Toast.makeText(this, "Error removing advert", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

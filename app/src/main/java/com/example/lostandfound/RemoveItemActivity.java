package com.example.lostandfound;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveItemActivity extends Activity {

    private Button buttonRemove;
    private DatabaseHelper databaseHelper;
    private long advertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_item);

        TextView textViewType = findViewById(R.id.textViewAdvertType);
        TextView textViewDescription = findViewById(R.id.textViewAdvertDescription);
        TextView textViewDetails = findViewById(R.id.textViewAdvertDetails);
        buttonRemove = findViewById(R.id.buttonRemove);

        databaseHelper = new DatabaseHelper(this);
        advertId = getIntent().getLongExtra("ADVERT_ID", -1);

        // Fetch the advert details from the database
        DatabaseHelper.Advert advert = databaseHelper.getAdvert(advertId);
        if (advert != null) {
            textViewType.setText("Type: " + advert.getType());
            textViewDescription.setText("Description: " + advert.getDescription());
            textViewDetails.setText("Phone: " + advert.getPhone() + ", Location: " + advert.getLocation());
        }

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAdvert();
            }
        });
    }

    private void removeAdvert() {
        if (databaseHelper.deleteAdvert(advertId)) {
            Toast.makeText(this, "Advert removed successfully", Toast.LENGTH_SHORT).show();
            finish();  // Close activity and go back
        } else {
            Toast.makeText(this, "Error removing advert", Toast.LENGTH_SHORT).show();
        }
    }
}

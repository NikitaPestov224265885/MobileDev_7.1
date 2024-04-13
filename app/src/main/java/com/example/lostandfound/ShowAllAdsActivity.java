package com.example.lostandfound;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowAllAdsActivity extends Activity {

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private List<String> advertsDescriptions = new ArrayList<>();
    private List<Long> advertsIds = new ArrayList<>(); // List to hold IDs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_ads_list);

        listView = findViewById(R.id.listViewItems);
        databaseHelper = new DatabaseHelper(this);

        loadAdverts();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowAllAdsActivity.this, RemoveItemActivity.class);
                long advertId = advertsIds.get(position);  // Get the ID using the position
                intent.putExtra("ADVERT_ID", advertId);
                startActivity(intent);
            }
        });
    }

    private void loadAdverts() {
        Cursor cursor = databaseHelper.getAllAdverts();
        advertsDescriptions.clear();
        advertsIds.clear();

        int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ADVERT_ID);
        int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ADVERT_DESCRIPTION);

        if (idIndex == -1 || descriptionIndex == -1) {
            Toast.makeText(this, "Database column not found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idIndex);
            String description = cursor.getString(descriptionIndex);
            advertsDescriptions.add(description);
            advertsIds.add(id); // Store the ID
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, advertsDescriptions);
        listView.setAdapter(adapter);
    }

}

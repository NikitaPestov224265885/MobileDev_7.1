package com.example.lostandfound;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowAllAdsActivity extends Activity {
    private ListView listView;
    private DatabaseHelper databaseHelper;
    private List<String> advertsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_ads_list);

        listView = findViewById(R.id.listViewItems);
        databaseHelper = new DatabaseHelper(this);
        advertsList = new ArrayList<>();

        loadAdverts();
    }

    private void loadAdverts() {
        // This should be run in a background thread
        Cursor advertsCursor = databaseHelper.getAllAdverts();
        if (advertsCursor.moveToFirst()) {
            do {
                // Assuming your cursor has the description in the second column
                String description = advertsCursor.getString(1);
                advertsList.add(description);
            } while (advertsCursor.moveToNext());
        }
        advertsCursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, advertsList);
        listView.setAdapter(adapter);
    }
}

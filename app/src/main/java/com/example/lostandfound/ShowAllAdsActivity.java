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

    @Override
    protected void onResume() {
        super.onResume();
        loadAdverts();
    }

    private void loadAdverts() {
        Cursor cursor = databaseHelper.getAllAdverts();
        List<String> adverts = new ArrayList<>();
        while(cursor.moveToNext()) {
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_ADVERT_DESCRIPTION));
            adverts.add(description);
        }
        cursor.close();

        // Set up the adapter with the new list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adverts);
        System.out.println((adapter.getCount()));
        listView.setAdapter(adapter);
    }

}

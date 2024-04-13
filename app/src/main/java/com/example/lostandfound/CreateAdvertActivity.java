package com.example.lostandfound;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    private EditText editTextName; // Assumed you have a Name field based on your wireframe
    private EditText editTextPhone;
    private EditText editTextDescription;
    private EditText editTextDate;
    private EditText editTextLocation;
    private RadioGroup radioGroupType;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ad);

        databaseHelper = new DatabaseHelper(this); // Initialize your database helper here

        editTextName = findViewById(R.id.editTextName); // Make sure you have a corresponding editTextName in your layout
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLocation = findViewById(R.id.editTextLocation);
        radioGroupType = findViewById(R.id.radioGroupType);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAdvert();
            }
        });
    }

    private void saveAdvert() {
        String name = editTextName.getText().toString().trim(); // Retrieve name input
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        int selectedTypeId = radioGroupType.getCheckedRadioButtonId();
        RadioButton radioButtonType = findViewById(selectedTypeId);

        if (radioButtonType == null) {
            Toast.makeText(this, "Please select a type", Toast.LENGTH_SHORT).show();
            return;
        }
        String type = radioButtonType.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an advert instance
        DatabaseHelper.Advert advert = new DatabaseHelper.Advert(-1, type, phone, description, date, location);

        // Save the advert to the database
        databaseHelper.addAdvert(advert);

        // Notify the user that the advert was saved
        Toast.makeText(this, "Advert saved", Toast.LENGTH_SHORT).show();

        // Clear the input fields
        editTextName.setText("");
        editTextPhone.setText("");
        editTextDescription.setText("");
        editTextDate.setText("");
        editTextLocation.setText("");
        radioGroupType.clearCheck();

        finish();
    }
}

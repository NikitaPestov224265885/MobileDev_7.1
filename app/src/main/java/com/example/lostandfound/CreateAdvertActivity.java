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

    private EditText editTextPhone;
    private EditText editTextDescription;
    private EditText editTextDate;
    private EditText editTextLocation;
    private RadioGroup radioGroupType;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ad);

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
        // Get values from the input fields
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        int selectedTypeId = radioGroupType.getCheckedRadioButtonId();
        String type = ((RadioButton) findViewById(selectedTypeId)).getText().toString();

        // Validate the input
        if (phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would save the data to the SQLite database
        // For now, let's just show a toast message
        Toast.makeText(this, "Advert saved", Toast.LENGTH_SHORT).show();

        // Reset fields
        editTextPhone.setText("");
        editTextDescription.setText("");
        editTextDate.setText("");
        editTextLocation.setText("");
        radioGroupType.clearCheck();

        // Optionally, navigate the user back to the list view or home screen
        // finish();
    }
}

package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText cityNameEditText;
    Button addCityButton;
    Button deleteCityButton;
    int selectedCityPosition = -1;
    boolean isAddMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.listView);
        cityNameEditText = findViewById(R.id.cityNameEdit);
        addCityButton = findViewById(R.id.addCity);
        deleteCityButton = findViewById(R.id.deleteCity);

        String[] cities = {"London", "Paris", "NewYork", "Lahore", "Skardu", "Hunza", "Moscow", "Dubai", "Sharjah"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Select a single city
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddMode) {
                    String newCity = cityNameEditText.getText().toString();
                    if (!newCity.isEmpty()) {
                        dataList.add(newCity);
                        cityAdapter.notifyDataSetChanged();
                        cityNameEditText.setText("");
                    }
                    cityNameEditText.setVisibility(View.GONE);
                    addCityButton.setText("Add City");
                    isAddMode = false;
                } else {
                    // Added extra confirm functionality for better control :>
                    cityNameEditText.setVisibility(View.VISIBLE);
                    addCityButton.setText("Confirm?");
                    isAddMode = true;
                }
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCityPosition = position;
                // Highlight the selected city
                cityList.setItemChecked(position, true);
            }
        });

        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCityPosition != -1) {
                    dataList.remove(selectedCityPosition);
                    cityAdapter.notifyDataSetChanged();
                    // Clear the city selection
                    cityList.clearChoices();
                    // Reset selection
                    selectedCityPosition = -1;
                }
            }
        });
    }
}

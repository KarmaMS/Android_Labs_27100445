package com.example.listycity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<City> cityList;
    private ArrayAdapter<City> cityArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = new ArrayList<>();
        cityList.add(new City("Lahore", "Punjab"));
        cityList.add(new City("Hyderabad", "Sindh"));
        cityList.add(new City("Quetta", "Balochistan"));
        cityList.add(new City("Rahim Yar Khan", "Punjab"));

        ListView cityListView = findViewById(R.id.city_list_view);
        EditText cityNameInput = findViewById(R.id.city_name_input);
        EditText provinceInput = findViewById(R.id.province_input);
        Button addCityButton = findViewById(R.id.add_city_button);

        cityArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityList);
        cityListView.setAdapter(cityArrayAdapter);

        addCityButton.setOnClickListener(v -> {
            String cityName = cityNameInput.getText().toString().trim();
            String province = provinceInput.getText().toString().trim();

            if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(province)) {
                Toast.makeText(this, "Enter both city and province.", Toast.LENGTH_SHORT).show();
                return;
            }

            cityList.add(new City(cityName, province));
            cityArrayAdapter.notifyDataSetChanged();
            cityNameInput.getText().clear();
            provinceInput.getText().clear();
        });

        cityListView.setOnItemLongClickListener((parent, view, position, id) -> {
            cityList.remove(position);
            cityArrayAdapter.notifyDataSetChanged();
            return true;
        });
    }
}

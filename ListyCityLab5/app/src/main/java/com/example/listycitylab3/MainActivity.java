package com.example.listycitylab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditCityFragment.Listener {
    private static final long DOUBLE_TAP_TIMEOUT_MS = 350L;

    private ArrayList<City> dataList;
    private CityArrayAdapter adapter;

    private TextInputEditText inputCity;
    private TextInputEditText inputProvince;
    private int selectedPosition = ListView.INVALID_POSITION;
    private int lastClickedPosition = ListView.INVALID_POSITION;
    private long lastClickTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCity = findViewById(R.id.input_city);
        inputProvince = findViewById(R.id.input_province);
        MaterialButton btnAdd = findViewById(R.id.btn_add);
        MaterialButton btnDelete = findViewById(R.id.btn_delete_selected);
        ListView listView = findViewById(R.id.city_list);

        // Initial cities
        dataList = new ArrayList<>();
        dataList.add(new City("Edmonton", "AB"));
        dataList.add(new City("Vancouver", "BC"));
        dataList.add(new City("Toronto", "ON"));
        dataList.add(new City("Hamilton", "ON"));
        dataList.add(new City("Denver", "CO"));
        dataList.add(new City("Los Angeles", "CA"));

        adapter = new CityArrayAdapter(this, dataList);
        listView.setAdapter(adapter);

        // Add city
        btnAdd.setOnClickListener(v -> {
            String cityName = inputCity.getText() == null ? "" : inputCity.getText().toString().trim();
            String province = inputProvince.getText() == null ? "" : inputProvince.getText().toString().trim();

            if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(province)) {
                Toast.makeText(this, "City and Province are required", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(new City(cityName, province));
            adapter.notifyDataSetChanged();

            inputCity.setText("");
            inputProvince.setText("");
        });

        btnDelete.setOnClickListener(v -> {
            if (selectedPosition == ListView.INVALID_POSITION) {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedPosition);

            if (dataList.isEmpty()) {
                selectedPosition = ListView.INVALID_POSITION;
            } else if (selectedPosition >= dataList.size()) {
                selectedPosition = dataList.size() - 1;
            }

            adapter.setSelectedPosition(selectedPosition);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            long now = System.currentTimeMillis();
            boolean isDoubleClick = (position == lastClickedPosition)
                    && (now - lastClickTime <= DOUBLE_TAP_TIMEOUT_MS);

            lastClickedPosition = position;
            lastClickTime = now;

            setSelectedPosition(position);

            if (isDoubleClick) {
                openEditDialog(position);
            }
        });
    }

    @Override
    public void onCityUpdated(City updatedCity, int position) {
        dataList.set(position, updatedCity);
        setSelectedPosition(position);
    }

    private void setSelectedPosition(int position) {
        selectedPosition = position;
        adapter.setSelectedPosition(position);
    }

    private void openEditDialog(int position) {
        City selected = dataList.get(position);
        EditCityFragment frag = EditCityFragment.newInstance(selected, position);
        frag.show(getSupportFragmentManager(), "EDIT_CITY");
    }
}

package com.example.listycitylab3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditCityFragment.Listener {

    private ArrayList<City> dataList;
    private CityArrayAdapter adapter;

    private TextInputEditText inputCity;
    private TextInputEditText inputProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCity = findViewById(R.id.input_city);
        inputProvince = findViewById(R.id.input_province);
        MaterialButton btnAdd = findViewById(R.id.btn_add);
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

        // Edit/Delete dialog
        listView.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);

            View dialogView = getLayoutInflater()
                    .inflate(R.layout.dialog_city_actions, null);

            TextView title = dialogView.findViewById(R.id.dialog_city_title);
            MaterialButton btnEdit = dialogView.findViewById(R.id.btn_edit);
            MaterialButton btnDelete = dialogView.findViewById(R.id.btn_delete);
            MaterialButton btnCancel = dialogView.findViewById(R.id.btn_cancel);

            title.setText(selected.getName() + ", " + selected.getProvince());

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create();

            btnEdit.setOnClickListener(v -> {
                dialog.dismiss();
                EditCityFragment frag =
                        EditCityFragment.newInstance(selected, position);
                frag.show(getSupportFragmentManager(), "EDIT_CITY");
            });

            btnDelete.setOnClickListener(v -> {
                dialog.dismiss();
                dataList.remove(position);
                adapter.notifyDataSetChanged();
            });

            btnCancel.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        });
    }

    @Override
    public void onCityUpdated(City updatedCity, int position) {
        dataList.set(position, updatedCity);
        adapter.notifyDataSetChanged();
    }
}
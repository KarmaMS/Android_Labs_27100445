package com.example.listycitylab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditCityFragment.Listener {
    private static final long DOUBLE_TAP_TIMEOUT_MS = 350L;
    private static final String TAG = "MainActivity";
    private static final String CITIES_COLLECTION = "cities";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_PROVINCE = "Province";
    private static final String FIELD_PROVINCE_LEGACY = "province";

    private ArrayList<City> dataList;
    private CityArrayAdapter adapter;

    private EditText inputCity;
    private EditText inputProvince;
    private int selectedPosition = ListView.INVALID_POSITION;
    private String selectedCityId = null;
    private int lastClickedPosition = ListView.INVALID_POSITION;
    private long lastClickTime = 0L;

    private FirebaseFirestore db;
    private CollectionReference citiesRef;
    private ListenerRegistration citiesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCity = findViewById(R.id.input_city);
        inputProvince = findViewById(R.id.input_province);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnDelete = findViewById(R.id.btn_delete_selected);
        ListView listView = findViewById(R.id.city_list);

        dataList = new ArrayList<>();

        adapter = new CityArrayAdapter(this, dataList);
        listView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        citiesRef = db.collection(CITIES_COLLECTION);
        observeCities();

        // Add city
        btnAdd.setOnClickListener(v -> {
            String cityName = inputCity.getText() == null ? "" : inputCity.getText().toString().trim();
            String province = inputProvince.getText() == null ? "" : inputProvince.getText().toString().trim();

            if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(province)) {
                Toast.makeText(this, "City and Province are required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cityName.contains("/")) {
                Toast.makeText(this, "City cannot contain '/'", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> cityMap = new HashMap<>();
            cityMap.put(FIELD_PROVINCE, province);

            citiesRef.document(cityName)
                    .set(cityMap)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(this, "City saved to Firestore", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to add city", e);
                        Toast.makeText(this, "Add failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });

            inputCity.setText("");
            inputProvince.setText("");
        });

        btnDelete.setOnClickListener(v -> {
            if (selectedPosition == ListView.INVALID_POSITION) {
                Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
                return;
            }

            City selectedCity = dataList.get(selectedPosition);
            String cityId = selectedCity.getId();

            if (cityId == null || cityId.isEmpty()) {
                Toast.makeText(this, "Cannot delete: invalid city id", Toast.LENGTH_SHORT).show();
                return;
            }

            citiesRef.document(cityId)
                    .delete()
                    .addOnSuccessListener(unused ->
                            Toast.makeText(this, "City deleted from Firestore", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to delete city", e);
                        Toast.makeText(this, "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
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
        if (position < 0 || position >= dataList.size()) {
            return;
        }

        City current = dataList.get(position);
        updatedCity.setId(current.getId());

        String oldCityId = updatedCity.getId();
        String newCityId = updatedCity.getName() == null ? "" : updatedCity.getName().trim();
        if (TextUtils.isEmpty(oldCityId) || TextUtils.isEmpty(newCityId)) {
            Toast.makeText(this, "Cannot edit: invalid city id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newCityId.contains("/")) {
            Toast.makeText(this, "City cannot contain '/'", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> cityMap = new HashMap<>();
        cityMap.put(FIELD_PROVINCE, updatedCity.getProvince());

        if (oldCityId.equals(newCityId)) {
            citiesRef.document(newCityId)
                    .set(cityMap)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(this, "City updated in Firestore", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to update city", e);
                        Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        } else {
            WriteBatch batch = db.batch();
            DocumentReference oldRef = citiesRef.document(oldCityId);
            DocumentReference newRef = citiesRef.document(newCityId);
            batch.delete(oldRef);
            batch.set(newRef, cityMap);
            batch.commit().addOnFailureListener(e -> {
                Log.e(TAG, "Failed to rename city", e);
                Toast.makeText(this, "Rename failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
        }

        selectedCityId = newCityId;
    }

    private void setSelectedPosition(int position) {
        selectedPosition = position;
        if (position >= 0 && position < dataList.size()) {
            selectedCityId = dataList.get(position).getId();
        } else {
            selectedCityId = null;
        }
        adapter.setSelectedPosition(position);
    }

    private void openEditDialog(int position) {
        City selected = dataList.get(position);
        EditCityFragment frag = EditCityFragment.newInstance(selected, position);
        frag.show(getSupportFragmentManager(), "EDIT_CITY");
    }

    private void observeCities() {
        citiesListener = citiesRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "Listen failed", e);
                Toast.makeText(this, "Listen failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            dataList.clear();
            if (snapshot != null) {
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    String name = doc.getString(FIELD_NAME);
                    if (TextUtils.isEmpty(name)) {
                        name = doc.getId();
                    }

                    String province = doc.getString(FIELD_PROVINCE);
                    if (TextUtils.isEmpty(province)) {
                        province = doc.getString(FIELD_PROVINCE_LEGACY);
                    }

                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(province)) {
                        dataList.add(new City(doc.getId(), name, province));
                    }
                }
            }

            restoreSelectionAfterRefresh();
        });
    }

    private void restoreSelectionAfterRefresh() {
        if (selectedCityId == null) {
            setSelectedPosition(ListView.INVALID_POSITION);
            return;
        }

        for (int i = 0; i < dataList.size(); i++) {
            if (selectedCityId.equals(dataList.get(i).getId())) {
                setSelectedPosition(i);
                return;
            }
        }

        setSelectedPosition(ListView.INVALID_POSITION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (citiesListener != null) {
            citiesListener.remove();
        }
    }
}

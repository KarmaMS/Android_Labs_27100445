package com.example.listycitylab5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class EditCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";
    private static final String ARG_POSITION = "position";

    public interface Listener {
        void onCityUpdated(City updatedCity, int position);
    }

    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POSITION, position);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_city, null);

        TextInputEditText nameEt = view.findViewById(R.id.edit_city_name);
        TextInputEditText provEt = view.findViewById(R.id.edit_city_province);

        City city = (City) getArguments().getSerializable(ARG_CITY);
        int position = getArguments().getInt(ARG_POSITION);

        nameEt.setText(city.getName());
        provEt.setText(city.getProvince());

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Edit city")
                .setView(view)
                .setNegativeButton("Cancel", (d, which) -> {})
                .setPositiveButton("Save", null) // override for validation
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = (nameEt.getText() == null) ? "" : nameEt.getText().toString().trim();
            String prov = (provEt.getText() == null) ? "" : provEt.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(prov)) {
                Toast.makeText(requireContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            City updated = new City(name, prov);

            if (getActivity() instanceof Listener) {
                ((Listener) getActivity()).onCityUpdated(updated, position);
            }

            dialog.dismiss();
        }));

        return dialog;
    }
}
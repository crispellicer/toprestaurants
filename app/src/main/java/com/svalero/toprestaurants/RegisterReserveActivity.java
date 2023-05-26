package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;
import com.svalero.toprestaurants.domain.Reserve;
import com.svalero.toprestaurants.domain.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterReserveActivity extends AppCompatActivity {

    private long customerId;
    private long restaurantId;
    private Customer customer;
    private List<Restaurant> restaurants;
    private List<Customer> customers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reserve);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        customers = db.customerDao().getAll();
        restaurants = db.restaurantDao().getAll();

        List<String> customerNames = customers.stream().map(Customer::getName).collect(Collectors.toList());

        Spinner customerSpinner = findViewById(R.id.spinner_reserve_customer_name);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, customerNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(dataAdapter);

        List<String> restaurantNames = restaurants.stream().map(Restaurant::getName).collect(Collectors.toList());

        Spinner restaurantSpinner = findViewById(R.id.spinner_reserve_restaurant_name);

        ArrayAdapter<String> dataAdapterRest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, restaurantNames);
        dataAdapterRest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantSpinner.setAdapter(dataAdapterRest);
    }

    public void saveButton(View view) {
        Spinner customerSpinner = findViewById(R.id.spinner_reserve_customer_name);
        Spinner restaurantSpinner = findViewById(R.id.spinner_reserve_restaurant_name);
        EditText etPeople = findViewById(R.id.edit_text_reserve_people);
        EditText etTables = findViewById(R.id.edit_text_reserve_tables);
        EditText etReserveDate = findViewById(R.id.edit_text_reserve_date);
        CheckBox checkPaid = findViewById(R.id.check_box_paid);
        CheckBox checkAllergic = findViewById(R.id.check_box_allergic);

        String customerName = customerSpinner.getSelectedItem().toString();
        customerId = customers.stream()
                .filter(customer -> customerName.equals(customer.getName()))
                .findAny()
                .orElse(null)
                .getId();
        String restaurantName = restaurantSpinner.getSelectedItem().toString();
        restaurantId = restaurants.stream()
                .filter(restaurant -> restaurantName.equals(restaurant.getName()))
                .findAny()
                .orElse(null)
                .getId();
        String peopleString = etPeople.getText().toString();
        int people = Integer.parseInt(peopleString);
        String tablesString = etTables.getText().toString();
        int tables = Integer.parseInt(tablesString);
        String reserveDate = etReserveDate.getText().toString();
        boolean isPaid = checkPaid.isChecked();
        boolean allergic = checkAllergic.isChecked();

        Reserve reserve = new Reserve(customerId, restaurantId, people, tables, reserveDate, isPaid, allergic);
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        db.reserveDao().insert(reserve);

        Toast.makeText(this, "Reserve has been registered", Toast.LENGTH_LONG).show();
        etPeople.setText("");
        etTables.setText("");
        etReserveDate.setText("");
        checkPaid.setChecked(false);
        checkAllergic.setChecked(false);
        etPeople.requestFocus();
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}

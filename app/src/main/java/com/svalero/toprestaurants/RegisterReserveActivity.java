package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;
import com.svalero.toprestaurants.domain.Reserve;
import com.svalero.toprestaurants.domain.Restaurant;

public class RegisterReserveActivity extends AppCompatActivity {

    private long customerId;
    private long restaurantId;
    private Customer customer;
    private Restaurant restaurant;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reserve);

        Intent intent = new Intent(getIntent());
        customerId = getIntent().getLongExtra("customerId", 1);
        restaurantId = getIntent().getLongExtra("restaurantId", 1);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        customer = db.customerDao().getById(customerId);
        restaurant = db.restaurantDao().getById(restaurantId);

        TextView tvCustomer = findViewById(R.id.edit_text_reserve_customer_name);
        TextView tvRestaurant = findViewById(R.id.edit_text_reserve_restaurant_name);

        tvCustomer.setText(customer.getName());
        tvRestaurant.setText(restaurant.getName());
    }

    public void saveButton(View view) {
        EditText etPeople = findViewById(R.id.edit_text_reserve_people);
        EditText etTables = findViewById(R.id.edit_text_reserve_tables);
        EditText etReserveDate = findViewById(R.id.edit_text_reserve_date);
        CheckBox checkPaid = findViewById(R.id.check_box_paid);
        CheckBox checkAllergic = findViewById(R.id.check_box_allergic);

        String peopleString = etPeople.getText().toString();
        int people = Integer.parseInt(peopleString);
        String tablesString = etTables.getText().toString();
        int tables = Integer.parseInt(tablesString);
        String reserveDate = etReserveDate.getText().toString();
        boolean isPaid = checkPaid.isChecked();
        boolean allergic = checkAllergic.isChecked();

        Reserve reserve = new Reserve(customerId, restaurantId, people, tables, reserveDate, false, false);
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

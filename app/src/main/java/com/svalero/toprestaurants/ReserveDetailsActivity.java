package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;
import com.svalero.toprestaurants.domain.Reserve;
import com.svalero.toprestaurants.domain.Restaurant;

public class ReserveDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_details);

        Intent intent = getIntent();
       long id = intent.getLongExtra("id",0);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Reserve reserve = db.reserveDao().getById(id);
         Toast.makeText(this, getString(R.string.reserve) + reserve.getId(), Toast.LENGTH_LONG).show();

        Customer customer = db.customerDao().getById(reserve.getCustomerId());

        Restaurant restaurant = db.restaurantDao().getById(reserve.getRestaurantId());

        fillData(reserve, customer, restaurant);
    }

    private void fillData(Reserve reserve, Customer customer, Restaurant restaurant) {
        TextView tvCustomerName = findViewById(R.id.tv_reserve_customername_details);
        TextView tvRestaurantName = findViewById(R.id.tv_reserve_restaurantname_details);
        TextView tvPeople = findViewById(R.id.tv_reserve_people_details);
        TextView tvTables = findViewById(R.id.tv_reserve_tables_details);
        TextView tvReserveDate = findViewById(R.id.tv_reserve_date_details);;
        CheckBox checkIsPaid = findViewById(R.id.cb_reserve_paid_details);
        CheckBox checkAllergic = findViewById(R.id.cb_reserve_allergic_details);

        tvCustomerName.setText(customer.getName());
        tvRestaurantName.setText(restaurant.getName());
        tvPeople.setText(String.valueOf(reserve.getPeople()));
        tvTables.setText(String.valueOf(reserve.getTables()));
        tvReserveDate.setText(reserve.getReserveDate());
        checkIsPaid.setChecked(reserve.isPaid());
        checkAllergic.setChecked(reserve.isAllergic());
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}
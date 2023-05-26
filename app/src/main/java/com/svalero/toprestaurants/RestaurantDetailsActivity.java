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
import com.svalero.toprestaurants.domain.Restaurant;

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Restaurant restaurant = db.restaurantDao().getByName(name);
        Toast.makeText(this, "Restaurant: " + restaurant.getName(), Toast.LENGTH_LONG).show();

        fillData(restaurant);
    }

    private void fillData(Restaurant restaurant) {
        TextView tvName = findViewById(R.id.tv_restaurant_name_details);
        TextView tvType = findViewById(R.id.tv_restaurant_type_details);
        TextView tvTimetable = findViewById(R.id.tv_restaurant_timetable_details);
        TextView tvReservePrice = findViewById(R.id.tv_restaurant_reserveprice_details);
        CheckBox checkVeganMenu = findViewById(R.id.cb_restaurant_veganmenu_details);
        TextView tvWebsite = findViewById(R.id.tv_restaurant_website_details);

        tvName.setText(restaurant.getName());
        tvType.setText(restaurant.getType());
        tvTimetable.setText(restaurant.getTimetable());
        tvReservePrice.setText(String.valueOf(restaurant.getReservePrice()));
        checkVeganMenu.setChecked(restaurant.isVeganMenu());
        tvWebsite.setText(restaurant.getWebsite());
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}

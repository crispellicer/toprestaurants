package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Restaurant;

public class RegisterRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);
    }

    public void saveButton(View view) {
        EditText etName = findViewById(R.id.edit_text_restaurant_name);
        EditText etTimetable = findViewById(R.id.edit_text_restaurant_timetable);
        EditText etType = findViewById(R.id.edit_text_restaurant_type);
        EditText etReservePrice = findViewById(R.id.edit_text_restaurant_reserveprice);
        CheckBox checkVeganMenu = findViewById(R.id.check_box_veganmenu);
        EditText etWebsite = findViewById(R.id.edit_text_restaurant_website);

        String name = etName.getText().toString();
        String timetable = etTimetable.getText().toString();
        String type = etType.getText().toString();
        String reservePriceString = etReservePrice.getText().toString();
        double reservePrice = Double.parseDouble(reservePriceString);
        boolean veganMenu = checkVeganMenu.isChecked();
        String website = etWebsite.getText().toString();

        Restaurant restaurant = new Restaurant(name, timetable, type, reservePrice,false, website);
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        db.restaurantDao().insert(restaurant);

        Toast.makeText(this, "Restaurant has been registered", Toast.LENGTH_LONG).show();
        etName.setText("");
        etTimetable.setText("");
        etType.setText("");
        etReservePrice.setText("");
        checkVeganMenu.setChecked(false);
        etWebsite.setText("");
        etName.requestFocus();
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}
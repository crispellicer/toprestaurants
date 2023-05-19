package com.svalero.toprestaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.svalero.toprestaurants.adapter.RestaurantAdapter;
import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListView extends AppCompatActivity {

    private List<Restaurant> restaurantsList;
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list_view);

        restaurantsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.restaurants_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RestaurantAdapter(this, restaurantsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "restaurants")
                .allowMainThreadQueries().build();
        restaurantsList.clear();
        restaurantsList.addAll(db.restaurantDao().getAll());
        adapter.notifyDataSetChanged();
    }
}
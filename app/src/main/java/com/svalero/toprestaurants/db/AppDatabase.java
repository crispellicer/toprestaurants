package com.svalero.toprestaurants.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.toprestaurants.domain.Customer;
import com.svalero.toprestaurants.domain.Reserve;
import com.svalero.toprestaurants.domain.Restaurant;

@Database(entities = {Restaurant.class, Customer.class, Reserve.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RestaurantDao restaurantDao();
    public abstract CustomerDao customerDao();
    public abstract ReserveDao reserveDao();
}

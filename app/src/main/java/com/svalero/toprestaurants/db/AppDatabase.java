package com.svalero.toprestaurants.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.toprestaurants.domain.Restaurant;

@Database(entities = {Restaurant.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RestaurantDao restaurantDao();

}

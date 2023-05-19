package com.svalero.toprestaurants.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.toprestaurants.domain.Restaurant;

import java.util.List;

public interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    List<Restaurant> getAll();

    @Query("SELECT * FROM restaurant WHERE name = :name")
    Restaurant getByName(String name);

    @Query("DELETE FROM restaurant WHERE name = :name")
    void deleteByName(String name);

    @Insert
    void insert(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);
}

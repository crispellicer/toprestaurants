package com.svalero.toprestaurants.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.toprestaurants.domain.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customer")
    List<Customer> getAll();

    @Query("SELECT * FROM customer WHERE name = :name")
    Customer getByName(String name);

    @Query("SELECT * FROM customer WHERE id = :id")
    Customer getById(long id);

    @Query("DELETE FROM customer WHERE name = :name")
    void deleteByName(String name);

    @Insert
    void insert(Customer customer);

    @Delete
    void delete(Customer customer);

    @Update
    void update(Customer customer);
}

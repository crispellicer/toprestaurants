package com.svalero.toprestaurants.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.toprestaurants.domain.Reserve;

import java.util.List;

@Dao
public interface ReserveDao {

    @Query("SELECT * FROM reserve")
    List<Reserve> getAll();

    @Query("SELECT * FROM reserve WHERE id = :id")
    Reserve getById(long id);

    @Query("DELETE FROM reserve WHERE id = :id")
    void deleteById(long id);

    @Insert
    void insert(Reserve reserve);

    @Delete
    void delete(Reserve reserve);

    @Update
    void update(Reserve reserve);
}

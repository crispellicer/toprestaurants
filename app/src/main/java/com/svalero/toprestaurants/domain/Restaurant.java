package com.svalero.toprestaurants.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {
    @PrimaryKey
    private long id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String timetable;
    @ColumnInfo
    private String type;
    @ColumnInfo
    private double reservePrice;
    @ColumnInfo
    private boolean veganMenu;
    @ColumnInfo
    private String website;
}

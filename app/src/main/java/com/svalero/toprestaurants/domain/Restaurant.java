package com.svalero.toprestaurants.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
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

    public Restaurant(String name, String timetable, String type, double reservePrice, boolean veganMenu, String website) {
        this.name = name;
        this.timetable = timetable;
        this.type = type;
        this.reservePrice = reservePrice;
        this.veganMenu = veganMenu;
        this.website = website;
    }

    public Restaurant(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public boolean isVeganMenu() {
        return veganMenu;
    }

    public void setVeganMenu(boolean veganMenu) {
        this.veganMenu = veganMenu;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}


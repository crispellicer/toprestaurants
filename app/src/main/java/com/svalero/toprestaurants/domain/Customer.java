package com.svalero.toprestaurants.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String surname;
    @ColumnInfo
    private String telephone;
    @ColumnInfo
    private String birthDate;
    @ColumnInfo
    private boolean vip;

    public Customer(String name, String surname, String telephone, String birthDate, boolean vip) {
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.vip = vip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}

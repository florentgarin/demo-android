package com.training.firstapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Bottle {

    @PrimaryKey
    public int id;

    public String title;
    public int year;
    public double price;

}

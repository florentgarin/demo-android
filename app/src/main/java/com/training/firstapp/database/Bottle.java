package com.training.firstapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Bottle {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public int year;
    public double price;

    public Bottle() {
    }

    @Ignore
    public Bottle(int id) {
        this.id = id;
    }

    @Ignore
    public Bottle(String title, int year, double price) {
        this.title = title;
        this.year = year;
        this.price = price;
    }

    @Ignore
    public Bottle(int id, String title, int year, double price) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public static class Title {
        public String title;

        public Title(String title) {
            this.title = title;
        }
    }

    public static class Year {
        public int year;

        public Year(int year) {
            this.year = year;
        }
    }
}

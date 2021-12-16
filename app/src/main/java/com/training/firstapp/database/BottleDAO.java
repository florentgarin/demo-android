package com.training.firstapp.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface BottleDAO {

    @Insert
    public void persistBottle(Bottle bottle);

    @Delete
    public void deleteBottle(Bottle bottle);


}

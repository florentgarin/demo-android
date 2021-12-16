package com.training.firstapp.database;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BottleDAO {

    @Insert
    void persistBottle(Bottle bottle);

    @Insert
    void persistBottle(List<Bottle> bottles);

    @Insert
    void persistBottle(Bottle... bottles);

    @Delete
    void deleteBottle(Bottle bottle);

    @Delete(entity=Bottle.class)
    void deleteBottle(Bottle.Title bottle);

    @Update
    void updateBottle(Bottle bottle);

    @Query("SELECT * FROM Bottle WHERE year > :year AND price > :price")
    List<Bottle> findBottlesByYearAndPrice(int year, double price);

}

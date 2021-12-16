package com.training.firstapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Bottle.class}, version = 2, exportSchema = false)
public abstract class WineRoomDatabase extends RoomDatabase {

    public abstract BottleDAO bottleDAO();
}

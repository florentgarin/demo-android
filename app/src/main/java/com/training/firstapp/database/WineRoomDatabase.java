package com.training.firstapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Bottle.class}, version = 1)
public abstract class WineRoomDatabase extends RoomDatabase {

    public abstract BottleDAO bottleDAO();
}

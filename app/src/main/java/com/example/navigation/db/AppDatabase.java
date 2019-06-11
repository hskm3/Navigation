package com.example.navigation.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.navigation.model.Movie;


@Database(entities = {Movie.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ResultDao resultDao();
}

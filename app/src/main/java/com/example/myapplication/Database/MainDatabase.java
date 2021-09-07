package com.example.myapplication.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Model.SavedStallion;
import com.example.myapplication.Model.Stallion;
import com.example.myapplication.Model.User;

@Database(entities = {User.class, Stallion.class, SavedStallion.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

    public abstract StallionDao getStallionDao();

    public abstract SavedStallionDao getSavedStallionDao();
}

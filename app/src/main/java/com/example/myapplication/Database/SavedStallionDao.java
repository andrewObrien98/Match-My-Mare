package com.example.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.Model.SavedStallion;
import com.example.myapplication.Model.Stallion;

@Dao
public interface SavedStallionDao {

    @Insert
    public long insert(SavedStallion stallion);

    @Query("SELECT * FROM savedstallion WHERE stallion_name =:name AND username =:email AND criteria =:criteria")
    public SavedStallion[] checkSavedStallion(String name, String email, String criteria);
}

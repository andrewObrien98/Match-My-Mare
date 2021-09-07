package com.example.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "savedstallion")
public class SavedStallion {

    @PrimaryKey(autoGenerate = true)
    public long id;

    //that way we only pull the saved stallions that correspond to this user
    @ColumnInfo(name = "username")
    public String username;

    //because we cant store a stallion[] we just store its name so then we can query the stallion database since names are unique
    @ColumnInfo(name = "stallion_name")
    public String stallionName;

    //this way we know whether they saved it to a specific mare or favorites
    @ColumnInfo(name = "criteria")
    public String criteria;


}

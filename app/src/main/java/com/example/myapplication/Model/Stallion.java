package com.example.myapplication.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "stallion", indices = {@Index(value = "name", unique = true)})
public class Stallion {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "sire")
    public String sire;

    @ColumnInfo(name = "damsire")
    public String damsire;

    @ColumnInfo(name = "dams_damsire")
    public String dams_damsire;

    @ColumnInfo(name = "dob")
    public String dob;

    @ColumnInfo(name = "registry_primary")
    public String registry_primary;

    @ColumnInfo(name = "registry_secondary")
    public String registry_secondary;

    @ColumnInfo(name = "height")
    public String height;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "WFFS_stat")
    public String WFFS_stat;

    @ColumnInfo(name = "results")
    public String results;

    @ColumnInfo(name = "contact")
    public String contact;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "breeder")
    public String breeder;
}
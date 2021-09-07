package com.example.myapplication.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.myapplication.Model.Stallion;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StallionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Stallion stallion);

    @Delete
    public void delete(Stallion stallion);

    @Update
    public void update(Stallion stallion);

    @Query("SELECT * FROM stallion")
    public Stallion[] getAllStallions();

    @Query("SELECT name FROM stallion")
    public String[] getAllNames();

    @Query("SELECT DISTINCT name FROM stallion ORDER BY name ASC")
    public String[] getNameList();

    @Query("SELECT DISTINCT sire FROM stallion ORDER BY sire ASC")
    public String[] getSireList();

    @Query("SELECT DISTINCT damsire FROM stallion ORDER BY damsire ASC")
    public String[] getDamsireList();

    @Query("SELECT DISTINCT dams_damsire FROM stallion ORDER BY dams_damsire ASC")
    public String[] getGrandDamsireList();

    @Query("SELECT DISTINCT dob FROM stallion ORDER BY dob ASC")
    public String[] getDOBList();

    @Query("SELECT DISTINCT height FROM stallion ORDER BY height ASC")
    public String[] getHeightList();

    @Query("SELECT DISTINCT color FROM stallion ORDER BY color ASC")
    public String[] getColorList();

    @Query("SELECT DISTINCT WFFS_stat FROM stallion ORDER BY WFFS_stat ASC")
    public String[] getWFFSList();

    @Query("SELECT DISTINCT results FROM stallion ORDER BY results ASC")
    public String[] getResultsList();

    @Query("SELECT DISTINCT contact FROM stallion ORDER BY contact ASC")
    public String[] getContactList();

    @Query("SELECT DISTINCT location FROM stallion ORDER BY location ASC")
    public String[] getLocationList();

    @Query("SELECT DISTINCT breeder FROM stallion ORDER BY breeder ASC")
    public String[] getBreederList();

    @Query("SELECT DISTINCT registry_primary FROM stallion ORDER BY registry_primary ASC")
    public String[] getRegistryPrimaryList();

    @Query("SELECT DISTINCT registry_secondary FROM stallion ORDER BY registry_secondary ASC")
    public String[] getRegistrySecondaryList();

    @RawQuery
    List<Stallion> getStallions(SupportSQLiteQuery query);

    @RawQuery
    public Stallion[] getSpecificStallion(SupportSQLiteQuery query);

    @RawQuery
    public Stallion[] getOptionStallion(SupportSQLiteQuery query);
}

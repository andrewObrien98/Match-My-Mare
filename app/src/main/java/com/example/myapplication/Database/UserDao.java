package com.example.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Model.User;

@Dao
public interface UserDao {

    @Insert
    public void insert(User user);

    @Query("SELECT email FROM user WHERE email =:email")
    public String[] checkEmail(String email);

    @Query("SELECT * FROM user where email =:email AND password =:password")
    public User[] checkEmailAndPassword(String email, String password);
}

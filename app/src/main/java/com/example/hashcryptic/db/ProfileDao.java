package com.example.hashcryptic.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Database Access Object commands for the Profile Details
@Dao
public interface ProfileDao {

    // List but only forced to insert and update the first instance
    @Query("SELECT * FROM profile")
    List<Profile> getprofile();

    // Dao command for INSERT
    @Insert
    void insertProfile(Profile profile);

    // Dao command for UPDATE
    @Update
    void updateProfile(Profile profile);

}

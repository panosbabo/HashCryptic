package com.example.hashcryptic.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Database Access Object commands for the Hash Value Details
@Dao
public interface HashDAO {

    // List but only forced to insert and update the first instance
    @Query("SELECT * FROM Hash")
    List<Hash> gethash();

    // Dao command for INSERT
    @Insert
    void insertHash(Hash hash);

    // Dao command for DELETE
    @Delete
    void delete(Hash hash);

}

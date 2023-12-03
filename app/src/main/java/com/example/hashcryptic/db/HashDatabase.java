package com.example.hashcryptic.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

// Reference: Some parts of the following code is from an online Android example https://github.com/ravizworldz/AndroidRoomDB_Java
//Hash database for the Hash value items
@Database(entities = {Hash.class}, version = 1)
public abstract class HashDatabase extends RoomDatabase {

    //Abstract Dao
    public abstract HashDAO hashDao();

    // Variable for INSTANCE
    private static HashDatabase INSTANCE;

    // Method for fetching the data for each instance
    public static HashDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HashDatabase.class, "DB_HASH")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}

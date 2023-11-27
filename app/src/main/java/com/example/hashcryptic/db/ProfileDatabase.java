package com.example.hashcryptic.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Reference: Some parts of the following code is from an online Android example https://github.com/ravizworldz/AndroidRoomDB_Java
//Profile database for the profile Details
@Database(entities = {Profile.class}, version  = 1)
public abstract class ProfileDatabase extends RoomDatabase {

    //Abstract Dao
    public abstract ProfileDao profileDao();

    // Variable for INSTANCE
    private static ProfileDatabase INSTANCE;

    // Method for fetching the data for each instance
    public static ProfileDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProfileDatabase.class, "DB_PROFILE")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}

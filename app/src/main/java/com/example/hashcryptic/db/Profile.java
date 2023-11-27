package com.example.hashcryptic.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entities for the details of the Profile Page
@Entity
public class Profile {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "person_username")
    public String personUsername;

    @ColumnInfo(name = "person_email")
    public String personEmail;

    @ColumnInfo(name = "person_age")
    public String personAge;

}

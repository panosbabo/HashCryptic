package com.example.hashcryptic.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entities for the details of the Hash Values Details
@Entity
public class Hash {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "hash_type")
    public String hashType;

    @ColumnInfo(name = "hash_text")
    public String hashTxt;

    @ColumnInfo(name = "hash_value")
    public String hashValue;

}

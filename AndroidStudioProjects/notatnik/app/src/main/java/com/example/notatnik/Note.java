package com.example.notatnik;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NOTES")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public Long nr;

    @ColumnInfo(name = "text")
    public String text;

    public Note(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

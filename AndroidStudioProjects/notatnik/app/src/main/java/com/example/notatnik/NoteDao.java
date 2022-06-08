package com.example.notatnik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertAll(Note... roomKontakts);

    @Delete
    void delete(Note roomKontakt);

    @Query("SELECT * FROM NOTES")
    LiveData<List<Note>> getAll();

    @Query("DELETE FROM NOTES")
    void deleteAll();
}

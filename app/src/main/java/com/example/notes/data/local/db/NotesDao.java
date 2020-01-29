package com.example.notes.data.local.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NotesEntity note);

    @Query("SELECT * FROM notes_table")
    List<NotesEntity> getAllNotes();

    @Query("SELECT * FROM notes_table WHERE notes_id = :id LIMIT 1")
    NotesEntity getNotes(Long id);

    @Update
    void updateNotes(NotesEntity note);

    @Query("DELETE from notes_table WHERE notes_id = :id")
    void deleteNote(Long id);
}

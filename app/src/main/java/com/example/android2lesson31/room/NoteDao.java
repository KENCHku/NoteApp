package com.example.android2lesson31.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android2lesson31.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note") //select everything from table  note
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM 'note'ORDER BY title ASC")
    List<Note> sortAll();


 @Query("SELECT * FROM 'note'ORDER BY date ASC")
    List<Note> sortAllByDate();

    /*@Query("DELETE FROM note")
    void ();*/


}

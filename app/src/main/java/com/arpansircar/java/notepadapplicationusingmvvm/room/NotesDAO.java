package com.arpansircar.java.notepadapplicationusingmvvm.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;

import java.util.List;

public interface NotesDAO {

    @Insert
    void insertNote(NotesEntity notesEntity);

    @Update
    void updateNote(NotesEntity notesEntity);

    @Query(Constants.QUERY_SINGLE_NOTE)
    LiveData<NotesEntity> selectNote(int noteID);

    @Query(Constants.QUERY_ALL_NOTES)
    LiveData<List<NotesEntity>> selectAllNotes();

    @Delete
    void deleteNote(NotesEntity notesEntity);

    @Query(Constants.DELETE_ALL_NOTES)
    void deleteAllNotes();

}

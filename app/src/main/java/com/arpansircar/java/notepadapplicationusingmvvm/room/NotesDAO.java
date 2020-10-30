package com.arpansircar.java.notepadapplicationusingmvvm.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;

import java.util.List;

/**
 * The NotesDAO interface contains the Data Access Objects.
 * These DAOs are used for accessing the data present within the database.
 */
public interface NotesDAO {

    /*The insertNote(...) method is to be used for inserting the NotesEntity object within the database.*/
    @Insert
    void insertNote(NotesEntity notesEntity);

    /*The updateNote(...) method is to be used for updating any note present in the database.
     * This method accepts a NotesEntity object as well containing the changed values for the database property.*/
    @Update
    void updateNote(NotesEntity notesEntity);

    /*The selectNote(...) method is used for getting a single note from the database.
     * This method will be used particularly when we select a particular note from the database to be displayed.
     * The value will be returned as a NotesEntity object which is encapsulated as a LiveData object.
     * With the help of LiveData, we can remain assured that as soon as there's a change in the object, the observer will accordingly update our view too.*/
    @Query(Constants.QUERY_SINGLE_NOTE)
    LiveData<NotesEntity> selectNote(int noteID);

    /*The selectAllNotes() will be used to display all the notes present in the database.
     * All these notes present will be returned in the form of a NotesEntity object.
     * The value returned from this method will be used primarily for being displayed in the RecyclerView in the NotesActivity.java class.*/
    @Query(Constants.QUERY_ALL_NOTES)
    LiveData<List<NotesEntity>> selectAllNotes();

    /*The deleteNote(...) method will be used for deleting a particular note from the database.*/
    @Delete
    void deleteNote(NotesEntity notesEntity);

    /*In the event that the user wants to delete all the notes present in the database, the user will select this method.*/
    @Query(Constants.DELETE_ALL_NOTES)
    void deleteAllNotes();

}

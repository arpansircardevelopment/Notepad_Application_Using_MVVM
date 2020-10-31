package com.arpansircar.java.notepadapplicationusingmvvm.repository;

import androidx.lifecycle.LiveData;

import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesDAO;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesDatabase;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

import java.util.List;

/**
 * The NotesRepository class acts as a single point of truth for accessing the database.
 * All database operations to be performed by the different activities are mentioned here.
 * As required, the different activities can access the database and perform the required operations.
 */
public class NotesRepository {

    /*A NotesDAO instance is created.
     *Using this instance, the methods in the NotesDAO interface are accessed and the required operations are performed.*/
    private final NotesDAO notesDAO;

    /*The NotesDAO instance is initialized in the constructor itself.
     * As a result of this, as soon as this NotesRepository class is initialized, the NotesDAO instance is initialized as well.*/
    public NotesRepository() {
        notesDAO = NotesDatabase.notesDatabase.notesDAO();
    }

    /*The insertNoteMethod(...) repository method connects to the insertNote(...) method of the NoteDAO interface.
     * It will be used for inserting a note within the database.
     * This method will be accessed from the AddEditNoteActivity via the ViewModel to create a new note within the database.*/
    public void insertNoteMethod(NotesEntity notesEntity) {
        notesDAO.insertNote(notesEntity);
    }

    /*The updateNoteMethod(...) repository method connects to the updateNote(...) method of the NoteDAO interface.
     * It will be used for updating an existing note in the database.
     * The method will be accessed from the AddEditNoteActivity via the ViewModel to update a particular note.*/
    public void updateNoteMethod(NotesEntity notesEntity) {
        notesDAO.updateNote(notesEntity);
    }

    /*The selectNoteMethod(...) repository method connects to the selectNote(..) method of the NoteDAO interface.
     * It will be used for selecting a single note from the database.
     * This method will be accessed from the DisplayNoteActivity via the ViewModel to display a single note to the user.*/
    public LiveData<NotesEntity> selectNoteMethod(int noteID) {
        return notesDAO.selectNote(noteID);
    }

    /*The selectAllNotesMethod() repository method connects to the selectAllNotes(...) method of the NoteDAO interface.
     * It will be used for selecting all the notes present in the database in the form of a list.
     * This method will be accessed from the NotesActivity via the ViewModel to get all the notes and display them within the RecyclerView.*/
    public LiveData<List<NotesEntity>> selectAllNotesMethod() {
        return notesDAO.selectAllNotes();
    }

    /*The deleteNoteMethod(...) repository method connects to the deleteNote(...) method of the NoteDAO interface.
     * It will be used for deleting a single note from the database.
     * This method will be accessed from the DisplayNoteActivity via the ViewModel class to delete a particular note.*/
    public void deleteNoteMethod(NotesEntity notesEntity) {
        notesDAO.deleteNote(notesEntity);
    }

    /*The deleteAllNotesMethod() repository method connects to the deleteAllNotes() method of the NoteDAO interface.
     * It will be used for deleting all the notes present in the database.
     * The method will be accessed from the NotesActivity via the ViewModel to delete all the notes.*/
    public void deleteAllNotesMethod() {
        notesDAO.deleteAllNotes();
    }

}
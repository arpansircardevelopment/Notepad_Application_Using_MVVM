package com.arpansircar.java.notepadapplicationusingmvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.arpansircar.java.notepadapplicationusingmvvm.repository.NotesRepository;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

/**
 * The AddEditNoteActivityViewModel serves as the ViewModel class for the AddEditActivity activity.
 * All the database operations to be performed for this activity will be performed via this class.
 */

public class AddEditNoteActivityViewModel extends ViewModel {

    private final NotesRepository notesRepository = new NotesRepository();

    /*The insertNoteMethod(...) will be called when the user creates a new note.*/
    public void insertNoteMethod(NotesEntity notesEntity) {
        notesRepository.insertNoteMethod(notesEntity);
    }

    /*The updateNoteMethod(...) will be called when the user wants to update an existing note.*/
    public void updateNoteMethod(NotesEntity notesEntity) {
        notesRepository.updateNoteMethod(notesEntity);
    }

}

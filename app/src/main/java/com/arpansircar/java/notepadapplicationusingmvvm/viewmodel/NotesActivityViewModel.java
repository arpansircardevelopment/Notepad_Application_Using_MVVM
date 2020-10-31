package com.arpansircar.java.notepadapplicationusingmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arpansircar.java.notepadapplicationusingmvvm.repository.NotesRepository;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

import java.util.List;

public class NotesActivityViewModel extends ViewModel {

    private final NotesRepository notesRepository;

    /*A NotesRepository instance will be created every time the ViewModel class is instantiated.
     * This instance will be used for performing all the database operations via the repository class.*/
    public NotesActivityViewModel() {
        notesRepository = new NotesRepository();
    }

    /*The selectAllNotesMethod() will be used for receiving a List of NotesEntity objects.
     * This list will be passed back to the activity.*/
    public LiveData<List<NotesEntity>> selectAllNotesMethod() {
        return notesRepository.selectAllNotesMethod();
    }

    /*The deleteAllNotesMethod() is used for deleting all the methods present in the database.*/
    public void deleteAllNotesMethod() {
        notesRepository.deleteAllNotesMethod();
    }

}

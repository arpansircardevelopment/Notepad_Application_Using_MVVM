package com.arpansircar.java.notepadapplicationusingmvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.arpansircar.java.notepadapplicationusingmvvm.repository.NotesRepository;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

public class AddEditNoteActivityViewModel extends ViewModel {

    private final NotesRepository notesRepository;

    public AddEditNoteActivityViewModel() {
        notesRepository = new NotesRepository();
    }

    public void insertNoteMethod(NotesEntity notesEntity) {
        notesRepository.insertNoteMethod(notesEntity);
    }

    public void updateNoteMethod(NotesEntity notesEntity) {
        notesRepository.updateNoteMethod(notesEntity);
    }

}

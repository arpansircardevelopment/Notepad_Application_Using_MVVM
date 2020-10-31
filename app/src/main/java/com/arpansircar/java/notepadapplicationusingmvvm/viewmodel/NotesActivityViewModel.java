package com.arpansircar.java.notepadapplicationusingmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arpansircar.java.notepadapplicationusingmvvm.repository.NotesRepository;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

import java.util.List;

public class NotesActivityViewModel extends ViewModel {

    private final NotesRepository notesRepository;

    public NotesActivityViewModel() {
        notesRepository = new NotesRepository();
    }

    public LiveData<List<NotesEntity>> selectAllNotesMethod() {
        return notesRepository.selectAllNotesMethod();
    }

}

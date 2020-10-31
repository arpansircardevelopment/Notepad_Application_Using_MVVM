package com.arpansircar.java.notepadapplicationusingmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arpansircar.java.notepadapplicationusingmvvm.repository.NotesRepository;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

public class DisplayNoteActivityViewModel extends ViewModel {

    private final NotesRepository notesRepository;

    public DisplayNoteActivityViewModel() {
        notesRepository = new NotesRepository();
    }

    public LiveData<NotesEntity> selectNoteMethod(int noteID) {
        return notesRepository.selectNoteMethod(noteID);
    }

    public void deleteNoteMethod(NotesEntity notesEntity) {
        notesRepository.deleteNoteMethod(notesEntity);
    }

}

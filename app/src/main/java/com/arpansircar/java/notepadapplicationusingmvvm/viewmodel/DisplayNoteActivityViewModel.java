package com.arpansircar.java.notepadapplicationusingmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arpansircar.java.notepadapplicationusingmvvm.repository.NotesRepository;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

public class DisplayNoteActivityViewModel extends ViewModel {

    private final NotesRepository notesRepository;

    /*A NotesRepository instance will be created every time the ViewModel class is instantiated.
     * This instance will be used for performing all the database operations via the repository class.*/
    public DisplayNoteActivityViewModel() {
        notesRepository = new NotesRepository();
    }

    /*The selectNoteMethod(...) will be called when the user selects a single note.*/
    public LiveData<NotesEntity> selectNoteMethod(int noteID) {
        return notesRepository.selectNoteMethod(noteID);
    }

    /*The deleteNoteMethod(...) will be called when the user wants to delete the selected note.*/
    public void deleteNoteMethod(NotesEntity notesEntity) {
        notesRepository.deleteNoteMethod(notesEntity);
    }

}

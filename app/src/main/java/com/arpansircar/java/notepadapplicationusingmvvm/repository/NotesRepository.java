package com.arpansircar.java.notepadapplicationusingmvvm.repository;

import androidx.lifecycle.LiveData;

import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesDAO;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesDatabase;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

import java.util.List;

public class NotesRepository {

    private final NotesDAO notesDAO;

    public NotesRepository() {
        notesDAO = NotesDatabase.notesDatabase.notesDAO();
    }

    public void insertNoteMethod(NotesEntity notesEntity) {
        notesDAO.insertNote(notesEntity);
    }

    public void updateNoteMethod(NotesEntity notesEntity) {
        notesDAO.updateNote(notesEntity);
    }

    public LiveData<NotesEntity> selectNoteMethod(int noteID) {
        return notesDAO.selectNote(noteID);
    }

    public LiveData<List<NotesEntity>> selectAllNotesMethod() {
        return notesDAO.selectAllNotes();
    }

    public void deleteNoteMethod(NotesEntity notesEntity) {
        notesDAO.deleteNote(notesEntity);
    }

    public void deleteAllNotesMethod() {
        notesDAO.deleteAllNotes();
    }

}
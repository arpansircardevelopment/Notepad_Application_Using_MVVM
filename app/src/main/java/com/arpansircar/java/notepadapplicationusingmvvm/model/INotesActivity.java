package com.arpansircar.java.notepadapplicationusingmvvm.model;

/**
 * The INotesActivity interface is used for intercepting the clicks on the RecyclerView present in the NotesActivity.
 * When the interface detects an event, it promptly transfers the required data to the overridden method.
 */
public interface INotesActivity {

    /*The onNoteClicked(...) method is the overridden method.
     * It is used to transfer the data from the RecyclerView to the onNoteClicked method in the NotesActivity.
     * Using this data, the DisplayNoteActivity.java is triggered.*/
    void onNoteClicked(int noteID);
}

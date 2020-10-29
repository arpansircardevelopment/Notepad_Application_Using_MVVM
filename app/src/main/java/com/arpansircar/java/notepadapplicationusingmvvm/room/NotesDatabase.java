package com.arpansircar.java.notepadapplicationusingmvvm.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;

@Database(entities = NotesEntity.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDAO notesDAO();

    public static NotesDatabase notesDatabase = null;

    public void initializeDatabase(Context context) {
        if (notesDatabase == null) {
            notesDatabase = Room
                    .databaseBuilder(context, NotesDatabase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public static NotesDatabase getInstance() {
        return notesDatabase;
    }
}

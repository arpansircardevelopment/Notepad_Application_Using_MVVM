package com.arpansircar.java.notepadapplicationusingmvvm.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;

/**
 * The NotesDatabase is primary class of the all three components of the Room Database.
 * This abstract class extends the RoomDatabase class.
 */
@Database(entities = NotesEntity.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    /*An abstract NotesDAO method is used.
     *This method will be used for accessing the DAO and performing the required transactions.*/
    public abstract NotesDAO notesDAO();

    /*The notesDatabase instance will used for storing a single database instance using the initializeDatabase(...) method.*/
    public static NotesDatabase notesDatabase = null;

    /*The initializeDatabase(...) method is a singleton method.
     * This method will be used for creating an instance of RoomDatabase, only if there isn't a instance of the database already present.
     * Apart from building the RoomDatabase instance, the method also ensures that queries can be performed on the main thread.*/
    public void initializeDatabase(Context context) {
        if (notesDatabase == null) {
            notesDatabase = Room
                    .databaseBuilder(context, NotesDatabase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
    }

    /*Once the database instance is created, the getInstance() method will be used to return the database instance.*/
    public static NotesDatabase getInstance() {
        return notesDatabase;
    }
}
package com.arpansircar.java.notepadapplicationusingmvvm.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;

/**
 * The NotesEntity class specifies the Entity aspect of the Room database.
 * This class specifies the model in which the data will be entered within the database.
 */
@Entity(tableName = Constants.TABLE_NAME)
public class NotesEntity {

    /*The id variable is used for storing the id of the note being entered within the database.
     * This value will be used as the Primary Key and will used for performing all the transactions.*/
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_ID)
    public int id;

    /*The title variable will be used for storing the title of a particular note.*/
    @ColumnInfo(name = Constants.COLUMN_NAME_TITLE)
    public String title;

    /*The content variable will be used for storing the content value of a particular note.*/
    @ColumnInfo(name = Constants.COLUMN_NAME_CONTENT)
    public String content;

    /*The date variable will used for storing the date of a particular note.
     * This date value will be displayed only in the RecyclerView.*/
    @ColumnInfo(name = Constants.COLUMN_NAME_DATE)
    public String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
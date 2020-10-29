package com.arpansircar.java.notepadapplicationusingmvvm.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;

@Entity(tableName = Constants.TABLE_NAME)
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.COLUMN_ID)
    public int id;

    @ColumnInfo(name = Constants.COLUMN_NAME_TITLE)
    public String title;

    @ColumnInfo(name = Constants.COLUMN_NAME_CONTENT)
    public String content;

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
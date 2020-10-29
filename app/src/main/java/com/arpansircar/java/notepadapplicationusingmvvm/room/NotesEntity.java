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

}
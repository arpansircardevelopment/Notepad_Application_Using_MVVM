package com.arpansircar.java.notepadapplicationusingmvvm.model;

public final class Constants {

    private Constants() {
    }

    /*Constants*/
    public static final String TABLE_NAME = "notes_table";
    public static final String DATABASE_NAME = "notes.db";
    public static final String COLUMN_ID = "column_id";
    public static final String COLUMN_NAME_TITLE = "column_title";
    public static final String COLUMN_NAME_CONTENT = "column_content";
    public static final String COLUMN_NAME_DATE = "column_date";

    /*Query Constants*/
    public static final String QUERY_ALL_NOTES = "SELECT * FROM " + TABLE_NAME;
    public static final String QUERY_SINGLE_NOTE = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = :noteID";
    public static final String DELETE_ALL_NOTES = "DELETE FROM " + TABLE_NAME;

}
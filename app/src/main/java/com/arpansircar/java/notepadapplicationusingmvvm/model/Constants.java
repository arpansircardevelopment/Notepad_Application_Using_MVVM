package com.arpansircar.java.notepadapplicationusingmvvm.model;

/**
 * The Constants class is tasked with saving information that will remain constants throughout the entirety of the program.
 * With the help of the Constants class, all these values are grouped into a single place.
 * In the event that we might need to change any of these values, we can do so easily ensuringthat any change is reflected everywhere in the application.
 */

/*The Constant class has been attributed as a final class.
 *Using a the final keyword here ensures that the class cannot be extended.*/
public final class Constants {

    /*A final constructor ensures that this class cannot be instantiated.*/
    private Constants() {
    }

    /*Constants*/
    /*These constants refer to any identifiers used, such as the name of the database, table, etc.*/
    public static final String TABLE_NAME = "notes_table";
    public static final String DATABASE_NAME = "notes.db";
    public static final String COLUMN_ID = "column_id";
    public static final String COLUMN_NAME_TITLE = "column_title";
    public static final String COLUMN_NAME_CONTENT = "column_content";
    public static final String COLUMN_NAME_DATE = "column_date";

    /*Query Constants*/
    /*The query constants are the queries that will be used for performing database transactions.*/
    public static final String QUERY_ALL_NOTES = "SELECT * FROM " + TABLE_NAME;
    public static final String QUERY_SINGLE_NOTE = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = :noteID";
    public static final String DELETE_ALL_NOTES = "DELETE FROM " + TABLE_NAME;

}
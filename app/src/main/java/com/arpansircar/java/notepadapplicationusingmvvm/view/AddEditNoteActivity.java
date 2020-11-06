package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityAddEditNoteBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.AddEditNoteActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * The AddEditNoteActivity is started when the user wants to either add or edit a particular note present in the activity.
 * Once the user has performed his desired function, he can press the floating action button to place the note in the database.
 * Upon clicking the button, the note the user is reverted back to the NotesActivity or the DisplayNoteActivity depending on the function performed.
 */
public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddEditNoteBinding activityAddEditNoteBinding;
    private AddEditNoteActivityViewModel addEditNoteActivityViewModel;
    private NotesEntity currentNoteEntity;
    private String activityFunction;

    /*The onCreate method is the first method to be executed when the application starts up.
     * Here, those methods are called that are to be executed only once.
     * This particular method contains the getIntentData() and initializeViewModel() methods.
     * The getIntentData() method fetches any intent data, if available.
     * The initializeViewModel() method creates an instance of the AddEditNoteActivityViewModel class.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddEditNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note);
        currentNoteEntity = new NotesEntity();
        getIntentData();
        setToolbarTitle();
        initializeViewModel();
    }

    /*onStart() lifecycle callback method is executed after onCreate(). Here, the setOnClickListenerMethod() is executed.*/
    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListenerMethod();
    }

    /*The getIntentData() method is used for fetching any intent data if available.
     * Intent data is available only when this activity is used for editing a particular note.
     * When getIntentData() is called, it first checks if the function is "edit" or "insert".
     * In case of "edit", the intent data is fetched and placed into a global instance of the NotesEntity class.
     * Finally, this instance is passed over to the setNoteInActivity(...) method.*/
    private void getIntentData() {
        Intent intent = getIntent();
        activityFunction = intent.getStringExtra("function");

        if (Objects.equals(activityFunction, "edit")) {
            currentNoteEntity.setId(intent.getIntExtra(Constants.COLUMN_ID, -1));
            currentNoteEntity.setTitle(intent.getStringExtra(Constants.COLUMN_NAME_TITLE));
            currentNoteEntity.setContent(intent.getStringExtra(Constants.COLUMN_NAME_CONTENT));
            currentNoteEntity.setDate(intent.getStringExtra(Constants.COLUMN_NAME_DATE));
            setNoteInActivity(currentNoteEntity);
        }
    }

    private void setToolbarTitle() {
        Toolbar toolbar = activityAddEditNoteBinding.toolbar.activityToolbar;
        if (Objects.equals(activityFunction, "insert")) {
            toolbar.setTitle(R.string.new_note_title);
        } else {
            toolbar.setTitle(R.string.edit_note);
        }
    }

    /*The setNoteInActivity(...) method sets the note title and content into the respective EditTexts via the NotesEntity parameter instance.
     * This allows the user to preview the note, earlier written, and edit them accordingly.*/
    private void setNoteInActivity(NotesEntity notesEntity) {
        try {
            activityAddEditNoteBinding.titleEditText.setText(notesEntity.getTitle());
            activityAddEditNoteBinding.contentEditText.setText(notesEntity.getContent());
        } catch (NullPointerException nullPointerException) {
            finish();
        }
    }

    /*The initializeViewModel() method initializes the AddEditNoteActivityViewModel instance with the ViewModel class.*/
    private void initializeViewModel() {
        addEditNoteActivityViewModel = new ViewModelProvider(this).get(AddEditNoteActivityViewModel.class);
    }

    /*The setOnClickListenerMethod() method sets the onClickListener to the floating action button used in the activity.*/
    private void setOnClickListenerMethod() {
        activityAddEditNoteBinding.doneFloatingActionButton.setOnClickListener(this);
    }

    /*The onClick(...) method intercepts all clicks performed in the current activity.
     * When the floating action button is clicked, the hideKeyboardMethod() method is executed and the keyboard is hidden.
     * The next function is performed depending on the value present in the activityFunction variable.
     * If the value is "insert", note title, content, and date values are extracted and passed on to the initializeSaveMethod(...) method.
     * If the value is "edit", the currentNoteEntity title and content values are replaced with new values and passed on to the
     *      initializeUpdateMethod() method.
     * Before performing any of these functions, a check is performed using the checkIfEditTextNotBlank() method to ensure that
     *      the titleEditText isn't blank.*/
    @Override
    public void onClick(View view) {
        if (view == activityAddEditNoteBinding.doneFloatingActionButton) {
            hideKeyboardMethod();

            if (activityFunction.equals("insert")) {
                if (checkIfEditTextNotBlank()) {
                    String title = activityAddEditNoteBinding.titleEditText.getText().toString();
                    String content = activityAddEditNoteBinding.contentEditText.getText().toString();
                    String date = new SimpleDateFormat("MMMM dd", Locale.UK).format(Calendar.getInstance().getTime());
                    initializeSaveMethod(title, content, date);
                }
            }

            if (activityFunction.equals("edit")) {
                if (checkIfEditTextNotBlank()) {
                    currentNoteEntity.setTitle(activityAddEditNoteBinding.titleEditText.getText().toString());
                    currentNoteEntity.setContent(activityAddEditNoteBinding.contentEditText.getText().toString());
                    initializeUpdateMethod(currentNoteEntity);
                }
            }
        }
    }

    /*The hideKeyboardMethod() hides the keyboard from the screen.*/
    private void hideKeyboardMethod() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*The checkIfEditTextBlank() method is called to check if the titleEditText is empty or not*/
    private boolean checkIfEditTextNotBlank() {
        if (activityAddEditNoteBinding.titleEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /*The initializeSaveMethod(...) saves a new note into the database.
     * When triggered, it fetches the title, content, and date parameters and binds them into a single NotesEntity object.
     * Finally, this NotesEntity object is inserted into the database and the activity is removed from view.*/
    private void initializeSaveMethod(String title, String content, String date) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setTitle(title);
        notesEntity.setContent(content);
        notesEntity.setDate(date);

        addEditNoteActivityViewModel.insertNoteMethod(notesEntity);
        finish();
    }

    /*The initializeUpdateMethod(...) updates an existing note in the database.
     * The NotesEntity instance parameter already contains the updated details for the note.
     * This instance passed within the ViewModel's update method to be inserted into the database and the activity is closed.*/
    private void initializeUpdateMethod(NotesEntity notesEntity) {
        addEditNoteActivityViewModel.updateNoteMethod(notesEntity);
        finish();
    }
}
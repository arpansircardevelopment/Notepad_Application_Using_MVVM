package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityAddEditNoteBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.AddEditNoteActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * The AddEditNoteActivity is started when the user wants to either add or edit a particular note present in the activity.
 * Once the user has started to perform his desired function, he can press the floating action button to place the note in the database.
 * Upon clicking the button, the note the user is reverted back to the NotesActivity with the new note displayed in the RecyclerView.
 */
public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddEditNoteBinding activityAddEditNoteBinding;
    private AddEditNoteActivityViewModel addEditNoteActivityViewModel;

    /*The onCreate method is the first method that is executed when the application starts up.
     * Usually, in this method, such functions are executed that are to be performed only once.
     * In this method, I've defined one other method to be executed when the application starts
     * The initializeViewModel() is used to create an instance of the ViewModel class associated with this activity.
     * This ViewModel will be used to handle any configuration changes.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddEditNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note);
        initializeViewModel();
    }

    /*The onStart() is the next method executed after the onCreate() callback method.
     * In this method, the setOnClickListenerMethod() method is executed to set the click listener for the floating action button.*/
    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListenerMethod();
    }

    /*The initializeViewModel() method is used for initializing the AddEditNoteActivityViewModel instance with the ViewModel class.*/
    private void initializeViewModel() {
        addEditNoteActivityViewModel = new ViewModelProvider(this).get(AddEditNoteActivityViewModel.class);
    }

    /*The setOnClickListenerMethod() is used to set the onClickListener to the floating action button used in the activity.*/
    private void setOnClickListenerMethod() {
        activityAddEditNoteBinding.doneFloatingActionButton.setOnClickListener(this);
    }

    /*The onClick(...) method allows us to intercept all the clicks performed in this current activity.
     * When the floating action button is clicked, first the hideKeyboardMethod() is executed.
     * Next, the checkIfEditTextBlank() method is triggered to check if the EditText is blank.
     * If the EditText isn't blank, the title, content, and date values are retrieved and sent to the initializeSaveMethod(...).*/
    @Override
    public void onClick(View view) {
        if (view == activityAddEditNoteBinding.doneFloatingActionButton) {
            hideKeyboardMethod();

            if (checkIfEditTextBlank()) {
                String title = activityAddEditNoteBinding.titleEditText.getText().toString();
                String content = activityAddEditNoteBinding.contentEditText.getText().toString();
                String date = new SimpleDateFormat("MMMM dd", Locale.UK).format(Calendar.getInstance().getTime());
                initializeSaveMethod(title, content, date);
            }
        }
    }

    /*The hideKeyboardMethod() is used to hide the keyboard from the screen.*/
    private void hideKeyboardMethod() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*The checkIfEditTextBlank() method is used to check if the EditText is empty or not.*/
    private boolean checkIfEditTextBlank() {
        if (activityAddEditNoteBinding.titleEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /*The initializeSaveMethod(...) is used for saving the new note that has been created.
     * When the method is triggered, it receives the title, the content, and the date on which the note was created.
     * Next, all these values are packed into a single object that passed on to the ViewModel to be saved into the database.
     * Once this happens, the activity is closed and the NotesActivity is brought back into view. */
    private void initializeSaveMethod(String title, String content, String date) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setTitle(title);
        notesEntity.setContent(content);
        notesEntity.setDate(date);

        addEditNoteActivityViewModel.insertNoteMethod(notesEntity);
        finish();
    }
}
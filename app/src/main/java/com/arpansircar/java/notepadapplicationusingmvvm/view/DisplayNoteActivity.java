package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityDisplayNoteBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.DisplayNoteActivityViewModel;

/**
 * The DisplayNoteActivity displays the contents of a single note.
 * Upon clicking a certain note in the NotesActivity RecyclerView, the user is guided to this activity and the selected note is displayed here.
 * In this activity, the user can view, delete, or update the particular note as required.
 * All such changes are reflected back in the NotesActivity.
 */
public class DisplayNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDisplayNoteBinding activityDisplayNoteBinding;
    private NotesEntity currentNoteEntity;
    private DisplayNoteActivityViewModel displayNoteActivityViewModel;

    /*The onCreate method is the first method to be executed when the application starts up.
     * Here, those methods are called that are to be executed only once.
     * This particular method executes the setToolbarMethod(), getIntentData(), and initializeViewModel() methods.
     * The setToolbarMethod() sets the toolbar which contains the functionality for deleting a note.
     * The getIntentData() fetches complete note details including the note id, title, content, and date.
     * The initializeViewModel() method creates an instance of the AddEditNoteActivityViewModel class.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplayNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_note);
        setToolbarMethod();
        getIntentData();
        initializeViewModel();
    }

    /*onStart() lifecycle callback method is executed after onCreate().
     * In this callback method, two methods are executed.
     * The setObserver() method activates the observer to check any changes arising within the LiveData object.
     * The setOnClickListenerMethod() intercepts any clicks occurring within the activity.*/
    @Override
    protected void onStart() {
        super.onStart();
        startObserver();
        setOnClickListenerMethod();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDisplayNoteBinding = null;
    }

    /*The onCreateOptionsMenu(...) creates the menu options in the toolbar.
     * Here, a single menu option is used to delete the note being viewed.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_note_menu, menu);
        return true;
    }

    /*The onOptionsItemSelected(...) method intercepting the clicks occurring on the menu options.
     * Here, a single menu option will be displayed to allow the user to delete the particular note being viewed.
     * Upon being pressed, this delete option will trigger the deleteNoteMethod() and the activity will be removed from the view.*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_menu_option) {
            deleteNoteMethod();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*The setToolbarMethod() sets the custom toolbar in the activity.*/
    private void setToolbarMethod() {
        Toolbar toolbar = activityDisplayNoteBinding.toolbar.activityToolbar;
        toolbar.setTitle(R.string.display_activity_title);
        setSupportActionBar(toolbar);
    }

    /*The initializeViewModel() creates an instance to the DisplayNoteActivityViewModel for communicating with the database.*/
    private void initializeViewModel() {
        displayNoteActivityViewModel = new ViewModelProvider(this).get(DisplayNoteActivityViewModel.class);
    }

    /*The setObserverMethod() method is used simply activates the observer.*/
    private void startObserver() {
        final Observer<NotesEntity> notesEntityObserver = notesEntity -> {
            this.currentNoteEntity = notesEntity;
            setNoteInActivity(notesEntity);
        };

        displayNoteActivityViewModel.selectNoteMethod(getIntentData()).observe(this, notesEntityObserver);
    }

    /*The setOnClickListenerMethod() method sets the onClickListener to the floating action button used in the activity.*/
    private void setOnClickListenerMethod() {
        activityDisplayNoteBinding.editNoteFloatingActionButton.setOnClickListener(this);
    }

    /*The setNoteInActivity(...) method sets the NotesEntity instance within the activity.
     * The method is triggered by any changes occurring within the LiveData instance.
     * A try-catch block is placed to handle the NullPointerExceptions that arise when a note is deleted.
     * The NullPointerException occurs as the observer observes a change and tries to fetch the changed note.
     * But this isn't possible as the note has already been deleted from the database, causing the exception.
     * Therefore, the catch block handles the exception by removing the activity from view and showing a Toast message.*/
    private void setNoteInActivity(NotesEntity notesEntity) {
        try {
            activityDisplayNoteBinding.titleTextView.setText(notesEntity.getTitle());
            activityDisplayNoteBinding.contentTextView.setText(notesEntity.getContent());
        } catch (NullPointerException nullPointerException) {
            finish();
        }
    }

    /*The getIntentData() method extracts the noteID from the intent.*/
    private int getIntentData() {
        Intent intent = getIntent();
        return intent.getIntExtra(Constants.COLUMN_ID, -1);
    }

    /*The deleteNoteMethod() deletes a note from the database and the NotesActivity.
     * The method is triggered by the onOptionsItemSelected() method when the user presses the delete menu option in the toolbar.*/
    private void deleteNoteMethod() {
        displayNoteActivityViewModel.deleteNoteMethod(currentNoteEntity);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
    }

    /*The onClick(...) method intercepts all clicks performed in the current activity.
     * When the floating action button is clicked, the "edit" function, note id, title, content, and date is bundled into the intent as extras.
     * Finally, this data is sent into the AddEditNoteActivity and the activity is started.*/
    @Override
    public void onClick(View view) {
        if (view == activityDisplayNoteBinding.editNoteFloatingActionButton) {
            Intent editNoteIntent = new Intent(DisplayNoteActivity.this, AddEditNoteActivity.class);
            editNoteIntent.putExtra("function", "edit");
            editNoteIntent.putExtra(Constants.COLUMN_ID, currentNoteEntity.getId());
            editNoteIntent.putExtra(Constants.COLUMN_NAME_TITLE, currentNoteEntity.getTitle());
            editNoteIntent.putExtra(Constants.COLUMN_NAME_CONTENT, currentNoteEntity.getContent());
            editNoteIntent.putExtra(Constants.COLUMN_NAME_DATE, currentNoteEntity.getDate());

            startActivity(editNoteIntent);
        }
    }
}
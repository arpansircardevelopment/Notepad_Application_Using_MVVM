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
import android.widget.Toast;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityDisplayNoteBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.DisplayNoteActivityViewModel;

/**
 * The DisplayNoteActivity is tasked with the complete contents of a single note.
 * When the user clicks on a particular note in the RecyclerView in NotesActivity, the user is guided to this activity.
 * Moreover, the note that they tapped on is displayed in this activity.
 * From this activity, the user can perform functions such as editing that particular note and deleting it if required.
 * All such changes performed are reflected back in the NotesActivity.
 */
public class DisplayNoteActivity extends AppCompatActivity {

    private ActivityDisplayNoteBinding activityDisplayNoteBinding;
    private NotesEntity currentNoteEntity;
    private DisplayNoteActivityViewModel displayNoteActivityViewModel;

    /*The onCreate method is the first method that is executed when the application starts up.
     * Usually, in this method, such functions are executed that are to be performed only once.
     * In this method, I've defined two methods to be executed as soon as the application starts up.
     * The setToolbarMethod() will be used for setting the toolbar. This toolbar will contain the functionality for deleting the note.
     * The getIntentData() method will be used for extracting the noteID which was sent as an Extra via the Intent.
     * Finally, the initializeViewModel() method is used to create an instance of the ViewModel class associated with this activity.
     * This ViewModel will be used to handle any configuration changes. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplayNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_note);
        setToolbarMethod();
        getIntentData();
        initializeViewModel();
    }

    /*The onStart() is the next method executed after the onCreate() callback method.
     * In this method, the application is made visible to the user as the application begins to enter into the foreground.
     * The onStart() method executes only one method, namely the startObserver() method.
     * The setObserverMethod() method is used to activate the Observer and observe any changes arising from the LiveData object. */
    @Override
    protected void onStart() {
        super.onStart();
        startObserver();
    }

    /*The onCreateOptionsMenu(...) is used for creating the menu options in the toolbar.
     * In this particular activity, a single menu option is used for providing the functionality to delete the note being viewed.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_note_menu, menu);
        return true;
    }

    /*The onOptionsItemSelected(...) method is used to intercepting the clicks when the user taps on a particular menu option.
     * This activity will contain only a single menu option, i.e., a menu option to delete the particular note being viewed.
     * As a result, this method also checks for a single menu option being tapped, i.e., the delete menu option.*/
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

    /*The setToolbarMethod() will be used for displaying the custom toolbar in the activity.
     * In this method, the toolbar instance is assigned to the custom toolbar layout.
     * Next, the required title is assigned to this toolbar instance.
     * Finally, this toolbar is set into the activity.*/
    private void setToolbarMethod() {
        Toolbar toolbar = activityDisplayNoteBinding.toolbar.activityToolbar;
        toolbar.setTitle(R.string.display_activity_sample_text);
        setSupportActionBar(toolbar);
    }

    /*The initializeViewModel() is used to create an instance to the DisplayNoteActivityViewModel.
     * This ViewModel instance will be used for communicating with the database as well as handling any configuration changes.*/
    private void initializeViewModel() {
        displayNoteActivityViewModel = new ViewModelProvider(this).get(DisplayNoteActivityViewModel.class);
    }

    /*The setObserverMethod() method is used simply for activating the observer.
     * Whenever any changes are performed in the database, the observer observes these changes and accordingly performs changes in this activity.*/
    private void startObserver() {
        final Observer<NotesEntity> notesEntityObserver = notesEntity -> {
            this.currentNoteEntity = notesEntity;
            setNoteInActivity(notesEntity);
        };

        displayNoteActivityViewModel.selectNoteMethod(getIntentData()).observe(this, notesEntityObserver);
    }

    /*The setNoteInActivity(...) method sets the data from the NotesEntity object within the activity.
     * This method is triggered when there's a change in the LiveData object in the database.
     * The method contains a try-catch to check and handle a NullPointerException that arises when the note is deleted.
     * The observer observes a change when the note is deleted. Therefore, it tries to fetch the note details using the noteID.
     * However, this isn't possible as the note is already deleted from the database. And this causes a NullPointerException causing the app to crash.
     * With the help of the try-catch block, the app simply guides the user to the previous, i.e., NotesActivity.java class.
     * Apart from this, the user is shown a Toast message that signifies that the note has been deleted.*/
    private void setNoteInActivity(NotesEntity notesEntity) {
        try {
            activityDisplayNoteBinding.titleTextView.setText(notesEntity.getTitle());
            activityDisplayNoteBinding.contentTextView.setText(notesEntity.getContent());
        } catch (NullPointerException nullPointerException) {
            finish();
        }
    }

    /*The getIntentData() method is used for extracting the data from the intent object.
     * This intent contains the noteID of the selected note.
     * This note value is returned to the startObserver() method to fetch the note from the database.*/
    private int getIntentData() {
        Intent intent = getIntent();
        return intent.getIntExtra("noteID", -1);
    }

    /*The deleteNoteMethod() is used for deleting a note from the database as well as from the activity.
     * The method is triggered when the user presses the delete menu option in the toolbar.
     * When this event occurs, the onOptionsItemSelected() method triggers this event.*/
    private void deleteNoteMethod() {
        displayNoteActivityViewModel.deleteNoteMethod(currentNoteEntity);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
    }
}
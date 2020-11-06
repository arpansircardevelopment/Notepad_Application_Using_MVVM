package com.arpansircar.java.notepadapplicationusingmvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityNotesBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.model.Constants;
import com.arpansircar.java.notepadapplicationusingmvvm.model.INotesActivity;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesDatabase;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.NotesActivityViewModel;

import java.util.List;
import java.util.Objects;

/**
 * The NotesActivity is the primary activity in the entire applications.
 * This activity starts up and shows the user all the notes that have been created or the facility to add a new note using the floating action button.
 * All the notes that have been created using this application and short details associated with them show up in the RecyclerView.
 * The user can click on any of these notes to view the complete details of the note.
 */
public class NotesActivity extends AppCompatActivity implements View.OnClickListener, INotesActivity {

    private ActivityNotesBinding activityNotesBinding;
    private NotesActivityViewModel notesActivityViewModel;
    private List<NotesEntity> notesEntityList;

    /*The onCreate method is the first method that is executed when the application starts up.
     * Usually, in this method, such functions are executed that are to be performed only once.
     * In this method, I've defined two other methods to be executed as soon as the application starts up and that are to be executed only once.
     * The initializeDatabase() method is used to create an instance of the RoomDatabase.
     * An application context is used because we want to use the same instance throughout the context of the application.
     * The initializeViewModel() is used to create an instance of the ViewModel class associated with this activity.
     * This ViewModel will be used to handle any configuration changes. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesBinding = DataBindingUtil.setContentView(this, R.layout.activity_notes);
        initializeDatabase();
        initializeViewModel();
        setToolbar();
        showHideRecyclerViewButton();
    }

    /*The onStart() is the next method executed after the onCreate() callback method.
     * In this method, two methods are executed, namely the setOnClickListenerMethod() and the setObserverMethod().
     * The setOnClickListenerMethod() method is tasked with setting the onClickListener for any and all buttons that might exist in the activity.
     * Currently, the activity has a single floating action button so this button's onClickListener is set in this activity.
     * The setObservableMethod() method is used to activate the Observer to observe changes in the LiveData object present in the ViewModel. */
    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListenerMethod();
        setObserverMethod();
    }

    /*The initializeDatabase() method is used to create an instance of the NotesDatabase class.
     * This single instance will be used for performing all the database transactions including the CRUD operations.
     * For creating an instance of this class, the application context is used.
     * I've assumed that using an application context will allow me to use the instance throughout the entirety of the application.*/
    private void initializeDatabase() {
        NotesDatabase.initializeDatabase(getApplicationContext());
    }

    /*The initializeViewModel() method is used for initializing the NotesActivityViewModel instance with the ViewModel class.*/
    private void initializeViewModel() {
        notesActivityViewModel = new ViewModelProvider(this).get(NotesActivityViewModel.class);
    }

    private void setToolbar() {
        Toolbar toolbar = activityNotesBinding.toolbar.activityToolbar;
        setSupportActionBar(toolbar);
    }

    /*The setObserverMethod() method is used simply for activating the observer.
     * This task is done at the Started state of the activity to allow it to start observing the changes in the LiveData as soon as the activity starts.
     * If there are any changes in the database, i.e., if it returns a List of NotesEntity objects, the list is sent to the setRecyclerViewMethod().*/
    private void setObserverMethod() {
        final Observer<List<NotesEntity>> observer = notesEntityList -> {
            this.notesEntityList = notesEntityList;
            setRecyclerViewMethod(notesEntityList);
        };
        notesActivityViewModel.selectAllNotesMethod().observe(this, observer);
    }

    /*The setRecyclerViewMethod(...) is called when the observer observes a change in the LiveData.
     * Upon calling this method, the List of NotesEntity objects are passed into this method which promptly passes it to the RecyclerViewAdapter class.
     * Accordingly, the RecyclerView is populated with the required views and a new RecyclerView is displayed with the newly added notes.*/
    private void setRecyclerViewMethod(List<NotesEntity> notesEntityList) {
        RecyclerView recyclerView = activityNotesBinding.notesListRecyclerView;
        NotesAdapter notesAdapter = new NotesAdapter(notesEntityList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(notesAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /*The setOnClickListenerMethod() is used to set the onClickListener to all the floating action buttons used in the activity.*/
    private void setOnClickListenerMethod() {
        activityNotesBinding.newNoteFloatingActionButton.setOnClickListener(this);
        activityNotesBinding.deleteAllNotesFloatingActionButton.setOnClickListener(this);
    }

    private void showHideRecyclerViewButton() {
        final Observer<Integer> recyclerViewSizeObserver = integer -> {
            if (integer == 0)
                activityNotesBinding.deleteAllNotesFloatingActionButton.setVisibility(View.INVISIBLE);
            else
                activityNotesBinding.deleteAllNotesFloatingActionButton.setVisibility(View.VISIBLE);
        };

        NotesAdapter.getRecyclerViewSize().observe(this, recyclerViewSizeObserver);
    }

    /*The onClick(...) method allows us to intercept all the clicks placed within this method.
     * Since, only a single view can be clicked in this activity, therefore, this method contains only a single if clause.
     * The if clause checks if the floating action button has been clicked or not.
     * If it has, an Intent object starts the AddEditActivity for the user to add a new note to the activity. */
    @Override
    public void onClick(View view) {
        if (view == activityNotesBinding.newNoteFloatingActionButton) {
            Intent newNoteIntent = new Intent(NotesActivity.this, AddEditNoteActivity.class);
            newNoteIntent.putExtra("function", "insert");
            startActivity(newNoteIntent);
        }

        if (view == activityNotesBinding.deleteAllNotesFloatingActionButton) {
            AlertDialog alertDialog = showAlertDialogMethod(
                    "delete_all",
                    getString(R.string.delete_all_notes_alert_title),
                    getString(R.string.delete_all_notes_alert_message));
            alertDialog.show();
        }
    }

    /*The onNoteClicked(...) method here is an overridden method from the INotesActivity interface.
     * When a note is clicked in the RecyclerView, the clicked noteID is transferred to this method.
     * When this method is triggered, an Intent object is created to start the DisplayNoteActivity.java activity.
     * Within this object, the noteID of the clicked note is sent as an Integer extra to next activity.
     * Finally, the activity is started.*/
    @Override
    public void onNoteClicked(NotesEntity notesEntity) {
        Intent intent = new Intent(NotesActivity.this, DisplayNoteActivity.class);
        intent.putExtra(Constants.COLUMN_ID, notesEntity.getId());
        startActivity(intent);
    }

    @Override
    public void onNoteSwiped(int position) {
        notesActivityViewModel.deleteNoteMethod(notesEntityList.get(position));
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
    }

    private AlertDialog showAlertDialogMethod(String function, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.yes_string, (dialogInterface, i) -> {
                    if (Objects.equals(function, "exit"))
                        super.onBackPressed();

                    if (Objects.equals(function, "delete_all"))
                        notesActivityViewModel.deleteAllNotesMethod();
                })

                .setNegativeButton(R.string.no_string, (dialogInterface, i) -> dialogInterface.cancel());

        return alertDialogBuilder.create();
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = showAlertDialogMethod(
                "exit",
                getString(R.string.exit_title),
                getString(R.string.exit_message));

        alertDialog.show();
    }
}
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

public class DisplayNoteActivity extends AppCompatActivity {

    private ActivityDisplayNoteBinding activityDisplayNoteBinding;
    private NotesEntity currentNoteEntity;
    private DisplayNoteActivityViewModel displayNoteActivityViewModel;
    private int currentNoteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplayNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_note);
        setToolbarMethod();
        getIntentData();
        initializeViewModel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startObserver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_note_menu, menu);
        return true;
    }

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

    private void setToolbarMethod() {
        Toolbar toolbar = activityDisplayNoteBinding.toolbar.activityToolbar;
        toolbar.setTitle(R.string.display_activity_sample_text);
        setSupportActionBar(toolbar);
    }

    private void initializeViewModel() {
        displayNoteActivityViewModel = new ViewModelProvider(this).get(DisplayNoteActivityViewModel.class);
    }

    private void startObserver() {
        final Observer<NotesEntity> notesEntityObserver = notesEntity -> {
            this.currentNoteEntity = notesEntity;
            setNoteInActivity(notesEntity);
        };

        displayNoteActivityViewModel.selectNoteMethod(currentNoteID).observe(this, notesEntityObserver);
    }

    private void setNoteInActivity(NotesEntity notesEntity) {
        if (currentNoteID != -1) {
            activityDisplayNoteBinding.titleTextView.setText(notesEntity.getTitle());
            activityDisplayNoteBinding.contentTextView.setText(notesEntity.getContent());
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        currentNoteID = intent.getIntExtra("noteID", -1);
    }

    private void deleteNoteMethod() {
        currentNoteID = -1;
        displayNoteActivityViewModel.deleteNoteMethod(currentNoteEntity);
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

}
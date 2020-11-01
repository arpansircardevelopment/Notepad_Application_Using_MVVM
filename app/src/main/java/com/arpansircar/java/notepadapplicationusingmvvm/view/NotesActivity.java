package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityNotesBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesDatabase;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.NotesActivityViewModel;

import java.util.List;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityNotesBinding activityNotesBinding;
    private NotesActivityViewModel notesActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesBinding = DataBindingUtil.setContentView(this, R.layout.activity_notes);
        initializeDatabase();
        initializeViewModel();
        setOnClickListenerMethod();
        setObserverMethod();
    }

    private void initializeDatabase() {
        NotesDatabase.initializeDatabase(getApplicationContext());
    }

    private void initializeViewModel() {
        notesActivityViewModel = new ViewModelProvider(this).get(NotesActivityViewModel.class);
    }

    private void setObserverMethod() {
        final Observer<List<NotesEntity>> observer = this::setRecyclerViewMethod;
        notesActivityViewModel.selectAllNotesMethod().observe(this, observer);
    }

    private void setRecyclerViewMethod(List<NotesEntity> notesEntityList) {
        RecyclerView recyclerView = activityNotesBinding.notesListRecyclerView;
        NotesAdapter notesAdapter = new NotesAdapter(notesEntityList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(notesAdapter);
    }

    private void setOnClickListenerMethod() {
        activityNotesBinding.newNoteFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == activityNotesBinding.newNoteFloatingActionButton) {
            startActivity(new Intent(NotesActivity.this, AddEditNoteActivity.class));
        }
    }
}
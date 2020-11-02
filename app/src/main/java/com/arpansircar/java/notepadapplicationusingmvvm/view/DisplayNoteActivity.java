package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityDisplayNoteBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;
import com.arpansircar.java.notepadapplicationusingmvvm.viewmodel.DisplayNoteActivityViewModel;

public class DisplayNoteActivity extends AppCompatActivity {

    private ActivityDisplayNoteBinding activityDisplayNoteBinding;
    private DisplayNoteActivityViewModel displayNoteActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisplayNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_note);
        initializeViewModel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startObserver();
    }

    private void initializeViewModel() {
        displayNoteActivityViewModel = new ViewModelProvider(this).get(DisplayNoteActivityViewModel.class);
    }

    private void startObserver() {
        final Observer<NotesEntity> notesActivityObserver = this::setNoteInActivity;
        displayNoteActivityViewModel.selectNoteMethod(getIntentData()).observe(this, notesActivityObserver);
    }

    private void setNoteInActivity(NotesEntity notesEntity) {
        activityDisplayNoteBinding.titleTextView.setText(notesEntity.getTitle());
        activityDisplayNoteBinding.contentTextView.setText(notesEntity.getContent());
    }

    private int getIntentData() {
        Intent intent = getIntent();
        return intent.getIntExtra("noteID", -1);
    }

}
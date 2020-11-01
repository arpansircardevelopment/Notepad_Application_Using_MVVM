package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityNotesBinding;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityNotesBinding activityNotesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesBinding = DataBindingUtil.setContentView(this, R.layout.activity_notes);
        setOnClickListenerMethod();
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
package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.ActivityNotesBinding;

public class NotesActivity extends AppCompatActivity {

    private ActivityNotesBinding activityNotesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesBinding = DataBindingUtil.setContentView(this, R.layout.activity_notes);
    }
}
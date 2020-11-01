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

public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddEditNoteBinding activityAddEditNoteBinding;
    private AddEditNoteActivityViewModel addEditNoteActivityViewModel;
    private static final String TAG = "AddEditNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddEditNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note);
        addEditNoteActivityViewModel = new ViewModelProvider(this).get(AddEditNoteActivityViewModel.class);
        setOnClickListenerMethod();
    }

    private void setOnClickListenerMethod() {
        activityAddEditNoteBinding.doneFloatingActionButton.setOnClickListener(this);
    }

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

    private void hideKeyboardMethod() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean checkIfEditTextBlank() {
        if (activityAddEditNoteBinding.titleEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initializeSaveMethod(String title, String content, String date) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setTitle(title);
        notesEntity.setContent(content);
        notesEntity.setDate(date);

        addEditNoteActivityViewModel.insertNoteMethod(notesEntity);
        finish();
    }
}
package com.arpansircar.java.notepadapplicationusingmvvm.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.arpansircar.java.notepadapplicationusingmvvm.model.INotesActivity;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final INotesActivity iNotesActivity;

    public SwipeToDeleteCallback(INotesActivity iNotesActivity) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.iNotesActivity = iNotesActivity;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        iNotesActivity.onNoteSwiped(position);
    }
}
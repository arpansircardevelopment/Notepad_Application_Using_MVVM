package com.arpansircar.java.notepadapplicationusingmvvm.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.IndividualItemsLayoutBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<NotesEntity> notesEntityList;

    public NotesAdapter(List<NotesEntity> notesEntityList) {
        this.notesEntityList = notesEntityList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IndividualItemsLayoutBinding individualItemsLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.individual_items_layout,
                parent,
                false
        );

        return new NotesViewHolder(individualItemsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesEntity currentNote = notesEntityList.get(position);
        holder.individualItemsLayoutBinding.setNoteDetails(currentNote);
    }

    @Override
    public int getItemCount() {
        if (notesEntityList != null) {
            return notesEntityList.size();
        } else {
            return 0;
        }
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        private final IndividualItemsLayoutBinding individualItemsLayoutBinding;

        public NotesViewHolder(@NonNull IndividualItemsLayoutBinding individualItemsLayoutBinding) {
            super(individualItemsLayoutBinding.getRoot());
            this.individualItemsLayoutBinding = individualItemsLayoutBinding;
        }
    }

}

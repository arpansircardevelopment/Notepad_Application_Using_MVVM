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

/**
 * The NotesAdapter class is used for setting up the RecyclerView.
 * The class accepts a List of NotesEntity objects as the argument for it's constructor.
 * This list is used to populate the RecyclerView with newer objects.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<NotesEntity> notesEntityList;

    public NotesAdapter(List<NotesEntity> notesEntityList) {
        this.notesEntityList = notesEntityList;
    }

    /*The onCreateViewHolder() method configures and returns the views for all the different objects present in the List.*/
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

    /*The onBindViewHolder(...) method receives the views individually from the NotesViewHolder class.
     * This method then binds all these individual views to the list.*/
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesEntity currentNote = notesEntityList.get(position);
        holder.individualItemsLayoutBinding.setNoteDetails(currentNote);
    }

    /*The getItemCount() method simply returns the value denoting the size that our RecyclerView is supposed to be. */
    @Override
    public int getItemCount() {
        if (notesEntityList != null) {
            return notesEntityList.size();
        } else {
            return 0;
        }
    }

    /*The NotesViewHolder class is used to hold each of the views for each of the objects present in the notesEntitiesList list.*/
    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        private final IndividualItemsLayoutBinding individualItemsLayoutBinding;

        public NotesViewHolder(@NonNull IndividualItemsLayoutBinding individualItemsLayoutBinding) {
            super(individualItemsLayoutBinding.getRoot());
            this.individualItemsLayoutBinding = individualItemsLayoutBinding;
        }
    }

}

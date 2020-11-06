package com.arpansircar.java.notepadapplicationusingmvvm.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.arpansircar.java.notepadapplicationusingmvvm.R;
import com.arpansircar.java.notepadapplicationusingmvvm.databinding.IndividualItemsLayoutBinding;
import com.arpansircar.java.notepadapplicationusingmvvm.model.INotesActivity;
import com.arpansircar.java.notepadapplicationusingmvvm.room.NotesEntity;

import java.util.List;

/**
 * The NotesAdapter class is used for setting up the RecyclerView.
 * The class accepts a List of NotesEntity objects as the argument for it's constructor.
 * This list is used to populate the RecyclerView with newer objects.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<NotesEntity> notesEntityList;
    private final INotesActivity iNotesActivity;
    private static MutableLiveData<Integer> recyclerViewSize;

    public NotesAdapter(List<NotesEntity> notesEntityList, INotesActivity iNotesActivity) {
        this.notesEntityList = notesEntityList;
        this.iNotesActivity = iNotesActivity;
        recyclerViewSize = new MutableLiveData<>();
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

        return new NotesViewHolder(individualItemsLayoutBinding, iNotesActivity);
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
            recyclerViewSize.postValue(notesEntityList.size());
            return notesEntityList.size();
        } else {
            recyclerViewSize.postValue(0);
            return 0;
        }
    }

    public static MutableLiveData<Integer> getRecyclerViewSize() {
        return recyclerViewSize;
    }

    /*The NotesViewHolder class is used to hold each of the views for each of the objects present in the notesEntitiesList list.*/
    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final IndividualItemsLayoutBinding individualItemsLayoutBinding;
        private final INotesActivity iNotesActivity;

        public NotesViewHolder(@NonNull IndividualItemsLayoutBinding individualItemsLayoutBinding, INotesActivity iNotesActivity) {
            super(individualItemsLayoutBinding.getRoot());
            this.individualItemsLayoutBinding = individualItemsLayoutBinding;
            individualItemsLayoutBinding.getRoot().setOnClickListener(this);
            this.iNotesActivity = iNotesActivity;
        }

        /*The onClick(...) method is used when we click a particular view present in the RecyclerView.
         * When the user clicks on a particular note, this method stores the object for that particular note from the list.
         * Next, the noteID is acquired from the object and is sent to the NotesActivity via the INotesActivity interface.*/
        @Override
        public void onClick(View view) {
            iNotesActivity.onNoteClicked(notesEntityList.get(getAdapterPosition()));
        }
    }
}
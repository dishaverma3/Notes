package com.example.notes.ui.NoteDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.util.AppExecutors;


public class NoteDetailsViewModel extends AndroidViewModel{

    NotesDatabase database;
    NotesEntity item;
    MutableLiveData<NotesEntity> noteData = new MutableLiveData<>();

    public NoteDetailsViewModel(@NonNull Application application) {
        super(application);

        database = NotesDatabase.getInstance(application.getApplicationContext());
    }

    public void getData(final Long noteId) {

        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                item = database.notesDao().getNotes(noteId);
            }
        });

        new AppExecutors().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                noteData.setValue(item);
            }
        });
    }


    public void delete(final Long id) {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.notesDao().deleteNote(id);

            }
        });
        new AppExecutors().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                noteData.setValue(item);
            }
        });
    }
}

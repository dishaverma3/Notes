package com.example.notes.ui.ViewAllNotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class ViewNotesViewModel extends AndroidViewModel {

    NotesDatabase database;
    List<NotesEntity> list = new ArrayList<>();
    MutableLiveData<Boolean> isListSet = new MutableLiveData<>();

    public ViewNotesViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(application.getApplicationContext());
    }

    void getAllNotes()
    {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                list = database.notesDao().getAllNotes();
            }
        });

        new AppExecutors().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                isListSet.setValue(true);
            }
        });
    }

    public List<NotesEntity> deleteNote(final NotesEntity listItem) {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.notesDao().deleteNote(listItem.getId());
                getAllNotes();

                new AppExecutors().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
        return list;
    }
}

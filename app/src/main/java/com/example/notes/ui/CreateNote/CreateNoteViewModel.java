package com.example.notes.ui.CreateNote;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.util.AppExecutors;

public class CreateNoteViewModel extends AndroidViewModel{

    NotesDatabase database;
    MutableLiveData<Boolean> isItemInserted = new MutableLiveData<>();

    public CreateNoteViewModel(@NonNull Application application) {
        super(application);

        database = NotesDatabase.getInstance(application.getApplicationContext());
    }

    void setData(final String title, final String content) {
        Log.d("viewmodel create", "INSERT: TIME -- "+System.currentTimeMillis()/1000);
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.notesDao().insertNote(new NotesEntity(title,String.valueOf(System.currentTimeMillis()/1000),content));
            }
        });


        new AppExecutors().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                isItemInserted.setValue(true);
            }
        });

    }

    public void updateData(final NotesEntity notes) {
        Log.d("viewmodel create", "UPDATE: TIME -- "+System.currentTimeMillis()/1000);
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.notesDao().updateNotes(notes);
            }
        });
    }
}

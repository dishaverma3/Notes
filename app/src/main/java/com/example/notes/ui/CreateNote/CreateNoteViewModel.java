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
    MutableLiveData<Long> isItemInserted = new MutableLiveData<>();
    Long id;

    public CreateNoteViewModel(@NonNull Application application) {
        super(application);

        database = NotesDatabase.getInstance(application.getApplicationContext());
    }

    void setData(final String title, final String content) {
        Log.d("viewmodel create", "INSERT: TIME -- "+System.currentTimeMillis()/1000);
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                id = database.notesDao().insertNote(new NotesEntity(title,String.valueOf(System.currentTimeMillis()/1000),content));
                Log.d(" id id ", "run: ID - "+id);

                new AppExecutors().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        isItemInserted.setValue(id);
                    }
                });
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

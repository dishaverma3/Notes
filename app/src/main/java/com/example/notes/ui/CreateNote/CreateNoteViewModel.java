package com.example.notes.ui.CreateNote;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;

public class CreateNoteViewModel extends AndroidViewModel{

    NotesDatabase database;

    public CreateNoteViewModel(@NonNull Application application) {
        super(application);

        database = NotesDatabase.getInstance(application.getApplicationContext());
    }

    public void setData(String title, String content) {
        database.notesDao().insertNote(new NotesEntity(title,String.valueOf(System.currentTimeMillis()/1000),content));
        Log.d("viewmodel create", "setData: TIME -- "+String.valueOf(System.currentTimeMillis()/1000));
    }
}

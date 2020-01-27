package com.example.notes.ui.CreateNote;

import android.app.Application;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;

import java.util.logging.Handler;

public class CreateNoteViewModel extends AndroidViewModel{

    NotesDatabase database;
    MutableLiveData<Boolean> saved = new MutableLiveData<>();

    public CreateNoteViewModel(@NonNull Application application) {
        super(application);

        database = NotesDatabase.getInstance(application.getApplicationContext());
    }

    void setData(final String title, final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.notesDao().insertNote(new NotesEntity(title,String.valueOf(System.currentTimeMillis()/1000),content));

                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        saved.setValue(true);
                    }
                });

            }
        }).start();
        Log.d("viewmodel create", "setData: TIME -- "+System.currentTimeMillis()/1000);
    }
}

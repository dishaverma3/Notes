package com.example.notes.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotesEntity.class}, version = 2, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static NotesDatabase INSTANCE = null;
    private static Object lock = new Object();

    public static NotesDatabase getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (lock)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),NotesDatabase.class,"NotesDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}

package com.example.notes;


import android.os.Build;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.notes.data.local.db.NotesDao;
import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;

@Config(sdk = {Build.VERSION_CODES.P,Build.VERSION_CODES.O_MR1})
@RunWith(RobolectricTestRunner.class)
public class DatabaseUnitTest {

    private NotesDatabase database;
    private NotesDao dao;

    @Before
    public void intializeDatabase(){
        database = Room.databaseBuilder(ApplicationProvider.getApplicationContext(),NotesDatabase.class,"DATABASE").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        dao = database.notesDao();
    }

    @Test
    public void testDatabaseOperations(){
        NotesEntity notesEntity = new NotesEntity("Shopping",String.valueOf(System.currentTimeMillis()/1000),"jsdbhcdbksjc");
        dao.insertNote(notesEntity);

        List<NotesEntity> notesEntities = dao.getAllNotes();
        assert(notesEntities.get(0).getTitle().equals("Shopping"));
    }

    @After
    public void closeDb() throws IOException{
        database.close();
    }
}

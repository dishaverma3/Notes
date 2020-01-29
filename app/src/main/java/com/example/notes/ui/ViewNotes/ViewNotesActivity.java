package com.example.notes.ui.ViewNotes;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.ui.CreateNote.CreateNoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.notes.R;

import java.util.List;

public class ViewNotesActivity extends AppCompatActivity {

    ViewNotesViewModel viewModel;
    RecyclerView recyclerView;
    ViewNotesAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        viewModel.getAllNotes();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewNotesActivity.this, CreateNoteActivity.class));
            }
        });

        viewModel.isListSet.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    setRecyclerView(viewModel.list);
                }
            }
        });
    }

    private void setRecyclerView(List<NotesEntity> list) {
        adapter = new ViewNotesAdapter(list,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(ViewNotesViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

    }

}

package com.example.notes.ui.ViewAllNotes;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.ui.CreateNote.CreateNoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.notes.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ViewNotesActivity extends AppCompatActivity {

    ViewNotesViewModel viewModel;
    RecyclerView recyclerView;
    ViewNotesAdapter adapter;
    LinearLayout noListView;
    LottieAnimationView arrowView;
    FloatingActionButton addNotesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        addNotesButton.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getAllNotes();
    }

    public void setRecyclerView(List<NotesEntity> list) {

        if(list.size() != 0)
        {
            Collections.sort(list);
            Collections.reverse(list);

            adapter = new ViewNotesAdapter(viewModel,list,getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);


        }else {

            recyclerView.setVisibility(View.GONE);
            noListView.setVisibility(View.VISIBLE);
            arrowView.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(ViewNotesViewModel.class);
        addNotesButton = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recycler_view);
        noListView = findViewById(R.id.noListView);
        arrowView = findViewById(R.id.arrowAnimation);

    }

}

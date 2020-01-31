package com.example.notes.ui.CreateNote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.ui.ViewAllNotes.ViewNotesActivity;
import com.example.notes.ui.NoteDetails.NoteDetailActivity;
import com.example.notes.util.AppExecutors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CreateNoteActivity extends AppCompatActivity {

    CreateNoteViewModel viewModel;
    TextInputEditText titleEditText;
    EditText contentEditText;
    private FloatingActionButton saveButton;
    Toolbar toolbar;
    NotesEntity noteDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        init();

        if (getIntent().getExtras() != null)
        {
            getBundle();
        }

        viewModel.isItemInserted.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(CreateNoteActivity.this, NoteDetailActivity.class);

                bundle.putLong("notes_id", aLong);

                intent.putExtras(bundle);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                new AppExecutors().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Note Saved",Snackbar.LENGTH_LONG);
                        (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        snackbar.show();
                    }
                });

                finish();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = titleEditText.getText().toString().trim();
                String contentText = contentEditText.getText().toString().trim();
                if(!(TextUtils.isEmpty(contentText)) || !(TextUtils.isEmpty(titleText)))
                {
                    if(noteDetails != null)
                    {
                        if(!noteDetails.getTitle().equals(titleText))
                            noteDetails.setTitle(titleText);
                        if(!noteDetails.getContent().equals(contentText))
                            noteDetails.setContent(contentText);

                        noteDetails.setDate(System.currentTimeMillis()/1000+"");

                        viewModel.updateData(noteDetails);
                        startActivity(new Intent(CreateNoteActivity.this,ViewNotesActivity.class));
                    }else viewModel.setData(titleText, contentText);
                }else{
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Notes can not be empty",Snackbar.LENGTH_LONG);
                    (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    snackbar.show();
                }
            }
        });
    }


    private void getBundle() {
        HashMap<String,String> notesMap;

        notesMap = (HashMap<String,String>) getIntent().getExtras().getSerializable("notes_details");
        noteDetails = new Gson().fromJson(notesMap.get("notesDetails"),NotesEntity.class);

        if(noteDetails != null)
        {
            titleEditText.setText(noteDetails.getTitle());
            contentEditText.setText(noteDetails.getContent());
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        viewModel = new ViewModelProvider(this).get(CreateNoteViewModel.class);
        titleEditText = findViewById(R.id.title_edittext);
        contentEditText = findViewById(R.id.content_edittext);
        saveButton = findViewById(R.id.save_button);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow =  ContextCompat.getDrawable(getApplicationContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }else return super.onOptionsItemSelected(item);
    }
}

package com.example.notes.ui.CreateNote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.ui.ViewNotes.ViewNotesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CreateNoteActivity extends AppCompatActivity {

    CreateNoteViewModel viewModel;
    TextInputEditText title;
    EditText content;
    private FloatingActionButton save;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        init();

        viewModel.saved.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    startActivity(new Intent(CreateNoteActivity.this, ViewNotesActivity.class));

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Note Saved",Snackbar.LENGTH_LONG);
                    (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    snackbar.show();

                    finish();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setData(title.getText().toString(), content.getText().toString());
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        viewModel = new ViewModelProvider(this).get(CreateNoteViewModel.class);
        title = findViewById(R.id.title_edittext);
        content = findViewById(R.id.content_edittext);
        save = findViewById(R.id.save_button);

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

package com.example.notes.ui.ViewSingleNote;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notes.R;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.ui.CreateNote.CreateNoteActivity;
import com.example.notes.ui.ViewAllNotes.ViewNotesActivity;
import com.google.android.material.internal.NavigationMenu;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ViewSingleNoteActivity extends AppCompatActivity {

    TextView titleView, contentView, dateView;
    Long noteId;
    FabSpeedDial fabSpeedDial;
    NotesEntity currentNotesDetails;
    SingleNoteViewModel viewModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_note);

        init();

        getBundle();

        getData();


        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_edit)
                {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);

                    HashMap<String,String> notesMap = new HashMap<>();
                    notesMap.put("notesDetails", new Gson().toJson(currentNotesDetails,NotesEntity.class));

                    bundle.putSerializable("notes_details",notesMap);

                    intent.putExtras(bundle);
                    startActivity(intent);

                    return true;
                }else if(menuItem.getItemId() == R.id.action_delete)
                {
                    viewModel.delete(currentNotesDetails.getId());
                    startActivity(new Intent(ViewSingleNoteActivity.this, ViewNotesActivity.class));
                    return true;
                }else
                    return super.onMenuItemSelected(menuItem);
            }


        });

    }

    private void getData() {
        viewModel.getData(noteId);

        viewModel.noteData.observe(this, new Observer<NotesEntity>() {
            @Override
            public void onChanged(NotesEntity notesEntity) {
                currentNotesDetails = notesEntity;
                setValue(notesEntity);
            }
        });
    }

    private void setValue(NotesEntity notesEntity) {

        titleView.setText(notesEntity.getTitle());
        contentView.setText(notesEntity.getContent());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        sdf.setTimeZone(Calendar.getInstance().getTimeZone());
        String dateString = sdf.format(new Date(Long.parseLong(notesEntity.getDate()) * 1000));

        dateView.setText(dateString);
    }

    private void getBundle() {
        noteId = getIntent().getExtras().getLong("notes_id");
    }

    private void init() {
        titleView = findViewById(R.id.title_text);
        contentView = findViewById(R.id.content_text);
        dateView = findViewById(R.id.date_text);
        toolbar = findViewById(R.id.toolbar);
        fabSpeedDial = findViewById(R.id.fab_speed_dial);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow =  ContextCompat.getDrawable(getApplicationContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        viewModel = new ViewModelProvider(this).get(SingleNoteViewModel.class);

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

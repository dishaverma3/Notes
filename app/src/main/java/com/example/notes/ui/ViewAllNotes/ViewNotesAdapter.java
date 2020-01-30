package com.example.notes.ui.ViewAllNotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.local.db.NotesDatabase;
import com.example.notes.data.local.db.NotesEntity;
import com.example.notes.ui.NoteDetails.NoteDetailActivity;
import com.example.notes.util.AppExecutors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ViewNotesAdapter extends RecyclerView.Adapter<ViewNotesAdapter.ViewHolder> {

    private List<NotesEntity> list;
    private Context context;
    private NotesDatabase database;

    public ViewNotesAdapter(List<NotesEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_view_notes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewNotesAdapter.ViewHolder holder, final int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, content, date;
        CircleImageView deleteButton;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            content = itemView.findViewById(R.id.content_text);
            date = itemView.findViewById(R.id.date_text);
            layout = itemView.findViewById(R.id.listitem_layout);

            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        void bind(final int position) {
            final NotesEntity listItem = list.get(position);
            database = NotesDatabase.getInstance(context);

            title.setText(listItem.getTitle());
            content.setText(listItem.getContent());
            content.setMaxLines(1);
            content.setEllipsize(TextUtils.TruncateAt.END);

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            sdf.setTimeZone(Calendar.getInstance().getTimeZone());
            String dateString = sdf.format(new Date(Long.parseLong(listItem.getDate()) * 1000));

            date.setText(dateString);

            int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);

            if(position >= 0 && position < 9)
                layout.setCardBackgroundColor(androidColors[position]);
            else layout.setCardBackgroundColor(androidColors[position%9]);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AppExecutors().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            database.notesDao().deleteNote(listItem.getId());
                            List<NotesEntity> updatedList = database.notesDao().getAllNotes();
                            setList(updatedList);

                            new AppExecutors().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemRemoved(position);
                                }
                            });
                        }
                    });
                }
            });

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context.getApplicationContext(), NoteDetailActivity.class);

                    bundle.putLong("notes_id", listItem.getId());
                    intent.putExtras(bundle);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setList(List<NotesEntity> updatedList) {
        list = updatedList;
    }
}

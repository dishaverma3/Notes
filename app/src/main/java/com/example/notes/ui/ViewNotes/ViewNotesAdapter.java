package com.example.notes.ui.ViewNotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.local.db.NotesEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewNotesAdapter extends RecyclerView.Adapter<ViewNotesAdapter.ViewHolder> {

    private List<NotesEntity> list;
    private Context context;

    public ViewNotesAdapter(List<NotesEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_view_notes,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewNotesAdapter.ViewHolder holder, int position) {
        NotesEntity listItem = list.get(position);

        holder.title.setText(listItem.getTitle());
        holder.content.setText(listItem.getContent());
        holder.date.setText(listItem.getDate());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, content, date;
        CircleImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            content = itemView.findViewById(R.id.content_text);
            date = itemView.findViewById(R.id.date_text);

            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}

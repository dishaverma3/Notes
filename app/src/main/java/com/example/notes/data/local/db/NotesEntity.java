package com.example.notes.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notes_id")
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String date;

    @NonNull
    private String content;

    public NotesEntity(@NonNull String title, @NonNull String date, @NonNull String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }
}

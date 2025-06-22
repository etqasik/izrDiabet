package com.example.izrdiabet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private List<DiaryEntry> entries = new ArrayList<>();
    private final OnEntryClickListener listener;

    public interface OnEntryClickListener {
        void onEntryClick(DiaryEntry entry);
        void onDeleteClick(DiaryEntry entry);
    }

    public DiaryAdapter(OnEntryClickListener listener) {
        this.listener = listener;
    }

    public void updateEntries(List<DiaryEntry> newEntries) {
        this.entries = newEntries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary_entry, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        DiaryEntry entry = entries.get(position);
        holder.bind(entry, listener);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class DiaryViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeTextView;
        private final TextView sugarTextView;
        private final TextView breadUnitsTextView;
        private final TextView insulinTextView;
        private final TextView notesTextView;
        private final ImageButton deleteButton;

        public DiaryViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            sugarTextView = itemView.findViewById(R.id.sugarTextView);
            breadUnitsTextView = itemView.findViewById(R.id.breadUnitsTextView);
            insulinTextView = itemView.findViewById(R.id.insulinTextView);
            notesTextView = itemView.findViewById(R.id.notesTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(DiaryEntry entry, OnEntryClickListener listener) {
            timeTextView.setText(entry.getTime());
            sugarTextView.setText(String.format("%.1f", entry.getSugarLevel()));
            breadUnitsTextView.setText(String.format("%.1f", entry.getBreadUnits()));
            insulinTextView.setText(String.format("%.1f", entry.getInsulin()));
            notesTextView.setText(entry.getNotes());

            itemView.setOnClickListener(v -> listener.onEntryClick(entry));
            deleteButton.setOnClickListener(v -> listener.onDeleteClick(entry));
        }
    }
}
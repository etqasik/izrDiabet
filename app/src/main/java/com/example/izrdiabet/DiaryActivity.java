package com.example.izrdiabet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity implements DiaryAdapter.OnEntryClickListener {

    private DiaryViewModel diaryViewModel;
    private DiaryAdapter adapter;
    private FloatingActionButton addButton;
    private TextView dateTextView;
    private Button prevDayButton, nextDayButton;
    private Calendar currentDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary);

        diaryViewModel = new ViewModelProvider(this).get(DiaryViewModel.class);
        initViews();
        setupRecyclerView();
        updateDateDisplay();
        observeData();
        loadEntriesForCurrentDate();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onEntryClick(DiaryEntry entry) {
        showEditEntryDialog(entry);
    }

    @Override
    public void onDeleteClick(DiaryEntry entry) {
        new AlertDialog.Builder(this)
                .setTitle("Удаление записи")
                .setMessage("Вы уверены, что хотите удалить эту запись?")
                .setPositiveButton("Удалить", (dialog, which) -> diaryViewModel.delete(entry))
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiaryAdapter(this);
        recyclerView.setAdapter(adapter);

        addButton = findViewById(R.id.addButton);
        dateTextView = findViewById(R.id.dateTextView);
        prevDayButton = findViewById(R.id.prevDayButton);
        nextDayButton = findViewById(R.id.nextDayButton);

        addButton.setOnClickListener(v -> showAddEntryDialog());
        prevDayButton.setOnClickListener(v -> {
            currentDate.add(Calendar.DATE, -1);
            updateDateDisplay();
            loadEntriesForCurrentDate();
        });
        nextDayButton.setOnClickListener(v -> {
            currentDate.add(Calendar.DATE, 1);
            updateDateDisplay();
            loadEntriesForCurrentDate();
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiaryAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void observeData() {
        diaryViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDateDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateTextView.setText(sdf.format(currentDate.getTime()));
    }

    private void loadEntriesForCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String date = sdf.format(currentDate.getTime());

        diaryViewModel.getEntriesByDate(date).observe(this, entries -> {
            if (entries != null) {
                adapter.updateEntries(entries);
            }
        });
    }

    private void showAddEntryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_entry, null);
        builder.setView(view);
        builder.setTitle("Добавить запись");

        EditText timeEditText = view.findViewById(R.id.timeEditText);
        EditText sugarEditText = view.findViewById(R.id.sugarEditText);
        EditText breadUnitsEditText = view.findViewById(R.id.breadUnitsEditText);
        EditText insulinEditText = view.findViewById(R.id.insulinEditText);
        EditText notesEditText = view.findViewById(R.id.notesEditText);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeEditText.setText(timeFormat.format(new Date()));

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String date = dateFormat.format(currentDate.getTime());

                DiaryEntry entry = new DiaryEntry(
                        date,
                        timeEditText.getText().toString(),
                        Float.parseFloat(sugarEditText.getText().toString()),
                        Float.parseFloat(breadUnitsEditText.getText().toString()),
                        Float.parseFloat(insulinEditText.getText().toString()),
                        notesEditText.getText().toString()
                );

                diaryViewModel.insert(entry);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Проверьте правильность введенных чисел", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void showEditEntryDialog(DiaryEntry entry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_entry, null);
        builder.setView(view);
        builder.setTitle("Редактировать запись");

        EditText timeEditText = view.findViewById(R.id.timeEditText);
        EditText sugarEditText = view.findViewById(R.id.sugarEditText);
        EditText breadUnitsEditText = view.findViewById(R.id.breadUnitsEditText);
        EditText insulinEditText = view.findViewById(R.id.insulinEditText);
        EditText notesEditText = view.findViewById(R.id.notesEditText);

        timeEditText.setText(entry.getTime());
        sugarEditText.setText(String.valueOf(entry.getSugarLevel()));
        breadUnitsEditText.setText(String.valueOf(entry.getBreadUnits()));
        insulinEditText.setText(String.valueOf(entry.getInsulin()));
        notesEditText.setText(entry.getNotes());

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            try {
                entry.setTime(timeEditText.getText().toString());
                entry.setSugarLevel(Float.parseFloat(sugarEditText.getText().toString()));
                entry.setBreadUnits(Float.parseFloat(breadUnitsEditText.getText().toString()));
                entry.setInsulin(Float.parseFloat(insulinEditText.getText().toString()));
                entry.setNotes(notesEditText.getText().toString());

                diaryViewModel.update(entry);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Проверьте правильность введенных чисел", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
        view.refreshDrawableState();
    }

    public void openMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
package com.example.izrdiabet;

import java.util.UUID;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;


// DiaryEntry.java
@Entity(tableName = "diary_entries") // Аннотация определяет таблицу в БД
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true) // Автоинкрементируемый первичный ключ
    private int id;

    @ColumnInfo(name = "date") // Колонка с датой (формат: "dd.MM.yyyy")
    private String date;

    @ColumnInfo(name = "time") // Колонка с временем (формат: "HH:mm")
    private String time;

    @ColumnInfo(name = "sugar_level") // Уровень сахара (ммоль/л)
    private float sugarLevel;

    @ColumnInfo(name = "bread_units") // Хлебные единицы
    private float breadUnits;

    @ColumnInfo(name = "insulin") // Доза инсулина
    private float insulin;

    @ColumnInfo(name = "notes") // Произвольные заметки
    private String notes;

    public DiaryEntry(String date, String time, float sugarLevel,
                      float breadUnits, float insulin, String notes) {
        this.date = date;
        this.time = time;
        this.sugarLevel = sugarLevel;
        this.breadUnits = breadUnits;
        this.insulin = insulin;
        this.notes = notes;
    }

    // Геттеры и сеттеры для всех полей
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(float sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public float getBreadUnits() {
        return breadUnits;
    }

    public void setBreadUnits(float breadUnits) {
        this.breadUnits = breadUnits;
    }

    public float getInsulin() {
        return insulin;
    }

    public void setInsulin(float insulin) {
        this.insulin = insulin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

package com.example.izrdiabet;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;
import java.util.List;
@Dao public interface DiaryDao {
    @Insert
    long insert(DiaryEntry entry);

    @Update
    int update(DiaryEntry entry);

    @Delete
    int delete(DiaryEntry entry);

    @Query("SELECT * FROM diary_entries WHERE date = :date ORDER BY time")
    LiveData<List<DiaryEntry>> getEntriesByDate(String date);

    @Query("SELECT * FROM diary_entries ORDER BY date DESC, time DESC")
    LiveData<List<DiaryEntry>> getAllEntries();
}

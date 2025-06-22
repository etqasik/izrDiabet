package com.example.izrdiabet;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiaryRepository {
    private final DiaryDao diaryDao;
    private final ExecutorService executor;

    public DiaryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        diaryDao = db.diaryDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<DiaryEntry>> getAllEntries() {
        return diaryDao.getAllEntries();
    }

    public LiveData<List<DiaryEntry>> getEntriesByDate(String date) {
        return diaryDao.getEntriesByDate(date);
    }

    public void insert(DiaryEntry entry, InsertCallback callback) {
        executor.execute(() -> {
            try {
                long id = diaryDao.insert(entry);
                callback.onInsertComplete(id != -1);
            } catch (Exception e) {
                callback.onInsertComplete(false);
            }
        });
    }

    public void update(DiaryEntry entry, UpdateCallback callback) {
        executor.execute(() -> {
            try {
                int count = diaryDao.update(entry);
                callback.onUpdateComplete(count > 0);
            } catch (Exception e) {
                callback.onUpdateComplete(false);
            }
        });
    }

    public void delete(DiaryEntry entry, DeleteCallback callback) {
        executor.execute(() -> {
            try {
                int count = diaryDao.delete(entry);
                callback.onDeleteComplete(count > 0);
            } catch (Exception e) {
                callback.onDeleteComplete(false);
            }
        });
    }

    public interface InsertCallback {
        void onInsertComplete(boolean success);
    }

    public interface UpdateCallback {
        void onUpdateComplete(boolean success);
    }

    public interface DeleteCallback {
        void onDeleteComplete(boolean success);
    }
}
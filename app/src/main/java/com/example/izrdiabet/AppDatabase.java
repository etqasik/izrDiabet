package com.example.izrdiabet;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.izrdiabet.DiaryDao;
import com.example.izrdiabet.DiaryEntry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {DiaryEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();

    private static volatile AppDatabase INSTANCE;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "diary_database.db"
                            ).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
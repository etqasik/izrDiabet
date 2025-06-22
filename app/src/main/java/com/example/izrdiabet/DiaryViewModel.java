package com.example.izrdiabet;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    private final DiaryRepository repository;
    private final LiveData<List<DiaryEntry>> allEntries;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public DiaryViewModel(Application application) {
        super(application);
        repository = new DiaryRepository(application);
        allEntries = repository.getAllEntries();
    }

    // Получение всех записей (LiveData)
    public LiveData<List<DiaryEntry>> getAllEntries() {
        return allEntries;
    }

    // Получение сообщений об ошибках
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Добавление новой записи
    public void insert(DiaryEntry entry) {
        repository.insert(entry, new DiaryRepository.InsertCallback() {
            @Override
            public void onInsertComplete(boolean success) {
                if (!success) {
                    errorMessage.postValue("Ошибка при добавлении записи");
                }
            }
        });
    }

    // Обновление записи
    public void update(DiaryEntry entry) {
        repository.update(entry, new DiaryRepository.UpdateCallback() {
            @Override
            public void onUpdateComplete(boolean success) {
                if (!success) {
                    errorMessage.postValue("Ошибка при обновлении записи");
                }
            }
        });
    }

    // Удаление записи
    public void delete(DiaryEntry entry) {
        repository.delete(entry, new DiaryRepository.DeleteCallback() {
            @Override
            public void onDeleteComplete(boolean success) {
                if (!success) {
                    errorMessage.postValue("Ошибка при удалении записи");
                }
            }
        });
    }

    // Получение записей за конкретную дату
    public LiveData<List<DiaryEntry>> getEntriesByDate(String date) {
        return repository.getEntriesByDate(date);
    }
}
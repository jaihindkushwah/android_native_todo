package com.example.todo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void addTask(String title) {
        List<Task> current = tasks.getValue();
        if (current == null) current = new ArrayList<>();
        current.add(new Task(title, false));
        tasks.setValue(current);
    }

    public void toggleTaskDone(int index) {
        List<Task> current = tasks.getValue();
        if (current != null && index >= 0 && index < current.size()) {
            Task t = current.get(index);
            t.setDone(!t.isDone());
            tasks.setValue(current);
        }
    }

    public void removeTask(int index) {
        List<Task> current = tasks.getValue();
        if (current != null && index >= 0 && index < current.size()) {
            current.remove(index);
            tasks.setValue(current);
        }
    }
}

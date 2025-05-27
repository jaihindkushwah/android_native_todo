package com.example.todo;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo.model.Task;
import com.example.todo.view.TaskListView;
import com.example.todo.viewmodel.TaskViewModel;

import java.util.List;

public class MainActivity extends ComponentActivity {

    private TaskViewModel taskViewModel;
    private TaskListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Optional: Set default mode if not specified (-100)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        taskListView = new TaskListView(this);
        setContentView(taskListView);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskListView.setListener(new TaskListView.Listener() {
            @Override
            public void onAddTask(String title) {
                taskViewModel.addTask(title);
            }

            @Override
            public void onToggleTaskDone(int index) {
                taskViewModel.toggleTaskDone(index);
            }

            @Override
            public void onRemoveTask(int index) {
                taskViewModel.removeTask(index);
            }

            @Override
            public void onToggleTheme() {
                int currentMode = AppCompatDelegate.getDefaultNightMode();
                System.out.println("Current theme mode: " + currentMode);

                if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                // Important: recreate activity to apply the new theme
                recreate();
            }
        });

        taskViewModel.getTasks().observe(this, this::renderTasks);
    }

    private void renderTasks(List<Task> tasks) {
        System.out.println("Welcome to India.");
        taskListView.renderTasks(tasks);
    }
}

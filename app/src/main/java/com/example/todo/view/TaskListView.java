package com.example.todo.view;

import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
import android.widget.*;

import androidx.core.content.ContextCompat;

import com.example.todo.model.Task;

import java.util.List;

public class TaskListView extends LinearLayout {

    private final EditText taskInput;
    private final Button addButton;
    private final Button searchButton;
    private final Button toggleThemeButton;
    private final LinearLayout taskListLayout;

    public interface Listener {
        void onAddTask(String title);
        void onToggleTaskDone(int index);
        void onRemoveTask(int index);
        void onToggleTheme();
    }

    private Listener listener;

    public TaskListView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        int padding = (int) (32 * getResources().getDisplayMetrics().density);
        setPadding(padding, padding, padding, padding);

        taskInput = new EditText(context);
        taskInput.setHint("Enter task");
        addView(taskInput);

        addButton = new Button(context);
        addButton.setText("Add Task");
        addView(addButton);

        toggleThemeButton = new Button(context);
        toggleThemeButton.setText("Toggle Theme");
        addView(toggleThemeButton);

        searchButton= new Button(context);
        searchButton.setText("Search");
        addView(searchButton);

        taskListLayout = new LinearLayout(context);
        taskListLayout.setOrientation(VERTICAL);
        addView(taskListLayout);

        setupListeners();
    }

    private void setupListeners() {
        addButton.setOnClickListener(v -> {
            String text = taskInput.getText().toString().trim();
            if (!text.isEmpty() && listener != null) {
                listener.onAddTask(text);
                taskInput.setText("");
            }
        });

        toggleThemeButton.setOnClickListener(v -> {
            if (listener != null) {
            System.out.println("Toggle theme button");
                listener.onToggleTheme();
            }
        });

        searchButton.setOnClickListener(v->{
            System.out.println("Searching is in progress.....");
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void renderTasks(List<Task> tasks) {
        taskListLayout.removeAllViews();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int index = i;

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(HORIZONTAL);
            int padding = (int) (8 * getResources().getDisplayMetrics().density);
            row.setPadding(padding, padding * 2, padding, padding * 2);
            row.setGravity(Gravity.CENTER_VERTICAL);

            TextView titleView = new TextView(getContext());
            titleView.setText(task.getTitle());
            titleView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
            titleView.setTextColor(task.isDone() ?
                    ContextCompat.getColor(getContext(), android.R.color.darker_gray) :
                    ContextCompat.getColor(getContext(), android.R.color.black));

            if (task.isDone()) {
                titleView.setPaintFlags(titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                titleView.setPaintFlags(titleView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            titleView.setOnClickListener(v -> {
                if (listener != null) listener.onToggleTaskDone(index);
            });

            Button deleteBtn = new Button(getContext());
            deleteBtn.setText("X");
            deleteBtn.setOnClickListener(v -> {
                if (listener != null) listener.onRemoveTask(index);
            });

            row.addView(titleView);
            row.addView(deleteBtn);

            taskListLayout.addView(row);
        }
    }
}

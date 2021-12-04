package hw4.todolist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nikolay Yarlychenko
 */
public class TodoList {
    private int id;
    private final List<TodoTask> taskList;
    private String name;

    public TodoList() {
        taskList = new ArrayList<>();
    }

    public TodoList(int id, String name) {
        this.id = id;
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    public void addTask(TodoTask newTask) {
        taskList.add(newTask);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoTask> getTaskList() {
        return taskList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

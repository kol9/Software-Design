package hw4.todolist.dao;

import hw4.todolist.model.TodoList;
import hw4.todolist.model.TodoTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nikolay Yarlychenko
 */
public class TodoListInMemoryDao implements TodoListDao {
    private final AtomicInteger lastListId = new AtomicInteger(0);
    private final AtomicInteger lastTaskId = new AtomicInteger(0);

    ConcurrentHashMap<Integer, TodoList> lists;

    ConcurrentHashMap<Integer, TodoTask> allTasks;

    public TodoListInMemoryDao() {
        lists = new ConcurrentHashMap<>();
        allTasks = new ConcurrentHashMap<>();
    }

    @Override
    public void addTask(int toList, TodoTask newTask) {
        int id = lastTaskId.getAndIncrement();
        newTask.setId(id);
        lists.get(toList).addTask(newTask);
        allTasks.put(id, newTask);
    }

    @Override
    public List<TodoList> getLists() {
        return new ArrayList<>(lists.values());
    }

    @Override
    public void addList(TodoList list) {
        int id = lastListId.getAndIncrement();
        list.setId(id);
        lists.put(list.getId(), list);
    }

    @Override
    public void updateTaskStatus(int taskId) {
        TodoTask task = allTasks.get(taskId);
        task.toggleStatus();
    }


    @Override
    public void deleteList(int id) {
        lists.remove(id);
    }
}

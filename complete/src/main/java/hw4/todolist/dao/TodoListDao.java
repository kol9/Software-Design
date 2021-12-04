package hw4.todolist.dao;

import hw4.todolist.model.TodoList;
import hw4.todolist.model.TodoTask;

import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */
public interface TodoListDao {

    void addTask(int toList, TodoTask newTask);
    List<TodoList> getLists();

    void addList(TodoList list);

    void updateTaskStatus(int taskId);

    void deleteList(int id);
}

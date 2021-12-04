package hw4.todolist.model;

import com.sun.tools.javac.comp.Todo;

/**
 * @author Nikolay Yarlychenko
 */

public class TodoTask {
    public int getParentListId() {
        return parentListId;
    }

    public void setParentListId(int parentListId) {
        this.parentListId = parentListId;
    }

    public void toggleStatus() {
        this.status = this.status == TodoTaskStatus.DONE ? TodoTaskStatus.NOT_DONE : TodoTaskStatus.DONE;
    }

    public enum TodoTaskStatus {
        NOT_DONE(0),
        DONE(1);

        private final int value;

        TodoTaskStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TodoTaskStatus forInt(int isDone) {
            return values()[isDone];
        }
    }

    private int id;
    private int parentListId;
    private String text;
    private TodoTaskStatus status = TodoTaskStatus.NOT_DONE;

    public TodoTask() {

    }

    public TodoTask(String text, int id, TodoTaskStatus status) {
        this.text = text;
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TodoTaskStatus getStatus() {
        return status;
    }

    public void setStatus(TodoTaskStatus status) {
        this.status = status;
    }
}

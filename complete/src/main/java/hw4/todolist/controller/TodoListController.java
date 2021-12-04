package hw4.todolist.controller;

import hw4.todolist.dao.TodoListDao;
import hw4.todolist.model.TodoList;
import hw4.todolist.model.TodoTask;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoListController {

    private final TodoListDao todoListDao;

    public TodoListController(TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    @RequestMapping(value = "/getLists", method = RequestMethod.GET)
    public String getLists(ModelMap map) {
        prepareModelMap(map, todoListDao.getLists());
        return "index";
    }

    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public String addList(@ModelAttribute("list") TodoList list) {
        todoListDao.addList(list);
        return "redirect:/getLists";
    }

    @RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST)
    public String updateTaskStatus(@RequestParam("taskId") int taskId) {
        todoListDao.updateTaskStatus(taskId);
        return "redirect:/getLists";
    }

    @RequestMapping(value = "deleteList", method = RequestMethod.POST)
    public String deleteList(@RequestParam(value = "listId") int listId) {
        todoListDao.deleteList(listId);
        return "redirect:/getLists";
    }

    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public String addTask(
            @RequestParam(value = "toListId") int toListId,
            @ModelAttribute("task") TodoTask task
    ) {
        todoListDao.addTask(toListId, task);
        return "redirect:/getLists";
    }

    private void prepareModelMap(ModelMap map, List<TodoList> todoLists) {
        map.addAttribute("lists", todoLists);
        map.addAttribute("list", new TodoList());
        map.addAttribute("task", new TodoTask());
    }
}

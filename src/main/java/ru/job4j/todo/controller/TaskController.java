package ru.job4j.todo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.filter.SessionChecker;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    public TaskController(TaskService service, PriorityService priorityService, CategoryService categoryService) {
        this.service = service;
        this.priorityService = priorityService;
        this.categoryService = categoryService;
    }


    @GetMapping("table")
    public String mainPageForm(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        List<Task> tasks = service.showAll();
        model.addAttribute("tasks", tasks);
        return "tasks/table";

    }

    @GetMapping("add")
    public String addPageForm(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/add";

    }

    @PostMapping("/create")
    public String addPageAction(@ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        service.addTask(task);
        return "redirect:/tasks/table";
    }


    @GetMapping("formTask/update/{taskId}")
    public String updatePageForm(Model model, @PathVariable("taskId") int id,
                                 HttpSession session) {
        Optional<Task> taskById = service.findById(id);
        SessionChecker.checkSession(model, session);
        model.addAttribute("task", taskById.get());
        return "tasks/update";
    }


    @GetMapping("formTask/{taskId}")
    public String taskForm(Model model, @PathVariable("taskId") int id,
                           HttpSession session) {
        SessionChecker.checkSession(model, session);
        Optional<Task> taskById = service.findById(id);
        model.addAttribute("task", taskById.get());
        return "tasks/task";
    }


    @PostMapping("updateAction")
    public String updateAction(@ModelAttribute Task task, @RequestParam("id") int id) {
        service.updateTask(id, task);
        return "redirect:/tasks/table";
    }

    @GetMapping("formTask/delete/{taskId}")
    public String deleteAction(@PathVariable("taskId") int id) {

        service.deleteTask(id);
        return "redirect:/tasks/table";
    }

    @GetMapping("formTask/done/{taskId}")
    public String doneAction(@PathVariable("taskId") int id) {
        service.setDoneTask(id);
        return "redirect:/tasks/table";
    }

    @GetMapping("old")
    public String showDonePage(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        model.addAttribute("tasks", service.showWithStatus(true));
        return "tasks/old";
    }

    @GetMapping("new")
    public String showNewPage(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        model.addAttribute("tasks", service.showWithStatus(false));
        return "tasks/new";
    }


}



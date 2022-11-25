package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.filter.SessionChecker;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller

public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public String mainPageForm(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        model.addAttribute("tasks", service.showAll());
        return "tasks";

    }

    @GetMapping("/addTask")
    public String addPageForm(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        return "addTask";

    }

    @PostMapping("/createTask")
    public String addPageAction(@ModelAttribute Task task) {
        service.addTask(task);
        return "redirect:/tasks";
    }


    @GetMapping("/formUpdateTask/{taskId}")
    public String updatePageForm(Model model, @PathVariable("taskId") int id,
                                 HttpSession session) {
        Optional<Task> taskById = service.findById(id);
        SessionChecker.checkSession(model, session);
        model.addAttribute("task", taskById.get());
        return "updateTask";
    }

    @GetMapping("/formTask/{taskId}")
    public String taskForm(Model model,  @PathVariable("taskId") int id,
                           HttpSession session) {
        SessionChecker.checkSession(model, session);
        Optional<Task> taskById = service.findById(id);
        model.addAttribute("task", taskById.get());
        return "task";
    }


    @PostMapping("/updateAction")
    public String updateAction(@ModelAttribute Task task, @RequestParam("id") int id) {
        service.updateTask(id, task);
        return "redirect:/tasks";
    }

    @GetMapping("/formDeleteTask/{taskId}")
    public String deleteAction(@PathVariable("taskId") int id) {

        service.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/formDoneTask/{taskId}")
    public String doneAction(@PathVariable("taskId") int id) {
        service.setDoneTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/oldTasks")
    public String showDonePage(Model model,  HttpSession session) {
        SessionChecker.checkSession(model, session);
        model.addAttribute("tasks", service.showWithStatus(true));
        return "oldTasks";
    }

    @GetMapping("/newTasks")
    public String showNewPage(Model model, HttpSession session) {
        SessionChecker.checkSession(model, session);
        model.addAttribute("tasks", service.showWithStatus(false));
        return "newTasks";
    }


}



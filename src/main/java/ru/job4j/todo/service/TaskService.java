package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.List;

@Service
public class TaskService {

    public TaskService(TaskStore store) {
        this.store = store;
    }


    private final TaskStore store;


    public void addTask(Task task) {
        store.create(task);
    }

    public void updateTask(int id, Task task) {
        store.update(id, task);
    }

    public void deleteTask(int id) {
        store.delete(id);
    }

    public List<Task> showAll() {
        return store.showALL();
    }

    public List<Task> showDone() {
        return store.showDone();
    }

    public List<Task> showNew() {
        return store.showNew();
    }

    public Task findById(int id) {
        return store.findById(id);
    }
}

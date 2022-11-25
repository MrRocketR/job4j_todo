package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.List;
import java.util.Optional;

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

    public List<Task> showWithStatus(boolean status) {
        return store.showWithStatus(status);
    }

    public void setDoneTask(int id) {
        store.done(id);
    }

    public Optional<Task> findById(int id) {
        return store.findById(id);
    }
}

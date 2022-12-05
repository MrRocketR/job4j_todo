package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    public TaskService(TaskRepository store) {
        this.store = store;
    }


    private final TaskRepository store;



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
        return store.showAllFetchQuery();
    }

    public List<Task> showWithStatus(boolean status) {
        return store.showWitStatusFetchQuery(status);
    }

    public void setDoneTask(int id) {
        store.done(id);
    }

    public Optional<Task> findById(int id) {
        return store.findById(id);
    }


}

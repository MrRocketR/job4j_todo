package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.TaskRepository;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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

    public List<Task> showWithStatusAndTimeZone(boolean status, User user) {
        ZoneId zoneId = ZoneId.of(user.getZone());
        ZoneId def = ZoneId.systemDefault();
        TimeZone zone = TimeZone.getTimeZone(user.getZone());
        List<Task> list =  store.showWitStatusFetchQuery(status);
        list.forEach(task -> task.setCreated(task.getCreated()
                .atZone(def).withZoneSameInstant(zoneId)
                .toLocalDateTime()));
        return list;
    }

    public void setDoneTask(int id) {
        store.done(id);
    }

    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    public List<Task> showAllWithTimeZone(User user) {
        ZoneId zoneId = ZoneId.of(user.getZone());
        ZoneId def = ZoneId.systemDefault();
        TimeZone zone = TimeZone.getTimeZone(user.getZone());
        List<Task> list = store.showAllFetchQuery();
        list.forEach(task -> task.setCreated(task.getCreated()
                .atZone(def).withZoneSameInstant(zoneId)
                .toLocalDateTime()));
        return list;

    }


}

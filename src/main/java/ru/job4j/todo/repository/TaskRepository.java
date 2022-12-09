package ru.job4j.todo.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TaskRepository {


    private final CrudRepository crudRepository;


    /**
     * Save into DB.
     * @param task task.
     * @return User with id.
     */

    public Task create(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    /**
     * Update task.
     *
     * @param task
     */


    public void update(int taskId, Task task) {
        crudRepository.run("UPDATE Task as t SET  t.description = :fDescription "
                        + "WHERE  t.id = :fId",
                Map.of("fId", taskId,
                        "fDescription", task.getDescription())
        );

    }

    /**
     * Delete Task by id.
     *
     * @param taskId ID
     */

    public void delete(int taskId) {
        crudRepository.run("DELETE Task as t where t.id = :fId",
                Map.of("fId", taskId)
        );

    }

    /**
     * Set Task. done = true by id.
     *
     * @param taskId ID
     */

    public void done(int taskId) {
        crudRepository.run("UPDATE Task as t SET t.done = true where t.id = :fId",
                Map.of("fId", taskId)
        );

    }

    /***
     * List of task sorted by id.
     * @return List<Tasks>
     */

    public List<Task> showAll() {
        return crudRepository.query("from Task order by id", Task.class);
    }

    /***
     * List of Old/New tasks sorted by id.
     * @return List<Tasks>
     */

    public List<Task> showWithStatus(boolean status) {
        return crudRepository.query("from Task where done = :fDone order by id", Task.class,
                Map.of("fDone", status));
    }

    /***
     * List of Old/New tasks with priority.
     * @return List<Tasks>
     */
    public List<Task> showWitStatusFetchQuery(boolean status) {
        return crudRepository.query("from Task t join fetch t.priority"
                        + " where t.done = :fDone", Task.class, Map.of("fDone", status));
    }

    /***
     * Find All with priority
     @return List<Tasks>
     */

    public List<Task> showAllFetchQuery() {
        return crudRepository.query("from Task t join fetch t.priority", Task.class);
    }


    /***
     * Find Task by Id
     * @param taskId
     * @return Task
     */

    public Optional<Task> findById(int taskId) {
        return crudRepository.optional("from Task where id = :fId", Task.class,
                Map.of("fId", taskId));

    }
}

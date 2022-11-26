package ru.job4j.todo.repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class TaskStoreTest {

    private static TaskStore store;
    private static SessionFactory sessionFactory;

    private static Task taskDone1;
    private static Task taskDone2;
    private static Task taskNew1;
    private static Task taskNew2;
    private static Task taskDone3;


    @BeforeAll
    public static void setConnection() {
        sessionFactory = new Main().sf();
        store = new TaskStore(sessionFactory);
        taskDone1 = new Task(1, "TestDone1", LocalDateTime.now(), true);
        taskDone2 = new Task(2, "TestDone2", LocalDateTime.now(), true);
        taskNew1 = new Task(3, "TestNew1", LocalDateTime.now(), false);
        taskNew2 = new Task(4, "TestNew2", LocalDateTime.now(), false);
        taskDone3 = new Task(5, "TestDone3", LocalDateTime.now(), true);
    }


    @Test
    public void whenCreateTaskAndShowAllAndById() {
        store.create(taskDone1);
        store.create(taskNew1);
        store.create(taskDone2);
        store.create(taskNew2);
        store.create(taskDone3);
        List<Task> expected = List.of(taskDone1, taskNew1, taskDone2, taskNew2, taskDone3);
        List<Task> actual = store.showALL();
        Assertions.assertEquals(expected, actual);

    }


    @Test
    public void whenUpdateTaskAndDelete() {
        Task taskDoneChanged = new Task();
        taskDoneChanged.setId(5);
        taskDoneChanged.setDescription("TesChanged");
        taskDoneChanged.setCreated(LocalDateTime.now());
        taskDoneChanged.setDone(true);
        store.create(taskDone3);
        boolean expectedUpdated = store.update(taskDone3.getId(), taskDoneChanged);
        Assertions.assertTrue(expectedUpdated);
        boolean expectedDeleted = store.delete(taskDone3.getId());
        Assertions.assertTrue(expectedDeleted);
    }


    @Test
    public void whenShowDoneAndNew() {
        store.create(taskDone1);
        store.create(taskNew1);
        store.create(taskDone2);
        store.create(taskNew2);
        List<Task> expectedDone = List.of(taskDone1, taskDone2);
        List<Task> actualDone = store.showWithStatus(true);
        Assertions.assertEquals(expectedDone, actualDone);
        List<Task> expectedNew = List.of(taskNew1, taskNew2);
        List<Task> actualNew = store.showWithStatus(false);
        Assertions.assertEquals(expectedNew, actualNew);


    }

    @Test
    public void whenShowDoneAndFindById() {
        store.create(taskNew1);
        store.done(taskNew1.getId());
        Optional<Task> expected = store.findById(taskNew1.getId());
        Assertions.assertEquals(taskNew1, expected.get());
        Assertions.assertTrue(expected.get().isDone());
        truncate();

    }

    @AfterEach
    public void truncate() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE tasks RESTART IDENTITY")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
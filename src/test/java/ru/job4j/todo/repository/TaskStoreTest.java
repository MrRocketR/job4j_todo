package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TaskStoreTest {

    private static TaskStore store;
    private static SessionFactory sessionFactory;

    private static Task taskDone1;
    private static Task taskDone2;
    private static Task taskNew1;
    private static Task taskNew2;
    private static Task taskDone3;


    @BeforeClass
    public static void setConnection() {
        sessionFactory = new Main().sf();
        store = new TaskStore(sessionFactory);
    }


    @BeforeClass
    public static void createTestClasses() {
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
        Assert.assertEquals(expected, actual);

    }


    @Test
    public void whenUpdateTaskAndDelete() {
        Task taskDoneChanged = new Task();
        taskDoneChanged.setId(5);
        taskDoneChanged.setDescription("TesChanged");
        taskDoneChanged.setCreated(LocalDateTime.now());
        taskDoneChanged.setDone(true);
        boolean expectedUpdated = store.update(taskDone3.getId(), taskDoneChanged);
        assertTrue(expectedUpdated);
        boolean expectedDeleted = store.delete(taskDone3.getId());
        assertTrue(expectedDeleted);
    }


    @Test
    public void whenShowDoneAndNew() {
        truncate();
        store.create(taskDone1);
        store.create(taskNew1);
        store.create(taskDone2);
        store.create(taskNew2);
        List<Task> expectedDone = List.of(taskDone1, taskDone2);
        List<Task> actualDone = store.showWithStatus(true);
        Assert.assertEquals(expectedDone, actualDone);
        List<Task> expectedNew = List.of(taskNew1, taskNew2);
        List<Task> actualNew = store.showWithStatus(false);
        Assert.assertEquals(expectedNew, actualNew);
        truncate();

    }

    @Test
    public void whenShowDoneAndFindById() {
        store.create(taskNew1);
        store.done(taskNew1.getId());
        Optional<Task> expected = store.findById(taskNew1.getId());
        Assert.assertEquals(taskNew1, expected.get());
        Assert.assertTrue(expected.get().isDone());
        truncate();

    }


    public void truncate() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE tasks RESTART IDENTITY")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
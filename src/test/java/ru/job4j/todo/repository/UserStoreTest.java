package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.User;

import java.util.Optional;


public class UserStoreTest {


    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void setConnection() {
       sessionFactory = new Main().sf();

    }

    @Test
    public void createNewUserAndFind() {
        UserStore store = new UserStore(sessionFactory);
        User userTest1 = new User(1, "Tester1", "test1", "test1");
        Optional<User> userPresent = store.create(userTest1);
         Assertions.assertTrue(userPresent.isPresent());
        Optional<User> found = store.findByLoginAndPassword(userTest1.getLogin(), userTest1.getPassword());
        Assertions.assertEquals(userTest1, found.get());
    }

    @Test
    public void whenUserIsNotPresent() {
        UserStore store = new UserStore(sessionFactory);
        Optional<User> notFound = store.findByLoginAndPassword("wrong", "wrong");
        Assertions.assertTrue(notFound.isEmpty());
    }

    @AfterEach
    public void truncate() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


}
package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.User;

import java.util.Optional;


public class UserStoreTest {


    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setConnection() {
        sessionFactory = new Main().sf();

    }

    @Test
    public void createNewUserAndFind() {
        UserStore store = new UserStore(sessionFactory);
        User userTest1 = new User(1, "Tester1", "test1", "test1");
        Optional<User> userPresent = store.create(userTest1);
        Assert.assertTrue(userPresent.isPresent());
        Optional<User> found = store.findByLoginAndPassport(userTest1.getLogin(), userTest1.getPassword());
        Assert.assertEquals(userTest1, found.get());
    }

    @Test
    public void whenUserIsNotPresent() {
        UserStore store = new UserStore(sessionFactory);
        Optional<User> notFound = store.findByLoginAndPassport("wrong", "wrong");
        Assert.assertTrue(notFound.isEmpty());
    }

    @After
    public void truncate() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


}
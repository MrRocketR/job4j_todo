package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;
@Repository
public class UserStore {


    private static final String FIND = "from User u where u.login = :fLogin and u.password  = :fPassword";
    private final SessionFactory sf;


    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> create(User user) {
        Session session = sf.openSession();
        Optional<User> optionalUser = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            optionalUser = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return optionalUser;

    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(
                FIND, User.class);
        query.setParameter("fLogin", login);
        query.setParameter("fPassword", password);
        Optional<User> user = query.uniqueResultOptional();
        session.close();
        return user;
    }
}

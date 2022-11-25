package ru.job4j.todo.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserStore;

import java.util.Optional;
@Service
public class UserService {
 private final UserStore store;

    public UserService(UserStore store) {
        this.store = store;
    }


    public Optional<User> createUser(User user) {
        return store.create(user);

    }

    public Optional<User> findByLoginAndPassport(String login, String password) {
        return store.findByLoginAndPassport(login, password);
    }
}

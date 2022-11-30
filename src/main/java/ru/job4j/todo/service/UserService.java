package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;


import java.util.Optional;
@Service
public class UserService {
 private final UserRepository store;

    public UserService(UserRepository store) {
        this.store = store;
    }


    public Optional<User> createUser(User user) {
        return Optional.of(store.create(user));

    }

    public Optional<User> findUser(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }
}

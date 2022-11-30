package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;
@Repository
@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Save into DB
     * @param user User.
     * @return User with id.
     */
    public  User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /***
     * Find User by login and password.
     * @param login User login
     *  @param password  User password.
     * @return Optional or user.
     */

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User u where u.login = :fLogin and u.password  = :fPassword", User.class,
                Map.of("fLogin", login, "fPassword", password)

        );
    }
}

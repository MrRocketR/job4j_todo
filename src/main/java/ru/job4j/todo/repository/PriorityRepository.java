package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityRepository {

    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query("FROM Priority", Priority.class);
    }

    public Optional<Priority> findById(int id) {
        Optional<Priority> optional = crudRepository.optional(
                "FROM Priority WHERE id = :fId", Priority.class, Map.of("fId", id)
        );
        if (!optional.isEmpty()) {
            return optional;
        }

        return Optional.empty();
    }
}

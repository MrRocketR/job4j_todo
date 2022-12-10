package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CategoryRepository {

    private final CrudRepository crudRepository;


    public List<Category> categories() {
        return crudRepository.query("from Category order by id", Category.class);
    }

    public List<Category> queryIn(List<Integer> ids) {
        List<Category> categories = crudRepository.query("From Category c where c.id IN :ids", Category.class,
                Map.of("ids", ids));
        return  categories;
    }
    public Optional<Category> findById(int id) {
        Optional<Category> category = crudRepository.optional("from Category where id = :fId", Category.class,
                Map.of("fId", id));
        if (category.isPresent()) {
            return category;
        }
        return Optional.empty();
    }

    public void create(Category category) {
        crudRepository.run(session -> session.persist(category));
    }
}

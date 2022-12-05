package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.List;


@Repository
@AllArgsConstructor
public class CategoryRepository {

    private final CrudRepository crudRepository;


    public  List<Category> categories() {
        return crudRepository.query("from Category order by id", Category.class);
    }

    public void create(Category category) {
        crudRepository.run(session -> session.persist(category));
    }
}

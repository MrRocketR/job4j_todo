package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public Map<Integer, Category> getMapOfCategories() {
        return categoryRepository.categories().stream()
                .collect(Collectors.toMap(Category::getId, category -> category));

    }
    public List<Category> getCategories() {
        return categoryRepository.categories();
    }
}

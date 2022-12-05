package ru.job4j.todo.service;


import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PriorityService {

    private  final PriorityRepository store;

    public PriorityService(PriorityRepository store) {
        this.store = store;
    }


    public List<Priority> findAll() {
        return store.findAll();
    }

    public Optional<Priority> findById(int id) {
        Optional<Priority> optional = store.findById(id);
        if (!optional.isEmpty()) {
            return optional;
        }
        return Optional.empty();
    }
}

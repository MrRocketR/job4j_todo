package ru.job4j.todo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.CrudRepository;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.repository.UserRepository;

import java.util.List;

public class ToDoRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            UserRepository userRepository = new UserRepository(new CrudRepository(sf));
            TaskRepository taskRepository = new TaskRepository(new CrudRepository(sf));
            User user = userRepository.findByLoginAndPassword("test", "test").get();
            var store = taskRepository.showAll();
            for (Task t : store) {
                System.out.println(t.getDescription());
                System.out.println(t.getCategories());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> List<T> listOf(String query, Class<T> model, SessionFactory sf) {
        Session session = sf.openSession();
        var rsl = session.createQuery(query, model)
                .getResultList();
        session.close();
        return rsl;
    }
}

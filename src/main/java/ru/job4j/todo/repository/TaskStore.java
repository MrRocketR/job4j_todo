package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore {

    private final SessionFactory sf;

    private final static String UPDATE = "UPDATE Task as t SET  t.description = :fDescription, "
            + "t.created = :fCreated, t.done = :fDone WHERE  t.id = :fId";

    private final static String SHOW_ALL = "from Task";

    private final static String DELETE = "DELETE Task as t where t.id = :fId";
    private final static  String SHOW_DONE = "from Task where done = true order by id";
    private final static String SHOW_NEW = "from Task where done = false order by id";

    private final static String FIND_BY_ID = "from Task where id = :fId";

    public Task create(Task task) {
        Session session = sf.openSession();
        task.setCreated(LocalDateTime.now());
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return task;
    }

    public boolean update(int id, Task task) {
        Session session = sf.openSession();
        int res = 0;
        try {
            session.beginTransaction();
            res = session.createQuery(UPDATE)
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCreated", task.getCreated())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", id)
                            .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return res == 1;
    }




    public boolean delete(int id) {
        Session session = sf.openSession();
        int res = 0;
        try {
            session.beginTransaction();
            res = session.createQuery(DELETE)
                   .setParameter("fId", id)
                            .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return res == 1;
    }




    public List<Task> showDone() {
        Session session = sf.openSession();
            List<Task> list = session.createQuery(SHOW_DONE, Task.class).list();
        session.close();
        return list;
    }

    public List<Task> showNew() {
        Session session = sf.openSession();
        List<Task> list = session.createQuery(SHOW_NEW, Task.class).list();
        session.close();
        return list;
    }

    public List<Task> showALL() {
        Session session = sf.openSession();
        List<Task> list = session.createQuery(SHOW_ALL, Task.class).list();
        session.close();
        return list;
    }

    public Task findById(int id) {
        Session session = sf.openSession();

        Query<Task> query = session.createQuery(
                FIND_BY_ID, Task.class);
        query.setParameter("fId", id);
        Task task = query.uniqueResult();
        session.close();
        return task;
    }


}

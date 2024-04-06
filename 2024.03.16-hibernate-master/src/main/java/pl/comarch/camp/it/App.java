package pl.comarch.camp.it;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class App {
    static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        User user = new User();
        user.setLogin("zbyszek");
        user.setName("Zbyszek");
        user.setSurname("Malinowski");
        user.setRole(User.Role.ADMIN);
        user.setPassword("tajnehaslozbyszka123");

        //save(user);

        //update(user);

        //System.out.println(user);

        //System.out.println(getAll());

        /*User user2 = getById(2).get();

        delete(user2);*/

        /*User user3 = new User();
        user3.setId(1);

        delete(user3);*/

        /*User newUser = new User();
        newUser.setLogin("wiesiek");
        newUser.setName("Wiesiek");
        newUser.setSurname("Kowalski");
        newUser.setRole(User.Role.ADMIN);
        newUser.setPassword("jakieshaslo");

        Order order = new Order();
        order.setTotal(1000.34);
        order.setUser(newUser);

        saveOrder(order);*/

        Order order = getOrderById(1).get();
        System.out.println(order.getId());
        System.out.println(order.getTotal());
        System.out.println(order.getUser());
    }

    public static void save(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch(Exception e) {
            System.out.println("cos sie zepsulo !!");
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void update(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void delete(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }

    public static Optional<User> getById(int id) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.comarch.camp.it.User WHERE id = :id", User.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    public static List<User> getAll() {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.comarch.camp.it.User", User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    public static void saveOrder(Order order) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static Optional<Order> getOrderById(int id) {
        Session session = sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.comarch.camp.it.Order WHERE id = :id");
        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            //session.close();
        }
    }
}

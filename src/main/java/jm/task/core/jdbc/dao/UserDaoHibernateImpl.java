package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users" +
            "(id mediumint not null auto_increment," +
            " name VARCHAR(50), " +
            "lastname VARCHAR(50), " +
            "age tinyint, " +
            "PRIMARY KEY (id))";
    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users;";
    public static final String SAVE_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
    public static final String REMOVE_USER = "DELETE FROM users WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE users";
    public static final SessionFactory sessionFactory = Util.getHibernateSession();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_USERS_TABLE).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_USERS_TABLE).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            Query query = session.createSQLQuery(SAVE_USER);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);

            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();

            Query query = session.createSQLQuery(REMOVE_USER);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();
            users = session.createSQLQuery(GET_ALL_USERS).addEntity(User.class).list();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_USERS_TABLE).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}

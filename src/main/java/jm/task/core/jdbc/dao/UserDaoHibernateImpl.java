package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Queue;
import java.util.logging.*;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private final static String CREATE_USERS_TABLE =
            "CREATE TABLE USER (Id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), last_name VARCHAR(25), age INT)";
    private final static String DROP_TABLE = "DROP TABLE IF EXISTS USER";
    private final static String DELETE_ALL_USER = "TRUNCATE mySQLtest.USER";
    private final static Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_USERS_TABLE).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(DROP_TABLE).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(DELETE_ALL_USER).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }
}

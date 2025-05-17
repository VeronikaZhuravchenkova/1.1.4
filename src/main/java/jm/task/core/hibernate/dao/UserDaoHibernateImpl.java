package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    private static final String CREATE_USERS_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                id bigint PRIMARY KEY AUTO_INCREMENT,
                name varchar(50),
                lastname varchar(50),
                age tinyint
            );
            """;
    private static final String DROP_USERS_TABLE_SQL = """
            DROP TABLE IF EXISTS users;
            """;

    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(CREATE_USERS_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при создании таблицы users: ", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(DROP_USERS_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы users: ", e);

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при сохранении пользователя: ", e);

        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя по id: ", e);

        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при получении всех пользователей: ", e);
            return List.of();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete from User ").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы users: ", e);
        }
    }
}

package ru.otus.shtyka.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.shtyka.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private Session session;

    public UserDAOImpl(Session session) {
        this.session = session;
    }

    public String getUserNameById(long id) {
        logger.info("Getting user by id " + id);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.where(builder.equal(from.get("id"), id));
        Query<User> query = session.createQuery(criteria);
        try {
            return query.uniqueResult().getName();
        } catch (NullPointerException e){
            throw new AssertionError("this id does not exist");
        }

    }

    public List<User> loadByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<User> query = session.createQuery(criteria);
        return query.list();
    }

    public List<User> loadAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        return session.createQuery(criteria).list();
    }
}

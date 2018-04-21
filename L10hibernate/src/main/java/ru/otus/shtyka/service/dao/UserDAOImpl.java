package ru.otus.shtyka.service.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.shtyka.base.UserDAO;
import ru.otus.shtyka.base.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Session session;

    public UserDAOImpl(Session session) {
        this.session = session;
    }

    public String getUserNameById(long id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.where(builder.equal(from.get("id"), id));
        Query<User> query = session.createQuery(criteria);
        return query.uniqueResult().getName();
    }

    public List<User> loadByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<User> query = session.createQuery(criteria);
        return query.list();
    }
}

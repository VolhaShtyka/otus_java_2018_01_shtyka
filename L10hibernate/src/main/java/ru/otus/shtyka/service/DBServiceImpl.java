package ru.otus.shtyka.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.shtyka.base.UserDAOImpl;
import ru.otus.shtyka.cache.CacheEngineImpl;
import ru.otus.shtyka.entity.Address;
import ru.otus.shtyka.entity.BaseEntity;
import ru.otus.shtyka.entity.Phone;
import ru.otus.shtyka.entity.User;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl<T extends BaseEntity> implements DBService<T> {

    private static SessionFactory sessionFactory = null;

    public DBServiceImpl() {
        if (sessionFactory != null) {
            return;
        }
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Phone.class);
        configuration.addAnnotatedClass(Address.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/otus");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        configuration.setProperty("hibernate.cache.use_second_level_cache", "true");
        configuration.setProperty("hibernate.cache.use_query_cache", "true");
        configuration.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void save(T t) {
        runInSession(session -> session.save(t));

    }

    @Override
    public T load(Class<T> clazz, long id) {
        T t = (T) CacheEngineImpl.getInstance().get(id);
        if (t == null) {
            return runInSession(session -> session.load(clazz, id));
        }
        return t;
    }

    public List<User> loadAll() {
        List<User> users = runInSession(session -> {
            UserDAOImpl dao = new UserDAOImpl(session);
            return dao.loadAll();
        });
        for (User user : users) {
            CacheEngineImpl.getInstance().put(user.getId(), user);
        }
        return users;
    }

    public List<User> loadByName(String name) {
        return runInSession(session -> {
            UserDAOImpl dao = new UserDAOImpl(session);
            return dao.loadByName(name);
        });
    }

    public String getUserNameById(long id) {
        User t = (User) CacheEngineImpl.getInstance().get(id);
        if (t == null) {
            return runInSession(session -> {
                UserDAOImpl dao = new UserDAOImpl(session);
                return dao.getUserNameById(id);
            });
        }
        return t.getName();
    }

    public void shutdown() {
        CacheEngineImpl.getInstance().dispose();
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}

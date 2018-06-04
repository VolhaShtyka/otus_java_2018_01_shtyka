package ru.otus.shtyka.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.base.UserDAOImpl;
import ru.otus.shtyka.cache.CacheEngine;
import ru.otus.shtyka.entity.Address;
import ru.otus.shtyka.entity.BaseEntity;
import ru.otus.shtyka.entity.Phone;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.MessageSystemImpl;
import ru.otus.shtyka.messageSystem.MessageAddress;
import ru.otus.shtyka.messageSystem.MessageSystemContext;

import java.util.List;
import java.util.function.Function;

@Service
public class DBServiceImpl<T extends BaseEntity> implements DBService<T> {

    private static SessionFactory sessionFactory = null;

    private MessageAddress address;

    @Autowired
    private MessageSystemContext msgSystemContext;

    @Autowired
    private CacheEngine cacheEngine;

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

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public CacheEngine getCacheEngine() {
        return cacheEngine;
    }

    @Override
    public MessageAddress getAddress() {
        return address;
    }

    @Override
    public MessageSystemImpl getMS() {
        return (MessageSystemImpl) msgSystemContext.getMessageSystem();
    }


    @Override
    public void save(T t) {
        runInSession(session -> session.save(t));
        cacheEngine.put(t.getId(), t);
    }

    @Override
    public T load(Class<T> clazz, long id) {
        T t = (T) cacheEngine.get(id);
        if (t == null) {
            return runInSession(session -> session.load(clazz, id));
        }
        return t;
    }

    @Override
    public List<User> loadAll() {
        List<User> users = runInSession(session -> {
            UserDAOImpl dao = new UserDAOImpl(session);
            return dao.loadAll();
        });
        for (User user : users) {
            cacheEngine.put(user.getId(), user);
        }
        return users;
    }

    @Override
    public String getUserNameById(long id) {
        User t = (User) cacheEngine.get(id);
        if (t == null) {
            return runInSession(session -> {
                UserDAOImpl dao = new UserDAOImpl(session);
                return dao.getUserNameById(id);
            });
        }
        return t.getName();
    }

    @Override
    public void init() {
        address = new MessageAddress("DB");
        msgSystemContext.setDbAddress(address);
        msgSystemContext.getMessageSystem().addAddressee(this);
        msgSystemContext.getMessageSystem().start();
    }

    public void shutdown() {
        cacheEngine.dispose();
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
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

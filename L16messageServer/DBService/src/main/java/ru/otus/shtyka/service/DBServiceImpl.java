package ru.otus.shtyka.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.shtyka.cache.CacheEngine;
import ru.otus.shtyka.channel.ClientSocketMsgWorker;
import ru.otus.shtyka.commands.GetCacheEngineInfoCommand;
import ru.otus.shtyka.commands.Command;
import ru.otus.shtyka.commands.SetDefaultWorkerCommand;
import ru.otus.shtyka.commands.SomeActionsSimulateCommand;
import ru.otus.shtyka.dao.UserDAOImpl;
import ru.otus.shtyka.entity.Address;
import ru.otus.shtyka.entity.BaseEntity;
import ru.otus.shtyka.entity.Phone;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.*;
import ru.otus.shtyka.messages.MsgSetDefaultWorker;
import ru.otus.shtyka.messages.MsgGetCacheEngineInfo;
import ru.otus.shtyka.messages.MsgSomeActionsSimulate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DBServiceImpl<T extends BaseEntity> implements DBService<T>, Addressee {

    private static SessionFactory sessionFactory = null;
    private ClientSocketMsgWorker worker;
    private CacheEngine cacheEngine;
    private Map<Class<? extends Message>, Command> commands = new HashMap<>();

    public void init() {
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

        commands.put(MsgGetCacheEngineInfo.class, new GetCacheEngineInfoCommand(worker, this));
        commands.put(MsgSetDefaultWorker.class, new SetDefaultWorkerCommand(worker));
        commands.put(MsgSomeActionsSimulate.class, new SomeActionsSimulateCommand(worker, this));
        worker.setEndPointService(this);
        worker.init();
    }

    public void setCacheEngine(CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    public ClientSocketMsgWorker getWorker() {
        return worker;
    }

    public void setWorker(ClientSocketMsgWorker worker) {
        this.worker = worker;
    }

    public CacheEngine getCacheEngine() {
        return cacheEngine;
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

    @Override
    public void execute(Message message) {
        commands.get(message.getClass()).execute(message);
    }
}

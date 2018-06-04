package ru.otus.shtyka.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.app.FrontendService;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.*;
import ru.otus.shtyka.service.MsgGetUserById;
import ru.otus.shtyka.service.MsgSaveUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tully.
 */
public class FrontendServiceImpl<T extends User> implements FrontendService<T> {

    private final MessageAddress address;

    @Autowired
    private MessageSystemContext context;

    private final Map<Long, String> users = new HashMap<>();

    public FrontendServiceImpl(MessageAddress address) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void load(Class<T> clazz, long id) {
        Message message = new MsgGetUserById<>(getAddress(), context.getDbAddress(), clazz, id);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void save(User user) {
        Message message = new MsgSaveUser<>(getAddress(), context.getDbAddress(), user);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public MessageAddress getAddress() {
        return address;
    }

    public void addUser(long id, String name) {
        users.put(id, name);
        System.out.println("User: " + name + " has id: " + id);
    }

    @Override
    public MessageSystemImpl getMS() {
        return (MessageSystemImpl) context.getMessageSystem();
    }
}

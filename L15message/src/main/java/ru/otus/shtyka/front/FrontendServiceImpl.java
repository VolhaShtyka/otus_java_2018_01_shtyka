package ru.otus.shtyka.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.app.FrontendService;
import ru.otus.shtyka.app.messages.MsgGetUserById;
import ru.otus.shtyka.app.messages.MsgSaveUser;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageAddress;
import ru.otus.shtyka.messageSystem.MessageSystemContext;
import ru.otus.shtyka.messageSystem.MessageSystemImpl;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FrontendServiceImpl<T extends User> implements FrontendService<T> {
    private final static Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());
    private MessageAddress address;

    @Autowired
    private MessageSystemContext msgSystemContext;

    private final Map<Long, String> users = new HashMap<>();

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        address = new MessageAddress("Frontend");
        msgSystemContext.setFrontAddress(address);
        msgSystemContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public void load(Class<T> clazz, long id) {
        Message message = new MsgGetUserById<>(getAddress(), msgSystemContext.getDbAddress(), clazz, id);
        msgSystemContext.getMessageSystem().sendMessage(message);
    }

    @Override
    public void save(User user) {
        Message message = new MsgSaveUser<>(getAddress(), msgSystemContext.getDbAddress(), user);
        msgSystemContext.getMessageSystem().sendMessage(message);
    }

    @Override
    public MessageAddress getAddress() {
        return address;
    }

    public void addUser(long id, String name) {
        users.put(id, name);
        logger.log(Level.INFO, "User: " + name + " has id: " + id);
    }

    @Override
    public MessageSystemImpl getMS() {
        return (MessageSystemImpl) msgSystemContext.getMessageSystem();
    }
}

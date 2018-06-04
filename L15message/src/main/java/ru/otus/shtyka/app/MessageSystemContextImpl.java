package ru.otus.shtyka.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.messageSystem.MessageSystem;
import ru.otus.shtyka.messageSystem.MessageAddress;
import ru.otus.shtyka.messageSystem.MessageSystemContext;

/**
 * Created by tully.
 */
@Service
public class MessageSystemContextImpl implements MessageSystemContext {

    @Autowired
    private MessageSystem messageSystem;

    private MessageAddress frontAddress;

    private MessageAddress dbAddress;

    public MessageSystemContextImpl() {
    }

    public MessageSystem getMessageSystem() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        return messageSystem;
    }

    public MessageAddress getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(MessageAddress frontAddress) {
        this.frontAddress = frontAddress;
    }

    public MessageAddress getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(MessageAddress dbAddress) {
        this.dbAddress = dbAddress;
    }
}

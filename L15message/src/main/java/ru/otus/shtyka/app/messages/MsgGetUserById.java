package ru.otus.shtyka.app.messages;

import org.hibernate.LazyInitializationException;
import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.app.MsgToDB;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.MessageAddress;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgGetUserById<T> extends MsgToDB {
    private final static Logger logger = Logger.getLogger(MsgGetUserById.class.getName());
    private final long id;
    private final Class<T> clazz;

    public MsgGetUserById(MessageAddress from, MessageAddress to, Class<T> clazz, long id) {
        super(from, to);
        this.id = id;
        this.clazz = clazz;
    }

    @Override
    public void exec(DBService dbService) {
        try {
            User user = (User) dbService.load(clazz, id);
            dbService.getMS().sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), id, user.getName()));
        } catch (LazyInitializationException e) {
            logger.log(Level.INFO, "User with id " + id + " does not exist");
        }
    }
}

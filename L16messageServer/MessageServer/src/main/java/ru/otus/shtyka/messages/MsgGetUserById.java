package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

import java.util.logging.Logger;

public class MsgGetUserById<T> extends Message {
    private final static Logger logger = Logger.getLogger(MsgGetUserById.class.getName());
    private final long id;
    private final Class<T> clazz;

    public MsgGetUserById(String from, String to, Class<T> clazz, long id) {
        super(from, to);
        this.id = id;
        this.clazz = clazz;
    }

    @Override
    public void exec() {
//        try {
//            T user = (T) dbService.load(clazz, id);
//            dbService.getMS().sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), id, user.getName()));
//        } catch (LazyInitializationException e) {
//            logger.log(Level.INFO, "User with id " + id + " does not exist");
//        }
    }
}

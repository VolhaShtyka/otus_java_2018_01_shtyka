package ru.otus.shtyka.service;

import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.app.MsgToDB;
import ru.otus.shtyka.messageSystem.MessageAddress;

/**
 * Created by tully.
 */
public class MsgGetUserById<T> extends MsgToDB {
    private final long id;
    private final Class<T> clazz;

    public MsgGetUserById(MessageAddress from, MessageAddress to, Class<T> clazz, long id) {
        super(from, to);
        this.id = id;
        this.clazz = clazz;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.load(clazz, id);
        //dbService.getMS().sendMessage(new MsgGetUserNameByIdAnswer(getTo(), getFrom(), user));
    }
}

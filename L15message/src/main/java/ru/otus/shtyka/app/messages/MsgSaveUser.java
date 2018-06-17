package ru.otus.shtyka.app.messages;

import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.app.MsgToDB;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.MessageAddress;

/**
 * Created by tully.
 */
public class MsgSaveUser<T> extends MsgToDB {
    private final User user;

    public MsgSaveUser(MessageAddress from, MessageAddress to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.save(user);
        dbService.getMS().sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), user.getId(), user.getName()));
    }
}

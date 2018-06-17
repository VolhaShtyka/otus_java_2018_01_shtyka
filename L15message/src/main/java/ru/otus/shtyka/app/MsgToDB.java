package ru.otus.shtyka.app;

import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.messageSystem.Addressee;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageAddress;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(MessageAddress from, MessageAddress to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}

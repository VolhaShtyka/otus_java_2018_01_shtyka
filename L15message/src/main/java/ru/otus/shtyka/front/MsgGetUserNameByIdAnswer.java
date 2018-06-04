package ru.otus.shtyka.front;

import ru.otus.shtyka.app.FrontendService;
import ru.otus.shtyka.app.MsgToFrontend;
import ru.otus.shtyka.messageSystem.MessageAddress;

/**
 * Created by tully.
 */
public class MsgGetUserNameByIdAnswer extends MsgToFrontend {
    private final String name;
    private final long id;

    public MsgGetUserNameByIdAnswer(MessageAddress from, MessageAddress to, long id, String name) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name);
    }
}

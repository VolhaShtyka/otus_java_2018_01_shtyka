package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

public class MsgSomeActionsSimulate extends Message {

    protected MsgSomeActionsSimulate(String sender, String receiver) {
        super(sender, receiver);
    }

    @Override
    public void exec() {

    }
}

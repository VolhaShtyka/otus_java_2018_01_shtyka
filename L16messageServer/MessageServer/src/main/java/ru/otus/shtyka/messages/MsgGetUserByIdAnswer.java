package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

public class MsgGetUserByIdAnswer extends Message {
    private final String name;
    private final long id;

    MsgGetUserByIdAnswer(String from, String to, long id, String name) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec() {
      //  frontendService.addUser(id, name);
    }
}

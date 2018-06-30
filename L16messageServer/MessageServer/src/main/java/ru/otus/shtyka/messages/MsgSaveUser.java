package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

public class MsgSaveUser<T> extends Message {
    private final T user;

    public MsgSaveUser(String from, String to, T user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec() {
//        dbService.save(user);
//        dbService.getMS().sendMessage(new MsgGetUserByIdAnswer(getTo(), getFrom(), user.getId(), user.getName()));
    }
}

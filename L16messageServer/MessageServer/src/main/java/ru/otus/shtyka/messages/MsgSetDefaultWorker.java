package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

public class MsgSetDefaultWorker extends Message {
    public MsgSetDefaultWorker(String from, String to) {
        super(from, to);
    }

    @Override
    public void exec() {
    }
}

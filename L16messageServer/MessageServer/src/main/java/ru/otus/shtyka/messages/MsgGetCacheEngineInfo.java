package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

public class MsgGetCacheEngineInfo extends Message {

    public MsgGetCacheEngineInfo(String from, String to) {
        super(from, to);
    }

    @Override
    public void exec() {
    }
}

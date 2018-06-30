package ru.otus.shtyka.messages;

import ru.otus.shtyka.messageSystem.Message;

public class MsgGetCacheEngineInfoAnswer extends Message {

    private String cacheInfo;

    public MsgGetCacheEngineInfoAnswer(String from, String to, String cacheInfo) {
        super(from, to);
        this.cacheInfo = cacheInfo;
    }

    @Override
    public void exec() {
    }
}

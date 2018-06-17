package ru.otus.shtyka.app.messages;

import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.app.MsgToDB;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageAddress;
import ru.otus.shtyka.websocket.CacheWebSocket;

public class MsgGetCacheEngineInfo extends MsgToDB {
    private CacheWebSocket ws;

    public MsgGetCacheEngineInfo(MessageAddress from, MessageAddress to, CacheWebSocket ws) {
        super(from, to);
        this.ws = ws;
    }

    @Override
    public void exec(DBService dbService) {
        Message msg = new MsgGetCacheEngineInfoAnswer(getTo(), getFrom(), dbService.getCacheEngine().getCacheInfo(), ws);
        dbService.getMS().sendMessage(msg);
    }
}

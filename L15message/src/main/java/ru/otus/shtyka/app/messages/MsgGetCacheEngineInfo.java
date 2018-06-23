package ru.otus.shtyka.app.messages;

import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.app.MsgToDB;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageAddress;

public class MsgGetCacheEngineInfo extends MsgToDB {
    private long userSessionId;

    public MsgGetCacheEngineInfo(MessageAddress from, MessageAddress to, long userSessionId) {
        super(from, to);
        this.userSessionId = userSessionId;
    }

    @Override
    public void exec(DBService dbService) {
        Message msg = new MsgGetCacheEngineInfoAnswer(getTo(), getFrom(), dbService.getCacheEngine().getCacheInfo(), userSessionId);
        dbService.getMS().sendMessage(msg);
    }
}

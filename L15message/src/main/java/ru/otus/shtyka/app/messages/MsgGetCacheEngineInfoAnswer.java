package ru.otus.shtyka.app.messages;


import ru.otus.shtyka.app.FrontendService;
import ru.otus.shtyka.app.MsgToFrontend;
import ru.otus.shtyka.messageSystem.MessageAddress;
import ru.otus.shtyka.websocket.CacheWebSocket;

public class MsgGetCacheEngineInfoAnswer extends MsgToFrontend {

    private String cacheInfo;

    private CacheWebSocket ws;

    MsgGetCacheEngineInfoAnswer(MessageAddress from, MessageAddress to, String cacheInfo, CacheWebSocket ws) {
        super(from, to);
        this.cacheInfo = cacheInfo;
        this.ws = ws;
    }

    @Override
    public void exec(FrontendService frontendService) {
        ws.send(cacheInfo);
    }
}

package ru.otus.shtyka.app.messages;


import ru.otus.shtyka.app.FrontendService;
import ru.otus.shtyka.app.MsgToFrontend;
import ru.otus.shtyka.messageSystem.MessageAddress;

public class MsgGetCacheEngineInfoAnswer extends MsgToFrontend {

    private String cacheInfo;

    private long userSessionId;

    MsgGetCacheEngineInfoAnswer(MessageAddress from, MessageAddress to, String cacheInfo, long userSessionId) {
        super(from, to);
        this.cacheInfo = cacheInfo;
        this.userSessionId = userSessionId;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.send(cacheInfo, userSessionId);
    }
}

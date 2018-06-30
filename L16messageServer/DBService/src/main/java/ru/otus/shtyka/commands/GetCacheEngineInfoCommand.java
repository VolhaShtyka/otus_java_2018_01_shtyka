package ru.otus.shtyka.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.shtyka.channel.MsgWorker;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messages.MsgGetCacheEngineInfoAnswer;
import ru.otus.shtyka.service.DBService;

public class GetCacheEngineInfoCommand extends Command {
    private Logger logger = LoggerFactory.getLogger(GetCacheEngineInfoCommand.class);

    private DBService service;

    public GetCacheEngineInfoCommand(MsgWorker worker, DBService service) {
        super(worker);
        this.service = service;
    }

    @Override
    public void execute(Message message) {
        logger.info("DBService receive MsgGetCacheEngineInfo");
        String cacheInfo = service.getCacheEngine().getCacheInfo();
        logger.info(cacheInfo);
        getWorker().accept(new MsgGetCacheEngineInfoAnswer(message.getTo(), message.getFrom(), cacheInfo));
    }
}

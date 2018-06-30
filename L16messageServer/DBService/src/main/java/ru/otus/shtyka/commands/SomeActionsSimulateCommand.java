package ru.otus.shtyka.commands;


import ru.otus.shtyka.channel.MsgWorker;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.service.DBService;
import ru.otus.shtyka.service.DBServiceHelper;

public class SomeActionsSimulateCommand extends Command {
    private DBService dbService;

    public SomeActionsSimulateCommand(MsgWorker worker, DBService dbService) {
        super(worker);
        this.dbService = dbService;
    }

    @Override
    public void execute(Message message) {
        DBServiceHelper.someActions(dbService);
    }
}

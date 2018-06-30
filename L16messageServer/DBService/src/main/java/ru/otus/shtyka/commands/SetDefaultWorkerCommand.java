package ru.otus.shtyka.commands;

import ru.otus.shtyka.channel.MsgWorker;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messages.MsgSetDefaultWorker;

public class SetDefaultWorkerCommand extends Command {
    public SetDefaultWorkerCommand(MsgWorker worker) {
        super(worker);
    }

    @Override
    public void execute(Message message) {
        String sender = this.getWorker().getSubscriber();
        getWorker().accept(new MsgSetDefaultWorker(sender, ""));
    }
}

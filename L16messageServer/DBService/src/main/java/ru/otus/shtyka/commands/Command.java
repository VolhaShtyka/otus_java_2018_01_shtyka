package ru.otus.shtyka.commands;

import ru.otus.shtyka.channel.MsgWorker;
import ru.otus.shtyka.messageSystem.Message;

public abstract class Command {
    private MsgWorker worker;

    public Command(MsgWorker worker) {
        this.worker = worker;
    }

    public abstract void execute(Message message);

    public MsgWorker getWorker() {
        return worker;
    }
}

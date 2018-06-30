package ru.otus.shtyka.channel;

import ru.otus.shtyka.messageSystem.Addressee;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageSystem;

import java.io.IOException;

public interface MsgWorker {
    void send(Message msg);

    Message pool();

    Message take() throws InterruptedException;

    void close() throws IOException;

    void init();

    MessageSystem getMessageSystem();

    void setMessageSystem(MessageSystem messageSystem);

    void setEndPointService(Addressee endPointService);

    String getSubscriber();

    void accept(Message message);
}

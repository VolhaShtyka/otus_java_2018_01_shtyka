package ru.otus.shtyka.messageSystem;

import ru.otus.shtyka.channel.MsgWorker;

public interface MessageSystem extends Addressee {

    void sendMessage(Message message);

    void dispose();

    void addSubscriber(String subscriber, MsgWorker worker);

}

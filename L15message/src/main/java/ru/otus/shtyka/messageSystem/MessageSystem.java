package ru.otus.shtyka.messageSystem;

public interface MessageSystem {
    void start();

    void addAddressee(Addressee addressee);

    void sendMessage(Message message);

    void dispose();
}

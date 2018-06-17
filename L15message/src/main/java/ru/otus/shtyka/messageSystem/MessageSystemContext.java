package ru.otus.shtyka.messageSystem;

public interface MessageSystemContext {
    MessageSystem getMessageSystem();

    void setDbAddress(MessageAddress dbAddress);

    void setFrontAddress(MessageAddress dbAddress);

    MessageAddress getDbAddress();

    MessageAddress getFrontAddress();
}

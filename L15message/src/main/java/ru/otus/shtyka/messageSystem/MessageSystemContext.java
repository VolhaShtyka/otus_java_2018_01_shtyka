package ru.otus.shtyka.messageSystem;

public interface MessageSystemContext {
    MessageSystem getMessageSystem();

    void setDbAddress(MessageAddress dbAddress);

    MessageAddress getDbAddress();
}

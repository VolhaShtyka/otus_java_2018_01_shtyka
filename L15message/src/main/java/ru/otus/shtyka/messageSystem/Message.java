package ru.otus.shtyka.messageSystem;

/**
 * @author tully
 */
public abstract class Message {
    private final MessageAddress from;
    private final MessageAddress to;

    public Message(MessageAddress from, MessageAddress to) {
        this.from = from;
        this.to = to;
    }

    public MessageAddress getFrom() {
        return from;
    }

    public MessageAddress getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}

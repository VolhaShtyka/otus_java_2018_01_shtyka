package ru.otus.shtyka.messageSystem;

public abstract class Message {

    public static final String CLASS_NAME_VARIABLE = "type";

    private final String type;

    private String from;

    private String to;

    public Message(String from, String to) {
        this.from = from;
        this.to = to;
        type = this.getClass().getName();
    }

    public abstract void exec();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}

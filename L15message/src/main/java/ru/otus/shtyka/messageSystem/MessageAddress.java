package ru.otus.shtyka.messageSystem;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tully
 */

@Service
public final class MessageAddress {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;

    public MessageAddress(){
        id = String.valueOf(ID_GENERATOR.getAndIncrement());
    }

    public MessageAddress(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageAddress address = (MessageAddress) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getId() {
        return id;
    }
}

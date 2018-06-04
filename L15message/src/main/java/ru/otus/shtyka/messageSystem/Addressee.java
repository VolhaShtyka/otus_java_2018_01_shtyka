package ru.otus.shtyka.messageSystem;

import org.springframework.stereotype.Service;

/**
 * @author tully
 */
@Service
public interface Addressee {
    MessageAddress getAddress();

    MessageSystemImpl getMS();
}

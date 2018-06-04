package ru.otus.shtyka.messageSystem;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MessageSystemImpl implements MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystemImpl.class.getName());
    private static final int DEFAULT_STEP_TIME = 10;

    private final List<Thread> workers;
    private final Map<MessageAddress, ConcurrentLinkedQueue<Message>> messagesMap;
    private final Map<MessageAddress, Addressee> addresseeMap;

    public MessageSystemImpl() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message) {
        try {
            messagesMap.get(message.getTo()).add(message);
        } catch (NullPointerException e) {
            throw new AssertionError(message.getTo() + messagesMap.toString());
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        Thread thread;
        for (Map.Entry<MessageAddress, Addressee> entry : addresseeMap.entrySet()) {
            String name = "MS-worker-" + entry.getKey().getId();
            thread = new Thread(() -> {
                while (true) {
                    ConcurrentLinkedQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (!queue.isEmpty()) {
                        Message message = queue.poll();
                        message.exec(entry.getValue());
                    }
                    try {
                        Thread.sleep(MessageSystemImpl.DEFAULT_STEP_TIME);
                    } catch (InterruptedException e) {
                        logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                        return;
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        logger.log(Level.INFO, "Finishing: " + name);
                        return;
                    }
                }

            });
            thread.setName(name);
            thread.start();
            workers.add(thread);
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}

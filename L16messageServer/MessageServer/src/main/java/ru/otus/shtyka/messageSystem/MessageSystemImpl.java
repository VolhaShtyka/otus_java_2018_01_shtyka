package ru.otus.shtyka.messageSystem;

import ru.otus.shtyka.channel.MsgWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class MessageSystemImpl implements MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystemImpl.class.getName());

    private final Map<String, MsgWorker> workers;
    private final Map<String, ConcurrentLinkedQueue<Message>> messagesMap;
    private final List<Thread> addresseeMap;

    public MessageSystemImpl() {
        workers = new HashMap<>();
        messagesMap = new HashMap<>();
        addresseeMap = new ArrayList<>();
    }

    public void addSubscriber(final String subscriber, final MsgWorker worker) {
        messagesMap.put(subscriber, new ConcurrentLinkedQueue<>());
        workers.put(subscriber, worker);
        start(subscriber);
    }

    private void start(final String subscriber) {
        Thread thread = new Thread(() -> {
            while (true) {
                ConcurrentLinkedQueue<Message> queue = messagesMap.get(subscriber);
                while (!queue.isEmpty()) {
                    Message message = queue.poll();
                    workers.get(subscriber).accept(message);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.info("Thread interrupted, finishing: " + subscriber);
                    return;
                }
                if (Thread.currentThread().isInterrupted()) {
                    logger.info("Finishing: " + subscriber);
                    return;
                }
            }
        });
        thread.setName(getClass().getSimpleName() + "_" + subscriber);
        thread.start();
        logger.info("Thread started: " + thread.getName() + ", state: " + thread.getState());
        addresseeMap.add(thread);
    }

    public void sendMessage(Message message) {
        String receiver = message.getTo();
        logger.info("send Message: [message:]" + message.toString() + "[receiver:]" + receiver);
        messagesMap.get(receiver).add(message);
        for (Map.Entry<String, ConcurrentLinkedQueue<Message>> entry : messagesMap.entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void dispose() {
        addresseeMap.forEach(Thread::interrupt);
    }

    @Override
    public void execute(Message message) {
        sendMessage(message);
    }
}

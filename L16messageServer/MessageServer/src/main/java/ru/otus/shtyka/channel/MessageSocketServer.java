package ru.otus.shtyka.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.shtyka.messageSystem.MessageSystem;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageSocketServer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(MessageSocketServer.class);

    private static final int PORT = 4050;
    private static final String DB_START_COMMAND = "java -jar db-service-1.0-SNAPSHOT.jar";
    private static final String FE_START_COMMAND = "java -jar frontend-service-1.0-SNAPSHOT.jar";
    private static final int START_DELAY = 5000;
    private boolean running = true;
    private MessageSystem messageSystem;

    public MessageSocketServer(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port " + serverSocket.getLocalPort());
            while (running) {
                Socket socket = serverSocket.accept();
                MsgWorker worker = new SocketMsgWorker(socket);
                worker.setMessageSystem(messageSystem);
                worker.setEndPointService(messageSystem);
                worker.init();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void startClients() {
        startDatabase();
        try {
            Thread.sleep(START_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startFrontend();
    }

    private void startDatabase() {
        logger.info("Start DB: " + DB_START_COMMAND);
        ProcessBuilder pb = new ProcessBuilder(DB_START_COMMAND.split(" "));
        pb.redirectErrorStream(true);
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = p.getInputStream();
        ProcessStreamListener handler = new ProcessStreamListener(inputStream, p);
        handler.start();
    }

    private void startFrontend() {
        logger.info("Start front: " + FE_START_COMMAND);
        ProcessBuilder pb = new ProcessBuilder(FE_START_COMMAND.split(" "));
        pb.redirectErrorStream(true);
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = p.getInputStream();
        ProcessStreamListener handler = new ProcessStreamListener(inputStream, p);
        handler.start();
    }
}

package ru.otus.shtyka.channel;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.shtyka.messageSystem.Addressee;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageSystem;
import ru.otus.shtyka.messages.MsgSetDefaultWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMsgWorker implements MsgWorker {
    private static final Logger logger = Logger.getLogger(SocketMsgWorker.class.getName());

    private static final int WORKERS_COUNT = 2;
    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();
    private final ExecutorService executor;
    private final Socket socket;
    private MessageSystem messageSystem;
    private String subscriber;
    private Addressee endPointService;

    public SocketMsgWorker(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    public void setSubscriber(String subscriber) {
        logger.info("Set subscriber " + subscriber + " on worker: " + this.toString());
        this.subscriber = subscriber;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setEndPointService(Addressee endPointService) {
        this.endPointService = endPointService;
    }

    @Override
    public void send(Message msg) {
        output.add(msg);
    }

    @Override
    public Message pool() {
        return input.poll();
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        executor.shutdown();
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public void setMessageSystem(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void accept(Message message) {
        output.add(message);
        logger.info("Add message " + message.toString() + " in queue: " + output);
    }

    public void init() {
        if (subscriber == null) {
            accept(new MsgSetDefaultWorker(null, ""));
        }
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            logger.info("Writer to socket found: " + out.toString());
            while (socket.isConnected()) {
                logger.info("Socket " + socket.toString() + " is connected");
                Message msg = output.take(); //blocks
                logger.info("Message " + msg.toString() + " taked from " + output);
                String json = new Gson().toJson(msg);
                logger.info("Jsoning message: " + json);
                out.println(json);
                out.println();//line with json + an empty line
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            logger.info("Reader from socket found: " + in.toString());
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    logger.info("Message in json loaded: " + json);
                    Message msg = getMsgFromJSON(json);
                    if (msg instanceof MsgSetDefaultWorker && msg.getFrom() != null) {
                        messageSystem.addSubscriber(msg.getFrom(), this);
                    } else {
                        endPointService.execute(msg);
                    }
                    input.add(msg);
                    logger.info("Message executed by service: " + endPointService);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Message getMsgFromJSON(String json) throws ParseException, ClassNotFoundException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Message.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Message) new Gson().fromJson(json, msgClass);
    }
}

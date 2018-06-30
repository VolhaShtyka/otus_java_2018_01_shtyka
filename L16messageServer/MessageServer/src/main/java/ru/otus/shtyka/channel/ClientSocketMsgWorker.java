package ru.otus.shtyka.channel;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketMsgWorker extends SocketMsgWorker {

    private final Socket socket;

    public ClientSocketMsgWorker(String host, int port, String subscriber) throws IOException {
        this(new Socket(host, port));
        this.setSubscriber(subscriber);
    }


    private ClientSocketMsgWorker(Socket socket) {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}

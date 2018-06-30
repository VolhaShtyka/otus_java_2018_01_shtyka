package ru.otus.shtyka.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.shtyka.channel.MessageSocketServer;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans-ms.xml");
        MessageSocketServer messageSocketServer = (MessageSocketServer) context.getBean("messageSocketServer");
        messageSocketServer.start();
        messageSocketServer.startClients();
    }
}

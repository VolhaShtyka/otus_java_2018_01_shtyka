package ru.otus.shtyka.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.shtyka.servlet.LoginServlet;
import ru.otus.shtyka.servlet.WebSocketCacheServlet;

import java.net.URL;

public class MainFE {

    public static void main(String[] args) throws Exception {
        int port = 8080;

        URL url = MainFE.class.getClassLoader().getResource("tml/");

        System.out.println("URI = " + url);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setBaseResource(Resource.newResource(url));

        context.setWelcomeFiles(new String[]{"index.html"});

        context.addServlet(new ServletHolder("default", DefaultServlet.class), "/");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new WebSocketCacheServlet()), "/cache");

        Server server = new Server(port);
        server.setHandler(context);
        server.start();
        server.join();
    }
}

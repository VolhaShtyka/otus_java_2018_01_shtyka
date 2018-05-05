package ru.otus.shtyka.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.shtyka.cache.CacheEngine;

public class JettyServer {
    private final static int PORT = 8090;
    // the path may be different depending on the root of the startup
    private final static String RESOURCES = "L10hibernate/src/main/resources";
    private Server server;

    public void run(CacheEngine cacheEngine) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(RESOURCES);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder holder = new ServletHolder(new LoginServlet(templateProcessor));
        context.addServlet(holder, "/login");
        holder = new ServletHolder(new CacheServlet(templateProcessor, cacheEngine));
        context.addServlet(holder, "/cache");

        server = new org.eclipse.jetty.server.Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}

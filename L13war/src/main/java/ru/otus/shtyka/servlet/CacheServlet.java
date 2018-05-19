package ru.otus.shtyka.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.cache.CacheEngine;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.service.DBService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.shtyka.servlet.LoginServlet.ADMIN_LOGIN;
import static ru.otus.shtyka.servlet.LoginServlet.LOGIN_PARAMETER_NAME;

public class CacheServlet extends HttpServlet {

    private static final String CACHE_PAGE_TEMPLATE = "cache.html";

    @Autowired
    private TemplateProcessor templateProcessor;

    @Autowired
    private CacheEngine cacheEngine;

    @Autowired
    private DBService dbService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        checkConnectDB();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("hit", cacheEngine.getHitCount());
        pageVariables.put("miss", cacheEngine.getMissCount());
        pageVariables.put("size", cacheEngine.getCurrentSize());
        pageVariables.put("maxSize", cacheEngine.getMaxSize());

        String login = (String) request.getSession().getAttribute(LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login == null ? "" : login);

        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String requestLogin = (String) request.getSession().getAttribute(LOGIN_PARAMETER_NAME);
        if (ADMIN_LOGIN.equals(requestLogin)) {
            response.getWriter().println(templateProcessor.getPage(CACHE_PAGE_TEMPLATE, createPageVariablesMap(request)));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            new LoginServlet().doPost(request, response);
        }
    }

    private void checkConnectDB() {
        User sidorov = new User("Sidorov", 22);

        dbService.save(sidorov);
        dbService.save(new User("Ivanov", 98));

        dbService.load(User.class, sidorov.getId());
        dbService.load(User.class, sidorov.getId());
        dbService.load(User.class, 5);

        dbService.loadAll();
    }
}

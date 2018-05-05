package ru.otus.shtyka.servlet;

import ru.otus.shtyka.cache.CacheEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheServlet extends HttpServlet {

    static final String CACHE_PAGE_TEMPLATE = "cache.html";

    private final TemplateProcessor templateProcessor;
    private CacheEngine cacheEngine;

    @SuppressWarnings("WeakerAccess")
    public CacheServlet(TemplateProcessor templateProcessor, CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
        this.templateProcessor = templateProcessor;
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("hit", cacheEngine.getHitCount());
        pageVariables.put("miss", cacheEngine.getMissCount());
        pageVariables.put("size", cacheEngine.getCurrentSize());
        pageVariables.put("maxSize", cacheEngine.getMaxSize());

        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login == null ? "" : login);

        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        response.setContentType("text/html;charset=utf-8");
        String page = templateProcessor.getPage(CACHE_PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

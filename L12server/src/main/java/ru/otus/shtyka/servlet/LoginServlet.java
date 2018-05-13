package ru.otus.shtyka.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    static final String PASSWORD_PARAMETER_NAME = "password";

    static final String ADMIN_LOGIN = "root";
    static final String ADMIN_PASSWORD = "root";
    private static final String MESSAGE_PARAMETER = "message";
    private static final String MESSAGE = "Enter your login and password";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private final TemplateProcessor templateProcessor;

    LoginServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER_NAME);
        String page;
        if (ADMIN_LOGIN.equals(requestLogin) && ADMIN_PASSWORD.equals(requestPassword)) {
            saveToSession(request, LOGIN_PARAMETER_NAME, requestLogin);
            page = getPage(ADMIN_PAGE_TEMPLATE, LOGIN_PARAMETER_NAME, ADMIN_LOGIN);
        } else {
            page = getPage(LOGIN_PAGE_TEMPLATE, MESSAGE_PARAMETER, MESSAGE);
        }
        setOK(response);
        response.getWriter().println(page);
    }

    private String getPage(String template, String key, String value) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(key, value);
        return templateProcessor.getPage(template, pageVariables);
    }

    private void saveToSession(HttpServletRequest request, String key, String value) {
        request.getSession().setAttribute(key, value);
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

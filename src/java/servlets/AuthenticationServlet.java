package servlets;

import common.models.User;
import storage.user.InMemoryUserStorage;
import storage.user.UserStorage;
import utils.Encrypt;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(urlPatterns = "/auth", initParams = {
//        @WebInitParam(name = "cookie-live-time", value = "300")
//})

public class AuthenticationServlet extends HttpServlet {
    /*private static final String PARAM_USERNAME = "login";
    public static final String COOKIE_USER_ID = "password";
    public static final String PARAM_UID = COOKIE_USER_ID;

    private UserStorage userStorage = InMemoryUserStorage.getInstance();

    private int cookieLifeTime = -1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cookieLifeTime = Integer.parseInt(config.getInitParameter("cookie-live-time"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter(PARAM_USERNAME);
        String password = Encrypt.encryptPassword(req.getParameter(COOKIE_USER_ID));

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        if (userStorage.ifUserLoginExists(user)) {
            Cookie userPasswordCookie = new Cookie(COOKIE_USER_ID, password);
            Cookie usernameCookie = new Cookie(PARAM_USERNAME, login);
            userPasswordCookie.setMaxAge(cookieLifeTime);
            resp.addCookie(userPasswordCookie);
            resp.addCookie(usernameCookie);
            resp.sendRedirect("/chat.html");
        } else {
            resp.sendRedirect("/register.jsp");
        }
    }*/
}

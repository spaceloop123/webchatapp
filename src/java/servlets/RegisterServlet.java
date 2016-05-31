package servlets;

import common.models.User;
import storage.user.InMemoryUserStorage;
import storage.user.UserStorage;
import utils.Encrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
    private UserStorage userStorage = InMemoryUserStorage.getInstance();

    private static final String PARAM_USERNAME = "login";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_UID = PARAM_USERNAME;

    private int cookieLifeTime = 30;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = Encrypt.encryptPassword(req.getParameter("password"));
        String username = req.getParameter("login");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setUsername(username);

        if (userStorage.ifUserLoginExists(user)) {
            resp.sendRedirect("/pages/register2.jsp");
        } else {
            userStorage.addUser(user);
            Cookie loginCookie = new Cookie(PARAM_USERNAME, login);
            Cookie passwordCookie = new Cookie(PARAM_PASSWORD, password);
            loginCookie.setMaxAge(300);
            resp.addCookie(loginCookie);
            resp.addCookie(passwordCookie);
            resp.sendRedirect("/chat.html");
        }
    }
}

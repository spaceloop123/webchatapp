package filters;

import common.models.User;
import storage.user.InMemoryUserStorage;
import storage.user.UserStorage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(value = "/start.jsp")
public class AuthenticationFilter implements Filter {

    private UserStorage userStorage = InMemoryUserStorage.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        try {
            Cookie[] cookies = ((HttpServletRequest) req).getCookies();
            for (Cookie item : cookies) {
                String login = item.getValue();

                User user = new User();

                user.setLogin(login);

                if (userStorage.ifUserLoginExists(user)) {
                    filterChain.doFilter(req, resp);
                    HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
                    httpServletResponse.sendRedirect("/chat.html");
                }
            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }
}

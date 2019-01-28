package by.gstu.airline.controller.servlet;

import javax.servlet.http.*;
import java.io.IOException;

/**
 * The class changes Locale and save user's locale to session and cookies.
 */
public class LocaleChangeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        String locale = req.getParameter("locale");

        if (locale != null) {
            if("default".equals(locale)) {
                locale = resp.getLocale().toString();
            }
            saveToSession(req, locale);
            saveToCookie(resp, locale);
            resp.getWriter().print("ok");
        } else {
            resp.getWriter().print("fail");
        }
    }

    private void saveToSession(HttpServletRequest req, String locale) {
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);
    }

    private void saveToCookie(HttpServletResponse resp, String locale) {
        Cookie cookie = new Cookie("locale", locale);
        cookie.setMaxAge(60 * 60); // 1 hour
        resp.addCookie(cookie);
    }
}

package by.gstu.airline.controller.servlet;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

/**
 * The class changes Locale and save user's locale to session and cookies.
 */
public class LocaleChangeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        String localeName = req.getParameter("locale");
        if (localeName == null) {
            resp.getWriter().print("fail");
            return;
        }
        Locale locale;
        if("default".equals(localeName)) {
            locale = req.getLocale();
        } else {
            locale = getLocaleByName(localeName);
        }
        saveToSession(req, locale);
        saveToCookie(resp, locale.toString());
        resp.getWriter().print("ok");
    }

    private void saveToSession(HttpServletRequest req, Locale locale) {
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);
    }

    private void saveToCookie(HttpServletResponse resp, String localeName) {
        Cookie cookie = new Cookie("locale", localeName);
        cookie.setMaxAge(60 * 60); // 1 hour
        resp.addCookie(cookie);
    }

    private Locale getLocaleByName(String localeName) {
        String[] localeArr = localeName.split("_");
        return new Locale(localeArr[0], localeArr[1]);
    }
}

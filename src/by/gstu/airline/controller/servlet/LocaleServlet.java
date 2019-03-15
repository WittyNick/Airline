package by.gstu.airline.controller.servlet;

import by.gstu.airline.config.ConfigurationManager;
import com.google.gson.Gson;

import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Find user's Locale in session and cookies and returns Map object
 * that contains localized parameters.
 * When user's session doesn't contains Locale information
 * assume default locale from HttpServletRequest parameter.
 */
public class LocaleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        BufferedReader reader = req.getReader();
        String json = "";
        if (reader != null) {
            json = reader.readLine();
        }
        Gson gson = new Gson();
        String[] requestParameters = gson.fromJson(json, String[].class);
        Locale locale = findLocale(req);
        Map<String, String> map = manager.getTextMap(locale, requestParameters);
        map.put("locale", locale.toString());
        String jsonMap = gson.toJson(map);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().print(jsonMap);
    }

    private Locale findLocale(HttpServletRequest req) {
        Locale locale = findSessionLocale(req);
        if (locale != null) {
            return locale;
        }
        locale = findCookieLocale(req);
        if (locale == null) {
            locale = req.getLocale();
        }
        saveToSession(req, locale);
        return locale;
    }

    private Locale findSessionLocale(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (Locale) session.getAttribute("locale");
    }

    private Locale findCookieLocale(HttpServletRequest req) {
        Cookie [] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("locale".equals(name)) {
                    return getLocaleByName(cookie.getValue());
                }
            }
        return null;
    }

    private Locale getLocaleByName(String localeName) {
        String[] localeArr = localeName.split("_");
        return new Locale(localeArr[0], localeArr[1]);
    }

    private void saveToSession(HttpServletRequest req, Locale locale) {
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);
    }
}

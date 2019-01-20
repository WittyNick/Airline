package by.gstu.airline.controller.servlet;

import by.gstu.airline.config.ConfigurationManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

        String locale = findLocale(req);
        String[] localeArr = locale.split("_");
        manager.changeLocale(new Locale(localeArr[0], localeArr[1]));

        Map<String, String> map = new HashMap<>();
        map.put("locale", locale);
        for (String parameter : requestParameters) {
            map.put(parameter, manager.getText(parameter));
        }
        String jsonMap = gson.toJson(map);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().print(jsonMap);
    }

    private String findLocale(HttpServletRequest req) {
        String locale = findSessionLocale(req);
        if (locale != null) {
            return locale;
        } else {
            locale = findCookieLocale(req);
        }
        if (locale == null || "default".equals(locale)) {
            locale = req.getLocale().toString();
        }
        return locale;
    }

    private String findSessionLocale(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (String) session.getAttribute("locale");
    }

    private String findCookieLocale(HttpServletRequest req) {
        Cookie [] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("locale".equals(name)) {
                    return cookie.getValue();
                }
            }
        return null;
    }
}

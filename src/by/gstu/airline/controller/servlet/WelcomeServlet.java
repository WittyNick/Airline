package by.gstu.airline.controller.servlet;

import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        Service service = Service.INSTANCE;
        String json = gson.toJson(service.readAllFlight());
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(json);
    }
}

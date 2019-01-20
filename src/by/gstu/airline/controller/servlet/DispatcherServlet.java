package by.gstu.airline.controller.servlet;

import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("dispatcher.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        Gson gson = new Gson();
        String json = gson.toJson(service.readAllFlight());
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(json);
    }
}

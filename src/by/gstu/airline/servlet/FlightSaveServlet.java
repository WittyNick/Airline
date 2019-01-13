package by.gstu.airline.servlet;

import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class FlightSaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Service service = Service.INSTANCE;
        BufferedReader reader = req.getReader();
        String jsonFlight;
        resp.setContentType("text/plain; charset=UTF-8");
        if (reader != null) {
            jsonFlight = reader.readLine();
        } else {
            resp.getWriter().print("fail");
            return;
        }
        Gson gson = new Gson();
        Flight bobtailFlight = gson.fromJson(jsonFlight, Flight.class);
        if (bobtailFlight.getId() > 0) {
            service.update(bobtailFlight);
        } else {
            service.create(bobtailFlight);
        }
        resp.getWriter().print("ok");
    }
}

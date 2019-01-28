package by.gstu.airline.controller.servlet;

import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class FlightDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        BufferedReader reader = req.getReader();
        resp.setContentType("text/plain; charset=UTF-8");
        String jsonFlight;
        if (reader != null) {
            jsonFlight = reader.readLine();
        } else {
            resp.getWriter().print("fail");
            return;
        }
        Gson gson = new Gson();
        Flight bobtailFlight = gson.fromJson(jsonFlight, Flight.class);
        service.delete(bobtailFlight);
        resp.getWriter().print("ok");
    }
}

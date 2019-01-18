package by.gstu.airline.controller.servlet;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        String departureDate = convertDate(bobtailFlight.getDepartureDate());
        String departureTime = convertTime(bobtailFlight.getDepartureTime());
        String arrivalDate = convertDate(bobtailFlight.getArrivalDate());
        String arrivalTime = convertTime(bobtailFlight.getArrivalTime());

        bobtailFlight.setDepartureDate(departureDate);
        bobtailFlight.setDepartureTime(departureTime);
        bobtailFlight.setArrivalDate(arrivalDate);
        bobtailFlight.setArrivalTime(arrivalTime);

        if (bobtailFlight.getId() > 0) {
            service.update(bobtailFlight);
        } else {
            service.create(bobtailFlight);
        }
        resp.getWriter().print("ok");
    }

    private String convertDate(String dateString) {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        SimpleDateFormat fromString = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toString = new SimpleDateFormat(manager.getText("date.format"));
        String result = "";
        try {
            Date date = fromString.parse(dateString);
            result = toString.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String convertTime(String timeString) {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        SimpleDateFormat fromString = new SimpleDateFormat("HH:mm");
        SimpleDateFormat toString = new SimpleDateFormat(manager.getText("time.format"));
        String result = "";
        try {
            Date date = fromString.parse(timeString);
            result = toString.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}

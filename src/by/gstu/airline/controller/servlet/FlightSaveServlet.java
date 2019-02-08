package by.gstu.airline.controller.servlet;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightSaveServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FlightEditServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        return convert(dateString, "yyyy-MM-dd", "date.format");
    }

    private String convertTime(String timeString) {
        return convert(timeString, "HH:mm", "time.format");
    }

    private String convert(String data, String fromPattern, String toPatternProperty) {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        SimpleDateFormat fromString = new SimpleDateFormat(fromPattern);
        SimpleDateFormat toString = new SimpleDateFormat(manager.getText(toPatternProperty));
        String result = "";
        try {
            Date date = fromString.parse(data);
            result = toString.format(date);
        } catch (ParseException e) {
            log.error(e);
        }
        return result;
    }
}

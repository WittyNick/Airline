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
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrewDeleteServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(WelcomeServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        resp.setContentType("text/plain; charset=UTF-8");
        BufferedReader reader = req.getReader();
        String jsonFlight;
        if (reader != null) {
            jsonFlight = reader.readLine();
        } else {
            resp.getWriter().print("fail");
            return;
        }
        Gson gson = new Gson();
        Flight bobtailFlight = gson.fromJson(jsonFlight, Flight.class);
        Locale locale = findSessionLocale(req);
        if (locale == null || !changeDateTimeFormat(bobtailFlight, locale)) {
            resp.getWriter().print("fail");
            return;
        }
        service.deleteMemberByCrewId(bobtailFlight.getCrew().getId());
        service.delete(bobtailFlight.getCrew());
        bobtailFlight.setCrew(null);
        service.update(bobtailFlight);
        resp.getWriter().print("ok");
    }

    private boolean changeDateTimeFormat(Flight flight, Locale locale) {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        String fromPattern = manager.getText(locale,"date.format") + ' ' + manager.getText(locale,"time.format");
        SimpleDateFormat fromFormat = new SimpleDateFormat(fromPattern);
        SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toTimeFormat = new SimpleDateFormat("HH:mm");
        Date departureDateTime;
        Date arrivalDateTime;
        try {
            departureDateTime = fromFormat.parse(flight.getDepartureDate() + ' ' + flight.getDepartureTime());
            arrivalDateTime = fromFormat.parse(flight.getArrivalDate() + ' ' + flight.getArrivalTime());
        } catch (ParseException e) {
            log.warn(e);
            return false;
        }
        flight.setDepartureDate(toDateFormat.format(departureDateTime));
        flight.setDepartureTime(toTimeFormat.format(departureDateTime));
        flight.setArrivalDate(toDateFormat.format(arrivalDateTime));
        flight.setArrivalTime(toTimeFormat.format(arrivalDateTime));
        return true;
    }

    private Locale findSessionLocale(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (Locale) session.getAttribute("locale");
    }
}

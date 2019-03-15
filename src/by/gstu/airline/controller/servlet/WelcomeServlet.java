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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WelcomeServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(WelcomeServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        Gson gson = new Gson();
        List<Flight> flightList = service.readAllFlight();
        changeDateTimeFormat(flightList, findSessionLocale(req));
        String json = gson.toJson(flightList);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(json);
    }

    private void changeDateTimeFormat(List<Flight> flightList, Locale locale) {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat toDateFormat = new SimpleDateFormat(manager.getText(locale,"date.format"));
        SimpleDateFormat toTimeFormat = new SimpleDateFormat(manager.getText(locale,"time.format"));
        Date departureDateTime;
        Date arrivalDateTime;
        for (Flight flight : flightList) {
            try {
                departureDateTime = fromFormat.parse(flight.getDepartureDate() + ' ' + flight.getDepartureTime());
                arrivalDateTime = fromFormat.parse(flight.getArrivalDate() + ' ' + flight.getArrivalTime());
            } catch (ParseException e) {
                log.warn(e);
                continue;
            }
            flight.setDepartureDate(toDateFormat.format(departureDateTime));
            flight.setDepartureTime(toTimeFormat.format(departureDateTime));
            flight.setArrivalDate(toDateFormat.format(arrivalDateTime));
            flight.setArrivalTime(toTimeFormat.format(arrivalDateTime));
        }
    }

    private Locale findSessionLocale(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        if (locale != null) {
            return locale;
        }
        return req.getLocale();
    }
}

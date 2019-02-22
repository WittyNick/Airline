package by.gstu.airline.controller.servlet;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.controller.servlet.util.PageTemplate;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightEditServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FlightEditServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        String contextPath = req.getContextPath();
        String favicon = contextPath + "/img/favicon.ico";
        String cssHeader = contextPath + "/css/header.css";
        String css = contextPath + "/css/flightEdit.css";
        String localeJs = contextPath + "/js/locale.js";
        String js = contextPath + "/js/flightEdit.js";

        int id = Integer.parseInt(req.getParameter("flightId"));
        String flightNumber = "";
        String startPoint = "";
        String destinationPoint = "";
        String departureDate = "";
        String departureTime = "";
        String arrivalDate = "";
        String arrivalTime ="";
        String plane = "";
        String crewId = "";

        if (id > 0) {
            Flight flight = service.readFlightById(id);
            flightNumber += flight.getFlightNumber();
            startPoint = flight.getStartPoint();
            destinationPoint = flight.getDestinationPoint();
            departureDate = convertDate(flight.getDepartureDate());
            departureTime = convertTime(flight.getDepartureTime());
            arrivalDate = convertDate(flight.getArrivalDate());
            arrivalTime = convertTime(flight.getArrivalTime());
            plane = flight.getPlane();
            if (flight.getCrew() != null) {
                crewId += flight.getCrew().getId();
            } else {
                crewId = "0";
            }
        }
        String htmlPage = String.format(PageTemplate.getFlightEditTemplate(),
                favicon, cssHeader, css, localeJs, js, id, crewId, flightNumber, startPoint, destinationPoint,
                departureDate, departureTime, arrivalDate, arrivalTime, plane);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(htmlPage);
    }

    private String convertDate(String dateString) {
        return convert(dateString, "date.format", "yyyy-MM-dd");
    }

    private String convertTime(String timeString) {
        return convert(timeString, "time.format", "HH:mm");
    }

    private String convert(String data, String fromPatternProperty, String toPattern) {
        ConfigurationManager manager = ConfigurationManager.INSTANCE;
        SimpleDateFormat fromString = new SimpleDateFormat(manager.getText(fromPatternProperty));
        SimpleDateFormat toString = new SimpleDateFormat(toPattern);
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

package by.gstu.airline.controller.servlet;

import by.gstu.airline.controller.servlet.util.HtmlTemplateEngine;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FlightEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String contextPath = req.getContextPath();
        int flightId = Integer.parseInt(req.getParameter("flightId"));
        Map<String, String> parameterMap = getPageParameters(contextPath, flightId);
        String fileName = getServletContext().getRealPath("html-template/flight-edit.html");
        String htmlPage = HtmlTemplateEngine.getHtmlPage(fileName, parameterMap);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(htmlPage);
    }

    private Map<String, String> getPageParameters(String contextPath, int flightId) {
        Service service = Service.INSTANCE;
        String flightNumber = "";
        String startPoint = "";
        String destinationPoint = "";
        String departureDate = "";
        String departureTime = "";
        String arrivalDate = "";
        String arrivalTime ="";
        String plane = "";
        String crewId = "";
        if (flightId > 0) {
            Flight flight = service.readFlightById(flightId);
            flightNumber += flight.getFlightNumber();
            startPoint = flight.getStartPoint();
            destinationPoint = flight.getDestinationPoint();
            departureDate = flight.getDepartureDate();
            departureTime = flight.getDepartureTime();
            arrivalDate = flight.getArrivalDate();
            arrivalTime = flight.getArrivalTime();
            plane = flight.getPlane();
            if (flight.getCrew() != null) {
                crewId += flight.getCrew().getId();
            } else {
                crewId = "0";
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("#context.path", contextPath);
        map.put("#id", "" + flightId);
        map.put("#crew.id", crewId);
        map.put("#flight.number", flightNumber);
        map.put("#start.point", startPoint);
        map.put("#destination.point", destinationPoint);
        map.put("#departure.date", departureDate);
        map.put("#departure.time", departureTime);
        map.put("#arrival.date", arrivalDate);
        map.put("#arrival.time", arrivalTime);
        map.put("#plane", plane);
        return map;
    }
}

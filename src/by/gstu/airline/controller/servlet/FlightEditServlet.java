package by.gstu.airline.controller.servlet;

import by.gstu.airline.config.ConfigurationManager;
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

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>FLIGHT EDIT</title>");
        out.println("<link rel=\"shortcut icon\" href=\"" + favicon + "\" type=\"image/x-icon\">");
        out.println("<link rel=\"stylesheet\" href=\"" + cssHeader + "\">");
        out.println("<link rel=\"stylesheet\" href=\"" + css + "\">");
        out.println("<script src=\"" + localeJs + "\"></script>");
        out.println("<script src=\"" + js + "\"></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"content\">");
        out.println("<div id=\"empty\"></div>");
        out.println("<table id=\"header\">");
        out.println("<tr>");
        out.println("<td id=\"space\"></td>");
        out.println("<td id=\"mainTab\" class=\"tab\"><a href=\"../\">main</a></td>");
        out.println("<td id=\"administratorTab\" class=\"picketTab\">administrator</td>");
        out.println("<td id=\"locale\">");
        out.println("<select id=\"lang\">");
        out.println("<option value=\"default\">default</option>");
        out.println("<option value=\"en_US\">english</option>");
        out.println("<option value=\"ru_RU\">русский</option>");
        out.println("</select>");
        out.println("<td id=\"sign\">");
        out.println("<span class=\"pseudolink\" onclick=\"signOut()\">sign out</span>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<form id=\"formMain\">");
        out.println("<input id=\"id\" type=\"hidden\" value=\"" + id + "\">");
        out.println("<input id=\"crewId\" type=\"hidden\" value=\"" + crewId + "\">");
        out.println("<label id=\"labelFlightNumber\" for=\"flightNumber\">flight number:</label><br>");
        out.println("<input id=\"flightNumber\" type=\"text\" value=\"" + flightNumber + "\">");  // flightNumber
        out.println("<span id=\"messageFlightNumber\" class=\"message\"></span><br>");
        out.println("<label id=\"labelStartPoint\" for=\"startPoint\">from:</label><br>");
        out.println("<input id=\"startPoint\" type=\"text\" maxlength=\"30ch\" value=\"" + startPoint + "\">"); // startPoint
        out.println("<span id=\"messageStartPoint\" class=\"message\"></span><br>");
        out.println("<label id=\"labelDestinationPoint\" for=\"destinationPoint\">to:</label><br>");
        out.println("<input id=\"destinationPoint\" type=\"text\" maxlength=\"30ch\" value=\"" + destinationPoint + "\">"); // destinationPoint
        out.println("<span id=\"messageDestinationPoint\" class=\"message\"></span>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<label id=\"labelDepartureDate\" for=\"departureDate\">departure date:</label><br>");
        out.println("<input id=\"departureDate\" type=\"date\" value=\"" + departureDate + "\">"); // departureDate
        out.println("</td>");
        out.println("<td>");
        out.println("<label id=\"labelDepartureTime\" for=\"departureTime\">time:</label><br>");
        out.println("<input id=\"departureTime\" type=\"time\" value=\"" + departureTime + "\">"); // departureTime
        out.println("</td>");
        out.println("<td>");
        out.println("<br><span id=\"messageDepartureDateTime\" class=\"message\"></span>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<label id=\"labelArrivalDate\" for=\"arrivalDate\">arrival date:</label><br>\n");
        out.println("<input id=\"arrivalDate\" type=\"date\" value=\"" + arrivalDate + "\">"); // arrivalDate
        out.println("</td>");
        out.println("<td>");
        out.println("<label id=\"labelArrivalTime\" for=\"arrivalTime\">time:</label><br>");
        out.println("<input id=\"arrivalTime\" type=\"time\" value=\"" + arrivalTime + "\">"); // arrivalTime
        out.println("</td>");
        out.println("<td>");
        out.println("<br><span id=\"messageArrivalDateTime\" class=\"message\"></span>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<label id=\"labelPlane\" for=\"plane\">plane:</label><br>\n");
        out.println("<input id=\"plane\" type=\"text\" maxlength=\"20ch\" value=\"" + plane + "\">"); // plane
        out.println("<span id=\"messagePlane\" class=\"message\"></span><br>");
        out.println("<input id=\"buttonSave\" type=\"button\" value=\"Save\" onclick=\"buttonSaveAction()\">");
        out.println("<input id=\"buttonCancel\" type=\"button\" value=\"Cancel\" onclick=\"buttonCancelAction()\">");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.print("</html>");
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

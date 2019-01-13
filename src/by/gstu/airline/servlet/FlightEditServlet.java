package by.gstu.airline.servlet;

import by.gstu.airline.entity.Flight;
import by.gstu.airline.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FlightEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Service service = Service.INSTANCE;
        String css = req.getContextPath() + "/css/flightEdit.css";
        String js = req.getContextPath() + "/js/flightEdit.js";

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

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>FLIGHT EDIT</title>");
        out.println("<link rel=\"stylesheet\" href=\"" + css + "\">");
        out.println("<script src=\"" + js + "\"></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"content\">");
        out.println("<form id=\"formMain\">");
        out.println("<input id=\"id\" type=\"hidden\" value=\"" + id + "\">");
        out.println("<input id=\"crewId\" type=\"hidden\" value=\"" + crewId + "\">");
        out.println("<label for=\"flightNumber\">flight number:</label><br>");
        out.println("<input id=\"flightNumber\" type=\"text\" value=\"" + flightNumber + "\"><br>");
        out.println("<label for=\"startPoint\">from:</label><br>");
        out.println("<input id=\"startPoint\" type=\"text\" maxlength=\"30ch\" value=\"" + startPoint + "\"><br>");
        out.println("<label for=\"destinationPoint\">to:</label><br>");
        out.println("<input id=\"destinationPoint\" type=\"text\" maxlength=\"30ch\" value=\"" + destinationPoint + "\"><br>");
        out.println("<label for=\"departureDate\">departure date:</label><br>");
        out.println("<input id=\"departureDate\" type=\"text\" maxlength=\"10ch\" value=\"" + departureDate + "\"><br>");
        out.println("<label for=\"departureTime\">departure time:</label><br>");
        out.println("<input id=\"departureTime\" type=\"text\" maxlength=\"5ch\" value=\"" + departureTime + "\"><br>");
        out.println("<label for=\"arrivalDate\">arrival date:</label><br>\n");
        out.println("<input id=\"arrivalDate\" type=\"text\" maxlength=\"10ch\" value=\"" + arrivalDate + "\"><br>");
        out.println("<label for=\"arrivalTime\">arrival time:</label><br>");
        out.println("<input id=\"arrivalTime\" type=\"text\" maxlength=\"5ch\" value=\"" + arrivalTime + "\"><br>");
        out.println("<label for=\"plane\">plane:</label><br>\n");
        out.println("<input id=\"plane\" type=\"text\" maxlength=\"20ch\" value=\"" + plane + "\"><br>");
        out.println("<input type=\"button\" value=\"OK\" onclick=\"buttonOkAction()\">");
        out.println("<input type=\"button\" value=\"Cancel\" onclick=\"buttonCancelAction()\">");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.print("</html>");
    }
}

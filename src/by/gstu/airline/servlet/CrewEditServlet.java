package by.gstu.airline.servlet;

import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CrewEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Service service = Service.INSTANCE;
        String css = req.getContextPath() + "/css/crewEdit.css";
        String js = req.getContextPath() + "/js/crewEdit.js";
        int flightId = Integer.parseInt(req.getParameter("flightId"));
        int crewId = Integer.parseInt(req.getParameter("crewId"));

        String crewName = "";
        String employeeListHtml = "";

        List<Employee> employeeBase = service.readAllEmployee();

        if (crewId > 0) {
            Crew crew = service.readCrewById(crewId);
            crewName = crew.getName();
            employeeListHtml = createTbodyEmployee(crew.getEmployeeList());
            for (Employee crewEmployee : crew.getEmployeeList()) {
                for (int i = 0; i < employeeBase.size(); i++) {
                    if (crewEmployee.getId() == employeeBase.get(i).getId()) {
                        employeeBase.remove(i);
                        break;
                    }
                }
            }
        }

        String employeeBaseHtml = createTbodyEmployee(employeeBase);

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>CREW EDIT</title>");
        out.println("<link rel=\"stylesheet\" href=\"" + css + "\">");
        out.println("<script src=\"" + js + "\"></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"content\">");
        out.println("<input id=\"flightId\" type=\"hidden\" value=\"" + flightId + "\">");
        out.println("<input id=\"crewId\" type=\"hidden\" value=\"" + crewId + "\">");
        out.println("<label for=\"name\">crew name:</label><br>");
        out.println("<input id=\"name\" type=\"text\" value=\"" + crewName + "\"><br>");
        out.println("<table id=\"employeeList\">");
        out.println("<caption>Employee List</caption>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>id</th>");
        out.println("<th>Name</th>");
        out.println("<th>Surname</th>");
        out.println("<th>positionEnum</th>");
        out.println("<th>Position</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody id=\"employeeListBody\">");
        out.println(employeeListHtml);
        out.println("</tbody>");
        out.println("</table>");
        out.println("<input id=\"buttonRemoveFromCrew\" type=\"button\" value=\"Remove from Crew\" onclick=\"removeFromCrewAction()\">");
        out.println("<form>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<label for=\"newEmployeeName\">name:</label><br>");
        out.println("<input id=\"newEmployeeName\" type=\"text\" value=\"\">");
        out.println("</td>");
        out.println("<td>");
        out.println("<label for=\"newEmployeeSurname\">surname:</label><br>");
        out.println("<input id=\"newEmployeeSurname\" type=\"text\">");
        out.println("</td>");
        out.println("<td>");
        out.println("<label for=\"newEmployeePosition\">position:</label><br>");
        out.println("<select id=\"newEmployeePosition\">");
        out.println("<option value=\"PILOT\">pilot</option>");
        out.println("<option value=\"NAVIGATOR\">navigator</option>");
        out.println("<option value=\"COMMUNICATOR\">communicator</option>");
        out.println("<option value=\"STEWARDESS\">stewardess</option>");
        out.println("</select>");
        out.println("</td>");
        out.println("<td>");
        out.println("<br>");
        out.println("<input id=\"engageEmployee\" type=\"button\" value=\"Engage Employee\" onclick=\"engageEmployeeAction()\">");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("<table id=\"employeeBase\">");
        out.println("<caption>Employee Base</caption>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>id</th>");
        out.println("<th>Name</th>");
        out.println("<th>Surname</th>");
        out.println("<th>positionEnum</th>");
        out.println("<th>Position</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody id=\"employeeBaseBody\">");
        out.println(employeeBaseHtml);
        out.println("</tbody>");
        out.println("</table>");
        out.println("<input id=\"buttonAddToCrew\" type=\"button\" value=\"Add to Crew\" onclick=\"addToCrewAction()\">");
        out.println("<input id=\"fireEmployee\" type=\"button\" value=\"Fire Employee\" onclick=\"fireEmployeeAction()\"><br>");
        out.println("<div id=\"buttons\">");
        out.println("<input id=\"Save\" type=\"button\" value=\"Save\" onclick=\"saveAction()\">");
        out.println("<input id=\"Cancel\" type=\"button\" value=\"Cancel\" onclick=\"cancelAction()\">");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.print("</html>");
    }

    private String createTbodyEmployee(List<Employee> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (Employee employee : list) {
            result.append(createTrEmployee(employee));
        }
        return result.toString();
    }

    private String createTrEmployee(Employee employee) {
        return "<tr>\n"+
        "<td>" + employee.getId() + "</td>\n" +
        "<td>" + employee.getName() + "</td>\n" +
        "<td>" + employee.getSurname() + "</td>\n" +
        "<td>" + employee.getPosition().name() + "</td>\n" +
        "<td></td>\n" +
        "</tr>\n";
    }
}

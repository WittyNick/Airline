package by.gstu.airline.controller.servlet;

import by.gstu.airline.controller.servlet.util.HtmlTemplateEngine;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.service.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int flightId = Integer.parseInt(req.getParameter("flightId"));
        int crewId = Integer.parseInt(req.getParameter("crewId"));
        Map<String, String> parameterMap = getParameterMap(flightId, crewId);
        String fileName = getServletContext().getRealPath("html/template/crew-edit.html");
        String htmlPage = HtmlTemplateEngine.getHtmlPage(fileName, parameterMap);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(htmlPage);
    }

    private Map<String, String> getParameterMap(int flightId, int crewId) {
        Service service = Service.INSTANCE;
        String contextPath = getServletContext().getContextPath();
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
        Map<String, String> map = new HashMap<>();
        map.put("#context.path", contextPath);
        map.put("#flight.id", "" + flightId);
        map.put("#crew.id", "" + crewId);
        map.put("#crew.name", crewName);
        map.put("#employee.list.body", employeeListHtml);
        map.put("#employee.base.body", employeeBaseHtml);
        return map;
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
        return "<tr>" +
                "<td>" + employee.getId() + "</td>" +
                "<td>" + employee.getName() + "</td>" +
                "<td>" + employee.getSurname() + "</td>" +
                "<td>" + employee.getPosition().name() + "</td>" +
                "<td></td>" +
                "</tr>";
    }
}

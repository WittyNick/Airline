package by.gstu.airline.controller.servlet;

import by.gstu.airline.controller.servlet.util.PageTemplate;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.service.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CrewEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        String contextPath = req.getContextPath();
        String favicon = contextPath + "/img/favicon.ico";
        String cssHeader = contextPath + "/css/header.css";
        String css = contextPath + "/css/crewEdit.css";
        String localeJs = contextPath + "/js/locale.js";
        String js = contextPath + "/js/crewEdit.js";
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

        String htmlPage = String.format(PageTemplate.getCrewEditTemplate(),
                favicon, cssHeader, css, localeJs, js, flightId, crewId, crewName, employeeListHtml, employeeBaseHtml);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(htmlPage);
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

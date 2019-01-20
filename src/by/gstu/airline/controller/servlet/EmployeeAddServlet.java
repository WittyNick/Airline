package by.gstu.airline.controller.servlet;

import by.gstu.airline.entity.Employee;
import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class EmployeeAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        BufferedReader reader = req.getReader();
        String jsonEmployee = "";
        if (reader != null) {
            jsonEmployee = reader.readLine();
        }
        Gson gson = new Gson();
        Employee employee = gson.fromJson(jsonEmployee, Employee.class);
        service.create(employee);
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().print(gson.toJson(employee));
    }
}

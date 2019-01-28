package by.gstu.airline.controller.servlet;

import by.gstu.airline.entity.Employee;
import by.gstu.airline.service.Service;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class EmployeeDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Service service = Service.INSTANCE;
        resp.setContentType("text/plain; charset=UTF-8");
        BufferedReader reader = req.getReader();
        String jsonEmployee = "";
        if (reader != null) {
            jsonEmployee = reader.readLine();
        } else {
            resp.getWriter().print("fail");
        }
        Gson gson = new Gson();
        Employee employee = gson.fromJson(jsonEmployee, Employee.class);
        service.delete(employee);
        resp.getWriter().print("ok");
    }
}

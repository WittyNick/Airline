package by.gstu.airline.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if ("admin".equals(login) && "admin".equals(password)) {
            printWriter.print("administrator");
        } else if ("dispatcher".equals(login) && "dispatcher".equals(password)) {
            printWriter.print("dispatcher");
        } else {
            printWriter.print("user");
        }
    }
}

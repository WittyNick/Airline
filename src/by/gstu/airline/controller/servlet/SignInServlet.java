package by.gstu.airline.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signIn.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if ("admin".equals(login) && "admin".equals(password)) {
            session.setAttribute("role", "administrator");
            printWriter.print("administrator");
        } else if ("dispatcher".equals(login) && "dispatcher".equals(password)) {
            session.setAttribute("role", "dispatcher");
            printWriter.print("dispatcher");
        } else {
            printWriter.print("fail");
        }
    }
}

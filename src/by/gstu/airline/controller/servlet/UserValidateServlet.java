package by.gstu.airline.controller.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Checks user's role in session.
 */
public class UserValidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        resp.setContentType("text/plain; charset=UTF-8");
        String role = null;
        if (session != null) {
            role = (String) session.getAttribute("role");
        }
        if (role != null) {
            resp.getWriter().print(role);
        } else {
            resp.getWriter().print("guest");
        }
    }
}

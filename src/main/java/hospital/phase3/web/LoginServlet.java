
package hospital.phase3.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Classroom demonstration only.
        if ("admin".equals(username) &&
            "admin123".equals(password)) {

            HttpSession session = request.getSession(true);

            request.changeSessionId();
            session.setAttribute("username", username);

            response.sendRedirect(
                request.getContextPath() + "/records?type=patients"
            );

        } else {

            request.setAttribute(
                "error",
                "Incorrect username or password"
            );

            doGet(request, response);
        }
    }
}

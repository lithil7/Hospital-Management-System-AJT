
package hospital.phase3.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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

        request.getRequestDispatcher(
                "/WEB-INF/views/login.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String validUsername =
                System.getenv("ADMIN_USERNAME");

        String validPassword =
                System.getenv("ADMIN_PASSWORD");

        boolean configured =
                validUsername != null &&
                !validUsername.isEmpty() &&
                validPassword != null &&
                !validPassword.isEmpty();

        boolean correctUsername = configured &&
                secureEquals(username, validUsername);

        boolean correctPassword = configured &&
                secureEquals(password, validPassword);

        if (correctUsername && correctPassword) {

            HttpSession session = request.getSession(true);

            request.changeSessionId();

            session.setAttribute("username", username);

            response.sendRedirect(
                request.getContextPath()
                + "/records?type=patients"
            );

        } else {

            request.setAttribute(
                "error",
                "Incorrect username or password"
            );

            doGet(request, response);
        }
    }

    private boolean secureEquals(String supplied,
                                 String expected) {

        if (supplied == null || expected == null) {
            return false;
        }

        return MessageDigest.isEqual(
                supplied.getBytes(StandardCharsets.UTF_8),
                expected.getBytes(StandardCharsets.UTF_8)
        );
    }
}

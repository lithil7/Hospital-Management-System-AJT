
package hospital.phase3.web;

import hospital.phase3.hibernate.HibernateDoctorDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hibernate-doctors")
public class HibernateDoctorsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HibernateDoctorDAO dao = new HibernateDoctorDAO();

            request.setAttribute(
                "doctors",
                dao.getAllDoctors()
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/hibernate.jsp"
            ).forward(request, response);

        } catch (Exception exception) {

            throw new ServletException(
                "Hibernate could not load doctors",
                exception
            );
        }
    }
}

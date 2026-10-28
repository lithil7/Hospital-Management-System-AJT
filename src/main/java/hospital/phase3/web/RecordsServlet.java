
package hospital.phase3.web;

import hospital.phase3.db.HospitalDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/records")
public class RecordsServlet extends HttpServlet {

    private final HospitalDAO dao = new HospitalDAO();

    private String getEntity(HttpServletRequest request) {

        String type = request.getParameter("type");

        if ("patients".equals(type) ||
            "doctors".equals(type) ||
            "appointments".equals(type)) {

            return type;
        }

        return "patients";
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String entity = getEntity(request);

        request.setAttribute("entity", entity);

        try {

            request.setAttribute(
                "rows",
                dao.list(entity)
            );

            if ("appointments".equals(entity)) {

                request.setAttribute(
                    "patients",
                    dao.list("patients")
                );

                request.setAttribute(
                    "doctors",
                    dao.list("doctors")
                );
            }

            request.getRequestDispatcher(
                "/WEB-INF/views/records.jsp"
            ).forward(request, response);

        } catch (SQLException exception) {

            throw new ServletException(
                "Database error: " + exception.getMessage(),
                exception
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String entity = getEntity(request);
        String action = request.getParameter("action");

        try {

            if ("delete".equals(action)) {

                int id = Integer.parseInt(
                    request.getParameter("id")
                );

                dao.delete(entity, id);

            } else if ("status".equals(action) &&
                       "appointments".equals(entity)) {

                int id = Integer.parseInt(
                    request.getParameter("id")
                );

                String status = request.getParameter("status");

                if (!"Scheduled".equals(status) &&
                    !"Completed".equals(status) &&
                    !"Cancelled".equals(status)) {

                    throw new IllegalArgumentException(
                        "Invalid appointment status"
                    );
                }

                dao.updateStatus(id, status);

            } else if ("add".equals(action)) {

                if ("patients".equals(entity)) {

                    String name = request.getParameter("name");
                    String ageText = request.getParameter("age");
                    String gender = request.getParameter("gender");
                    String phone = request.getParameter("phone");
                    String email = request.getParameter("email");
                    String address = request.getParameter("address");

                    if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException(
                            "Patient name is required"
                        );
                    }

                    int age = Integer.parseInt(ageText);

                    if (age < 0 || age > 120) {
                        throw new IllegalArgumentException(
                            "Age must be between 0 and 120"
                        );
                    }

                    if (phone == null ||
                        !phone.matches("[0-9]{10,15}")) {

                        throw new IllegalArgumentException(
                            "Phone must contain 10 to 15 digits"
                        );
                    }

                    dao.addPatient(
                        name,
                        age,
                        gender,
                        phone,
                        email,
                        address
                    );

                } else if ("doctors".equals(entity)) {

                    String name = request.getParameter("name");
                    String specialization =
                            request.getParameter("specialization");

                    String phone = request.getParameter("phone");
                    String email = request.getParameter("email");

                    if (name == null || name.trim().isEmpty() ||
                        specialization == null ||
                        specialization.trim().isEmpty()) {

                        throw new IllegalArgumentException(
                            "Doctor name and specialization are required"
                        );
                    }

                    dao.addDoctor(
                        name,
                        specialization,
                        phone,
                        email
                    );

                } else {

                    int patientId = Integer.parseInt(
                        request.getParameter("patient_id")
                    );

                    int doctorId = Integer.parseInt(
                        request.getParameter("doctor_id")
                    );

                    String date =
                            request.getParameter("appointment_date");

                    String time =
                            request.getParameter("appointment_time");

                    String reason = request.getParameter("reason");

                    dao.addAppointment(
                        patientId,
                        doctorId,
                        date,
                        time,
                        reason,
                        "Scheduled"
                    );
                }

            } else {

                throw new IllegalArgumentException(
                    "Unknown operation"
                );
            }

            response.sendRedirect(
                request.getContextPath() +
                "/records?type=" + entity
            );

        } catch (SQLException | IllegalArgumentException exception) {

            request.setAttribute(
                "error",
                "Could not save changes: " + exception.getMessage()
            );

            doGet(request, response);
        }
    }
}

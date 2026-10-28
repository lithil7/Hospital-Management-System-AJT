
package hospital.phase3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HospitalDAO {

    public List<Map<String, Object>> list(String entity)
            throws SQLException {

        String sql;

        switch (entity) {
            case "patients":
                sql = "SELECT * FROM patients ORDER BY patient_id";
                break;

            case "doctors":
                sql = "SELECT * FROM doctors ORDER BY doctor_id";
                break;

            case "appointments":
                sql = "SELECT a.appointment_id, a.patient_id, " +
                      "p.name AS patient_name, a.doctor_id, " +
                      "d.name AS doctor_name, a.appointment_date, " +
                      "a.appointment_time, a.reason, a.status " +
                      "FROM appointments a " +
                      "JOIN patients p ON p.patient_id = a.patient_id " +
                      "JOIN doctors d ON d.doctor_id = a.doctor_id " +
                      "ORDER BY a.appointment_id";
                break;

            default:
                throw new IllegalArgumentException("Invalid table");
        }

        List<Map<String, Object>> rows = new ArrayList<>();

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            ResultSetMetaData metadata = result.getMetaData();

            while (result.next()) {

                Map<String, Object> row = new LinkedHashMap<>();

                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    String columnName = metadata.getColumnLabel(i);
                    Object value = result.getObject(i);
                    row.put(columnName, value);
                }

                rows.add(row);
            }
        }

        return rows;
    }

    public void addPatient(
            String name,
            int age,
            String gender,
            String phone,
            String email,
            String address) throws SQLException {

        String sql = "INSERT INTO patients " +
                     "(name, age, gender, phone, email, address) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, gender);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, address);

            statement.executeUpdate();
        }
    }

    public void addDoctor(
            String name,
            String specialization,
            String phone,
            String email) throws SQLException {

        String sql = "INSERT INTO doctors " +
                     "(name, specialization, phone, email) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, specialization);
            statement.setString(3, phone);
            statement.setString(4, email);

            statement.executeUpdate();
        }
    }

    public void addAppointment(
            int patientId,
            int doctorId,
            String date,
            String time,
            String reason,
            String status) throws SQLException {

        String sql = "INSERT INTO appointments " +
                     "(patient_id, doctor_id, appointment_date, " +
                     "appointment_time, reason, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        if (time.length() == 5) {
            time = time + ":00";
        }

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);
            statement.setInt(2, doctorId);
            statement.setDate(3, java.sql.Date.valueOf(date));
            statement.setTime(4, java.sql.Time.valueOf(time));
            statement.setString(5, reason);
            statement.setString(6, status);

            statement.executeUpdate();
        }
    }

    public void updateStatus(int appointmentId, String status)
            throws SQLException {

        String sql = "UPDATE appointments " +
                     "SET status = ? WHERE appointment_id = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, appointmentId);

            statement.executeUpdate();
        }
    }

    public void delete(String entity, int id) throws SQLException {

        String sql;

        switch (entity) {
            case "patients":
                sql = "DELETE FROM patients WHERE patient_id = ?";
                break;

            case "doctors":
                sql = "DELETE FROM doctors WHERE doctor_id = ?";
                break;

            case "appointments":
                sql = "DELETE FROM appointments WHERE appointment_id = ?";
                break;

            default:
                throw new IllegalArgumentException("Invalid table");
        }

        try (Connection connection = DB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}

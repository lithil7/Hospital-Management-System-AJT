
package hospital.phase3.desktop;

import hospital.phase3.db.HospitalDAO;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.table.DefaultTableModel;

public class HospitalSwing extends JFrame {

    private final HospitalDAO dao = new HospitalDAO();

    private final JTabbedPane tabs = new JTabbedPane();

    public HospitalSwing() {

        setTitle("Hospital Management System - Swing");

        setSize(1000, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(tabs);

        tabs.addTab(
            "Patients",
            createPanel("patients")
        );

        tabs.addTab(
            "Doctors",
            createPanel("doctors")
        );

        tabs.addTab(
            "Appointments",
            createPanel("appointments")
        );
    }

    private JPanel createPanel(String entity) {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel top = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );

        JButton refreshButton = new JButton("Refresh");

        JButton addButton = new JButton(
            "Add " + entity.substring(0, entity.length() - 1)
        );

        JButton deleteButton = new JButton("Delete Selected");

        top.add(new JLabel("Table: " + entity));
        top.add(refreshButton);
        top.add(addButton);
        top.add(deleteButton);

        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        panel.add(top, BorderLayout.NORTH);

        panel.add(
            new JScrollPane(table),
            BorderLayout.CENTER
        );

        refreshButton.addActionListener(event -> {

            loadRecords(entity, model);
        });

        addButton.addActionListener(event -> {

            addRecord(entity);

            loadRecords(entity, model);
        });

        deleteButton.addActionListener(event -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow < 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please select a record first."
                );

                return;
            }

            int id = Integer.parseInt(
                table.getValueAt(selectedRow, 0).toString()
            );

            int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Delete record with ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {

                try {

                    dao.delete(entity, id);

                    JOptionPane.showMessageDialog(
                        this,
                        "Record deleted successfully."
                    );

                } catch (SQLException exception) {

                    showError(exception);
                }

                loadRecords(entity, model);
            }
        });

        loadRecords(entity, model);

        return panel;
    }

    private void loadRecords(
            String entity,
            DefaultTableModel model) {

        try {

            List<Map<String, Object>> records =
                    dao.list(entity);

            model.setRowCount(0);
            model.setColumnCount(0);

            if (records.isEmpty()) {

                String[] columns;

                if ("patients".equals(entity)) {

                    columns = new String[] {
                        "patient_id",
                        "name",
                        "age",
                        "gender",
                        "phone",
                        "email",
                        "address"
                    };

                } else if ("doctors".equals(entity)) {

                    columns = new String[] {
                        "doctor_id",
                        "name",
                        "specialization",
                        "phone",
                        "email"
                    };

                } else {

                    columns = new String[] {
                        "appointment_id",
                        "patient_id",
                        "patient_name",
                        "doctor_id",
                        "doctor_name",
                        "appointment_date",
                        "appointment_time",
                        "reason",
                        "status"
                    };
                }

                for (String column : columns) {
                    model.addColumn(column);
                }

            } else {

                for (String column : records.get(0).keySet()) {
                    model.addColumn(column);
                }

                for (Map<String, Object> record : records) {

                    model.addRow(
                        record.values().toArray()
                    );
                }
            }

        } catch (SQLException exception) {

            showError(exception);
        }
    }

    private void addRecord(String entity) {

        String[] labels;

        if ("patients".equals(entity)) {

            labels = new String[] {
                "Name",
                "Age",
                "Gender",
                "Phone",
                "Email",
                "Address"
            };

        } else if ("doctors".equals(entity)) {

            labels = new String[] {
                "Name",
                "Specialization",
                "Phone",
                "Email"
            };

        } else {

            labels = new String[] {
                "Patient ID",
                "Doctor ID",
                "Date (YYYY-MM-DD)",
                "Time (HH:MM)",
                "Reason"
            };
        }

        JPanel form = new JPanel(
            new GridLayout(labels.length, 2, 8, 8)
        );

        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {

            form.add(new JLabel(labels[i]));

            fields[i] = new JTextField(18);

            form.add(fields[i]);
        }

        int result = JOptionPane.showConfirmDialog(
            this,
            form,
            "Add " + entity,
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            if ("patients".equals(entity)) {

                int age = Integer.parseInt(
                    fields[1].getText()
                );

                if (age < 0 || age > 120) {
                    throw new IllegalArgumentException(
                        "Age must be between 0 and 120."
                    );
                }

                dao.addPatient(
                    fields[0].getText(),
                    age,
                    fields[2].getText(),
                    fields[3].getText(),
                    fields[4].getText(),
                    fields[5].getText()
                );

            } else if ("doctors".equals(entity)) {

                dao.addDoctor(
                    fields[0].getText(),
                    fields[1].getText(),
                    fields[2].getText(),
                    fields[3].getText()
                );

            } else {

                int patientId = Integer.parseInt(
                    fields[0].getText()
                );

                int doctorId = Integer.parseInt(
                    fields[1].getText()
                );

                dao.addAppointment(
                    patientId,
                    doctorId,
                    fields[2].getText(),
                    fields[3].getText(),
                    fields[4].getText(),
                    "Scheduled"
                );
            }

            JOptionPane.showMessageDialog(
                this,
                "Record added successfully."
            );

        } catch (SQLException | IllegalArgumentException exception) {

            showError(exception);
        }
    }

    private void showError(Exception exception) {

        JOptionPane.showMessageDialog(
            this,
            exception.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            HospitalSwing window = new HospitalSwing();

            window.setVisible(true);
        });
    }
}

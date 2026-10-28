
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn"
           uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hospital Records</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<header>
    <h1>Hospital Management System</h1>

    <nav>
        <a href="${pageContext.request.contextPath}/records?type=patients">
            Patients
        </a>

        <a href="${pageContext.request.contextPath}/records?type=doctors">
            Doctors
        </a>

        <a href="${pageContext.request.contextPath}/records?type=appointments">
            Appointments
        </a>

        <a href="${pageContext.request.contextPath}/hibernate-doctors">
            Hibernate
        </a>

        <a href="${pageContext.request.contextPath}/spring/doctors">
            Spring MVC
        </a>

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>
    </nav>
</header>

<main>

    <h2>
        <c:out value="${fn:toUpperCase(entity)}" />
    </h2>

    <c:if test="${not empty error}">
        <p class="error">
            <c:out value="${error}" />
        </p>
    </c:if>

    <h3>Add New Record</h3>

    <form method="post"
          action="${pageContext.request.contextPath}/records?type=${entity}">

        <input type="hidden"
               name="action"
               value="add">

        <c:choose>

            <c:when test="${entity eq 'patients'}">

                <input type="text"
                       name="name"
                       placeholder="Patient Name"
                       maxlength="100"
                       required>

                <input type="number"
                       name="age"
                       placeholder="Age"
                       min="0"
                       max="120"
                       required>

                <select name="gender">
                    <option>Male</option>
                    <option>Female</option>
                    <option>Other</option>
                </select>

                <input type="tel"
                       name="phone"
                       placeholder="Phone Number"
                       pattern="[0-9]{10,15}"
                       maxlength="15"
                       required>

                <input type="email"
                       name="email"
                       placeholder="Email"
                       maxlength="100">

                <input type="text"
                       name="address"
                       placeholder="Address"
                       maxlength="200">

            </c:when>

            <c:when test="${entity eq 'doctors'}">

                <input type="text"
                       name="name"
                       placeholder="Doctor Name"
                       maxlength="100"
                       required>

                <input type="text"
                       name="specialization"
                       placeholder="Specialization"
                       maxlength="100"
                       required>

                <input type="tel"
                       name="phone"
                       placeholder="Phone Number"
                       maxlength="15">

                <input type="email"
                       name="email"
                       placeholder="Email"
                       maxlength="100">

            </c:when>

            <c:otherwise>

                <label>
                    Patient
                    <select name="patient_id" required>

                        <c:forEach var="p" items="${patients}">
                            <option value="${p.patient_id}">
                                <c:out value="${p.name}" />
                            </option>
                        </c:forEach>

                    </select>
                </label>

                <label>
                    Doctor
                    <select name="doctor_id" required>

                        <c:forEach var="d" items="${doctors}">
                            <option value="${d.doctor_id}">
                                <c:out value="${d.name}" />
                            </option>
                        </c:forEach>

                    </select>
                </label>

                <input type="date"
                       name="appointment_date"
                       required>

                <input type="time"
                       name="appointment_time"
                       required>

                <input type="text"
                       name="reason"
                       placeholder="Reason for Appointment"
                       maxlength="200">

            </c:otherwise>

        </c:choose>

        <button type="submit">
            Add Record
        </button>

    </form>

    <h3>Existing Records</h3>

    <div class="overflow">

        <table>

            <thead>
                <tr>

                    <c:choose>

                        <c:when test="${entity eq 'patients'}">
                            <th>ID</th>
                            <th>Name</th>
                            <th>Age</th>
                            <th>Gender</th>
                            <th>Phone</th>
                            <th>Email</th>
                            <th>Address</th>
                        </c:when>

                        <c:when test="${entity eq 'doctors'}">
                            <th>ID</th>
                            <th>Name</th>
                            <th>Specialization</th>
                            <th>Phone</th>
                            <th>Email</th>
                        </c:when>

                        <c:otherwise>
                            <th>ID</th>
                            <th>Patient</th>
                            <th>Doctor</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Reason</th>
                            <th>Status</th>
                        </c:otherwise>

                    </c:choose>

                    <th>Action</th>

                </tr>
            </thead>

            <tbody>

                <c:forEach var="r" items="${rows}">

                    <tr>

                        <c:choose>

                            <c:when test="${entity eq 'patients'}">

                                <td>${r.patient_id}</td>
                                <td><c:out value="${r.name}" /></td>
                                <td>${r.age}</td>
                                <td><c:out value="${r.gender}" /></td>
                                <td><c:out value="${r.phone}" /></td>
                                <td><c:out value="${r.email}" /></td>
                                <td><c:out value="${r.address}" /></td>

                            </c:when>

                            <c:when test="${entity eq 'doctors'}">

                                <td>${r.doctor_id}</td>
                                <td><c:out value="${r.name}" /></td>
                                <td><c:out value="${r.specialization}" /></td>
                                <td><c:out value="${r.phone}" /></td>
                                <td><c:out value="${r.email}" /></td>

                            </c:when>

                            <c:otherwise>

                                <td>${r.appointment_id}</td>
                                <td><c:out value="${r.patient_name}" /></td>
                                <td><c:out value="${r.doctor_name}" /></td>
                                <td>${r.appointment_date}</td>
                                <td>${r.appointment_time}</td>
                                <td><c:out value="${r.reason}" /></td>

                                <td>

                                    <form method="post"
                                          action="${pageContext.request.contextPath}/records?type=appointments">

                                        <input type="hidden"
                                               name="action"
                                               value="status">

                                        <input type="hidden"
                                               name="id"
                                               value="${r.appointment_id}">

                                        <select name="status">

                                            <option value="Scheduled"
                                                ${r.status eq 'Scheduled' ? 'selected' : ''}>
                                                Scheduled
                                            </option>

                                            <option value="Completed"
                                                ${r.status eq 'Completed' ? 'selected' : ''}>
                                                Completed
                                            </option>

                                            <option value="Cancelled"
                                                ${r.status eq 'Cancelled' ? 'selected' : ''}>
                                                Cancelled
                                            </option>

                                        </select>

                                        <button type="submit">
                                            Update
                                        </button>

                                    </form>

                                </td>

                            </c:otherwise>

                        </c:choose>

                        <td>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/records?type=${entity}"
                                  onsubmit="return confirm('Delete this record?')">

                                <input type="hidden"
                                       name="action"
                                       value="delete">

                                <input type="hidden"
                                       name="id"
                                       value="${entity eq 'patients' ? r.patient_id : (entity eq 'doctors' ? r.doctor_id : r.appointment_id)}">

                                <button type="submit" class="danger">
                                    Delete
                                </button>

                            </form>

                        </td>

                    </tr>

                </c:forEach>

            </tbody>
        </table>

    </div>

    <p>
        Use fictional data for testing.
        Delete linked appointments before deleting
        their patients or doctors.
    </p>

</main>

</body>
</html>

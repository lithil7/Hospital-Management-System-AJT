
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hibernate Doctors</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<main>

    <h1>Doctors Fetched Using Hibernate ORM</h1>

    <p>
        This page retrieves records from the doctors
        table using Hibernate instead of direct JDBC.
    </p>

    <a href="${pageContext.request.contextPath}/records?type=doctors">
        Back to Doctors
    </a>

    <table>

        <thead>
            <tr>
                <th>Doctor ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Phone</th>
                <th>Email</th>
            </tr>
        </thead>

        <tbody>

            <c:forEach var="doctor" items="${doctors}">

                <tr>

                    <td>${doctor.doctorId}</td>

                    <td>
                        <c:out value="${doctor.name}" />
                    </td>

                    <td>
                        <c:out value="${doctor.specialization}" />
                    </td>

                    <td>
                        <c:out value="${doctor.phone}" />
                    </td>

                    <td>
                        <c:out value="${doctor.email}" />
                    </td>

                </tr>

            </c:forEach>

        </tbody>

    </table>

</main>

</body>
</html>

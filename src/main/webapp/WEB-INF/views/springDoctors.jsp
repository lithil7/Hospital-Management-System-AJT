
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

    <title>Spring MVC - Hospital Management</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/style.css">

</head>

<body>

<main>

    <h1>Spring MVC Demonstration</h1>

    <p>
        <c:out value="${message}" />
    </p>

    <p>
        This page demonstrates a Spring Controller,
        Service class, Dependency Injection,
        and database connectivity.
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
            </tr>

        </thead>

        <tbody>

            <c:forEach var="doctor" items="${rows}">

                <tr>

                    <td>${doctor.doctor_id}</td>

                    <td>
                        <c:out value="${doctor.name}" />
                    </td>

                    <td>
                        <c:out value="${doctor.specialization}" />
                    </td>

                </tr>

            </c:forEach>

        </tbody>

    </table>

</main>

</body>
</html>

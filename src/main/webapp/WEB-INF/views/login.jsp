
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hospital Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/style.css">
</head>

<body>

    <main class="login">

        <h1>Hospital Management System</h1>

        <p>Phase 3 - Advanced Java Technology</p>

        <c:if test="${not empty error}">
            <p class="error">
                <c:out value="${error}" />
            </p>
        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/login">

            <label>
                Username
                <input type="text"
                       name="username"
                       required>
            </label>

            <label>
                Password
                <input type="password"
                       name="password"
                       required>
            </label>

            <button type="submit">
                Login
            </button>

        </form>

        <small>
            Student demonstration login:
            admin / admin123
        </small>

    </main>

</body>
</html>

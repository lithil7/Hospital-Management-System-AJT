
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Application Error</title>
</head>

<body>

    <h2>Something went wrong</h2>

    <p>
        Please check your database connection,
        server settings, and NetBeans output window.
    </p>

    <a href="<%= request.getContextPath() %>/login">
        Return to Login
    </a>

</body>
</html>

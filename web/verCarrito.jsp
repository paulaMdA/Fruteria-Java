<%-- 
    Document   : mostrar
    Created on : 29-ene-2024, 23:49:05
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Carrito</h1>
        <form action="controlador" method="get">
            <input type = "email" name = "email" value="<%=request.getAttribute("email")%>" hidden>
            <input type="text" name="rol" value="<%= request.getAttribute("rol")%>" hidden>

            <input type="submit" name="submit" value="Atras" />
            <br>
        </form>
    </body>
</html>

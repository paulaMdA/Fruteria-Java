<%-- 
    Document   : menu
    Created on : 29-ene-2024, 19:53:53
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
        <h3>Bienvenido, <%=request.getAttribute("email")%></h3> 
        <hr>
        <%
            String rol = (String) request.getAttribute("rol");
            System.out.println("ROLLL: " + rol);
        %>
        <form action="controlador" method="post">
            <input type = "email" name = "email" value="<%=request.getAttribute("email")%>" hidden>
            <input type="text" name="rol" value="<%= request.getAttribute("rol")%>" hidden>
            <input type="submit" name="submit" value="Ver Productos" />
            <br>

            <%if ("vendedor".equals(rol) || "comprador".equals(rol)) {%>
            <input type="submit" name="submit" value="Ver Carrito" />
            <br>
            <input type="submit" name="submit" value="Insertar Producto" />
            <br>
            <%}%>
            <input type="submit" name="submit" value="Salir" />
            <br>
        </form>
    </body>
</html>

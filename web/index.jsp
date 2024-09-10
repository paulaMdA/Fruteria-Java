<%-- 
    Document   : index
    Created on : 24-feb-2024, 16:22:35
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
        <h3>Inicio de sesion</h3>
        <hr>
        <form action="controlador" method="get">

            <input type = "email" name = "email" value = "" placeholder="Nombre de usuario" /><br>
            <input type="password" name="password" value="" placeholder="Contraseña" /><br>

            <input type="submit" value="Register" name="submit" />
            <input type="submit" value="Login" name="submit" />

        </form>
    </body>
</body>
</html>

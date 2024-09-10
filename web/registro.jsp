<%-- 
    Document   : registro
    Created on : 29-ene-2024, 23:45:07
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
        <h3>Registro</h3>
        <hr>
        <form action="controlador" method="get">

            <input type = "text" name = "usuario" value = "" placeholder="Nombre de usuario" /><br>
            <input type = "text" name = "apellidos" value = "" placeholder="Apellidos de usuario" /><br>
            <input type = "email" name = "email" value = "" placeholder="Email de usuario" /><br>
            <input type="password" name="password" value="" placeholder="Contraseña" /><br>
            <input type="password" name="passwordAgain" value="" placeholder="Repita la contraseña" /><br>

            <input type="submit" value="Registrarme" name="submit" />
            <input type="submit" value="Salir" name="submit" />

        </form>
    </body>
</html>

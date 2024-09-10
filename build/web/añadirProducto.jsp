<%-- 
    Document   : añadirProducto
    Created on : 25-feb-2024, 5:55:35
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
        <h1>Añadir producto</h1>
        <hr>
        
        <form action="controlador" method="POST">
            <input type="email" name = "email" value="<%=request.getAttribute("email")%>" hidden>
            <input type="text" name="rol" value="<%= request.getAttribute("rol")%>" hidden>
            <input type="text" name="insertNombreP" value="" size="20" placeholder="Nombre de Producto" /><br>
            <input type="text" name="insertDescripcionP" value="" size="20" placeholder="Descripcion de Producto" /><br>
            <input type="text" name="insertPrecioP" value="" size="20" placeholder="Precio de Producto" /><br>
            <input type="text" name="insertFotoP" value="" size="20" placeholder="url de foto de Producto" /><br>
            <br>
            <br>
            <input type="submit" name="submit" value="Insertar" />
            <input type="submit" name="submit" value="Atras" />


        </form>
    </body>
</html>

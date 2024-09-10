<%@ page import="java.util.ArrayList" %>
<%@ page import="DML.Producto" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Tienda</h1>
        <hr>
        <form action="controlador" method="post">
            <input type="email" name="email" value="<%= request.getAttribute("email")%>" hidden>
            <input type="text" name="rol" value="<%= request.getAttribute("rol")%>" hidden>
            <input type="text" name="text" value="<%= request.getAttribute("listado")%>" hidden>

            <select name="productos">
                <%
                    ArrayList<Producto> productos = (ArrayList<Producto>) request.getAttribute("listado");
                    System.out.println("PRODUCTOS: " + productos);
                    for (int i = 0; i < productos.size(); i++) {
                        Producto producto = productos.get(i);
                %>
                <option value="<%= producto.getId()%>">
                    <%= "ID: " + producto.getId() + " | "
                            + "Nombre: " + producto.getNombre() + " | "
                            + "Descripción: " + producto.getDescripcion() + " | "
                            + "Precio: " + producto.getPrecio() + " €"%>
                </option>
                <% } %>
            </select>
            <br><br>

            <input type="submit" name="submit" value="Agregar al carrito" />
            <br>
        </form>

        <%
            ArrayList<String> carrito = (ArrayList<String>) request.getSession().getAttribute("carrito");
            if (carrito != null && !carrito.isEmpty()) {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
            </tr>
            <%
                double total = 0;
                for (String productoId : carrito) {
                    Producto producto = Producto.obtenerProductoPorId(Integer.parseInt(productoId));
                    total += Double.parseDouble( producto.getPrecio());
            %>
            <tr>
                <td><%= producto.getId()%></td>
                <td><%= producto.getNombre()%></td>
                <td><%= producto.getDescripcion()%></td>
                <td><%= producto.getPrecio()%></td>
            </tr>
            <% }%>
            <tr>
                <td colspan="3">Total:</td>
                <td><%= total%></td>
            </tr>
        </table>
        <%
            } else {
                out.println("No hay productos en el carrito");
            }
        %>
        <form action="controlador" method="post">
            <input type="email" name="email" value="<%= request.getAttribute("email")%>" hidden>
            <input type="text" name="rol" value="<%= request.getAttribute("rol")%>" hidden>
            <input type="text" name="rol" value="<%= request.getAttribute("rol")%>" hidden>
            <input type="submit" name="submit" value="Ir a pago" />
            <input type="submit" name="submit" value="Atras" />
        </form>
        

    </body>
</html>

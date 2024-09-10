/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DML.Carrito;
import DML.ConexionBD;
import DML.Producto;
import DML.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
public class controlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //Variables con los datos de conexion a base de datos
            String servidor = "localhost";
            String database = "pasarela";
            String usuarioBD = "pau";
            String passwordBD = "kk";

            //Creación de la conexión con la BD.
            // - Se crea un conector
            // - Se prepara el objeto de conexion con los datos de la conexion
            // - Se obtienen los datos de conexión preparados en el objeto y se aplica sobre el conector
            Connection conn = null;
            ConexionBD cnx = new ConexionBD(servidor, database, usuarioBD, passwordBD);
            conn = cnx.getConnection();

            //Creación de carrito
            Producto prod = new Producto();
            ArrayList<Producto> listado = prod.listarProductos(conn);
            String rol;

            //Creación de objeto de redireccionamiento entre archivos
            RequestDispatcher rd = null;
            String submit, usuario, apellidos, email, password;

            submit = request.getParameter("submit");
            usuario = request.getParameter("usuario");
            apellidos = request.getParameter("apellidos");
            email = request.getParameter("email");
            password = request.getParameter("password");
            rol = request.getParameter("rol");

            switch (submit) {
                //Comprobacion para acceder a la BD
                case "Login":
                    Usuario userLog = new Usuario(0, usuario, apellidos, email, password, "invitado");
                    if (userLog.comprobarUsuario(email,password, conn)) {
                        rd = getServletContext().getRequestDispatcher("/menu.jsp");
                        rol = userLog.comprobarRol(email, conn);
                        System.out.println("LOGIN ROLL: " + rol);
                        request.setAttribute("email", email);
                        request.setAttribute("rol", rol);
                        rd.forward(request, response);
                    } else {
                        System.out.println("");
                        rd = getServletContext().getRequestDispatcher("/index.jsp");
                        rd.forward(request, response);
                    }
                    
                    
                    cnx.cerrarConexion();

                    break;
                //Redireccion al menu de registro
                case "Register":
                    rd = getServletContext().getRequestDispatcher("/registro.jsp");
                    rd.forward(request, response);
                    break;
                //Redireccion al carrito con los productos añadidos
                case "Ver Carrito":
                    rd = getServletContext().getRequestDispatcher("/verCarrito.jsp");
                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    rd.forward(request, response);
                    break;
                //Redireccion a la tienda con los productos
                case "Ver Productos":
                    rd = getServletContext().getRequestDispatcher("/verProductos.jsp");
                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    request.setAttribute("listado", listado);
                    rd.forward(request, response);
                    break;
                case "Insertar Producto":
                    rd = getServletContext().getRequestDispatcher("/añadirProducto.jsp");
                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    rd.forward(request, response);
                    break;
                case "Ir a pago":
                    rd = getServletContext().getRequestDispatcher("/verCarrito.jsp");
                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    
                    ArrayList<String> carrito = (ArrayList<String>) request.getSession().getAttribute("carrito");

                    Carrito.insertarCarrito(carrito, conn, email);
                    
                    
                    rd.forward(request, response);
                    break;
                case "Insertar":

                    String nomP = request.getParameter("insertNombreP");
                    String desP = request.getParameter("insertDescripcionP");
                    String preP = request.getParameter("insertPrecioP");
                    String fotoP = request.getParameter("insertFotoP");

                    Producto nuevoProducto = new Producto(0, nomP, desP, preP, fotoP);
                    nuevoProducto.insertarProducto(nuevoProducto, conn);
                    
                    System.out.println("INSERTAR: " + nuevoProducto.getNombre() + nuevoProducto.getDescripcion() + " -> " );
                    cnx.cerrarConexion();

                    rd = getServletContext().getRequestDispatcher("/añadirProducto.jsp");
                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    rd.forward(request, response);
                    break;

                case "Agregar al carrito":
                    rd = getServletContext().getRequestDispatcher("/verProductos.jsp");
                    // Obtener el ID del producto seleccionado
                    String productoId = request.getParameter("productos");
                    System.out.println("PRODUCTOID" + productoId);
                    // Obtener la lista de productos en el carrito de la sesión
                    //ArrayList<String> carrito;
                    if (request.getSession().getAttribute("carrito") != null) {
                        System.out.println("IF");
                        carrito = (ArrayList<String>) request.getSession().getAttribute("carrito");
                    } else {
                        System.out.println("ELSE");
                        carrito = new ArrayList<>();
                    }

                    // Agregar el ID del producto al carrito
                    carrito.add(productoId);

                    System.out.println("CARRITO: " + carrito);
                    // Actualizar el carrito en la sesión

                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    request.getSession().setAttribute("carrito", carrito);
                    request.setAttribute("listado", listado);
                    System.out.println("RESPONSE " + response);
                    // Redirigir de vuelta al JSP verProductos.jsp
                    //cnx.cerrarConexion();
                    rd.forward(request, response);
                    
                    break;

                //Redireccionamiento al menu principal
                case "Atras":
                    rd = getServletContext().getRequestDispatcher("/menu.jsp");
                    request.setAttribute("email", email);
                    request.setAttribute("rol", rol);
                    rd.forward(request, response);
                    break;
                //Redireccionamiento al index
                case "Salir":
                    rd = getServletContext().getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                    break;
                //Menu de registro para dar de alta una persona
                case "Registrarme":
                    Usuario user = new Usuario(0, usuario, apellidos, email, password, "invitado");
                    user.insertarUsuario(user, conn);
                    cnx.cerrarConexion();
                    rd = getServletContext().getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                    break;

                default:
                    break;
            }

            out.println("<h1>Servlet AtributosContexto at " + request.getContextPath() + "</h1>");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

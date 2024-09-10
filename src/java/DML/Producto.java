/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private String precio;
    private String foto;

    public Producto() {
    }

    public Producto(int id) {
        this.id = id;
    }

    public Producto(int id, String nombre, String descripcion, String precio, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ArrayList<Producto> listarProductos(Connection conn) {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            String sqlStr = "SELECT * FROM productos";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            // Ejecutar la consulta
            ResultSet rset = pstmt.executeQuery();

            //Recorre todos los datos obtenidos de la consulta.
            //Genera un objeto producto y lo almacena en un array de productos
            while (rset.next()) {
                Producto p = new Producto(
                        rset.getInt("id"),
                        rset.getString("nombre"),
                        rset.getString("descripcion"),
                        rset.getString("precio"),
                        rset.getString("fotos"));
                lista.add(p);
            }

            // Cerrar los recursos (PreparedStatement ya se cerrará al cerrar el ResultSet)
            if (rset != null) {
                rset.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Devuelve un array de productos
        return lista;
    }

    public static Producto obtenerProductoPorId(int id) {
        Producto p = null;
        String servidor = "localhost";
        String database = "pasarela";
        String usuarioBD = "pau";
        String passwordBD = "kk";
        Connection conn = null;
        ConexionBD cnx = new ConexionBD(servidor, database, usuarioBD, passwordBD);
        conn = cnx.getConnection();
        try {
            String sqlStr = "SELECT * FROM productos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setInt(1, id);
            // Ejecutar la consulta
            ResultSet rset = pstmt.executeQuery();

            // Si hay un resultado, crea un objeto Producto
            if (rset.next()) {
                p = new Producto();
                p.setId(rset.getInt("id"));
                p.setNombre(rset.getString("nombre"));
                p.setDescripcion(rset.getString("descripcion"));
                p.setPrecio(rset.getString("precio"));
                p.setFoto(rset.getString("fotos"));
            }

            // Cerrar los recursos (PreparedStatement ya se cerrará al cerrar el ResultSet)
            if (rset != null) {
                rset.close();
            }
            cnx.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public boolean insertarProducto(Producto producto, Connection conn){
        try {
            // Preparación de sentencia con PreparedStatement para evitar inyección SQL
            String sqlStr = "INSERT INTO productos (id, nombre, descripcion, precio, fotos) VALUES (0, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            // Establecer los valores de los parámetros en la sentencia preparada
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, producto.getPrecio());
            pstmt.setString(4, producto.getFoto());

            // Ejecución de la sentencia preparada para insertar el usuario
            pstmt.executeUpdate();
            // Cerrar el PreparedStatement
            pstmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }
}

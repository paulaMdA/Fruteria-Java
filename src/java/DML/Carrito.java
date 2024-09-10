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
public class Carrito {
    private int id;
    private int usuario_id;
    private int producto_id;
    private int cantidad;
    private Double total;
    private String estado;
    private ArrayList<Producto> carrito;

    public Carrito() {
    }

    public Carrito(ArrayList<Producto> carrito) {
        this.carrito = carrito;
    }

    public Carrito(int id, int usuario_id, int producto_id, int cantidad, Double total, String estado, ArrayList<Producto> carrito) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.total = total;
        this.estado = estado;
        this.carrito = carrito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Producto> getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Producto> carrito) {
        this.carrito = carrito;
    }
    
    public static boolean insertarCarrito(ArrayList<String> carrito, Connection conn, String email){
        try {
            // Preparación de sentencia con PreparedStatement para evitar inyección SQL
            String sqlStr = "INSERT INTO carrito (id, usuario_id, producto_id, cantidad, total, estado) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);

            
            // Establecer los valores de los parámetros en la sentencia preparada
            pstmt.setInt(1, 0);
            pstmt.setInt(2, Usuario.idUsuario(email, conn));
            pstmt.setInt(4, 0);
            pstmt.setDouble(5, 0);
            pstmt.setString(6, "pendiente");

            for(int i = 0; i < carrito.size(); i++){
                pstmt.setInt(3, Integer.parseInt(carrito.get(i)));
                pstmt.executeUpdate();
            }

            // Cerrar el PreparedStatement
            pstmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static Carrito recogerCarrito(Connection conn, String email){
        try {
            int idUser = Usuario.idUsuario(email, conn);
            
            String sqlStr = "SELECT * FROM carrito WHERE usuario_id = " + idUser;
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            
            Carrito carro = null;
            
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                
                return null;
            } else {
                return null; // El usuario no fue encontrado
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    
    
    
    
}

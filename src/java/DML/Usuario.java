/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DML;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class Usuario {

    private int id_usr;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String rol;

    public Usuario(int id_usr, String nombre, String apellidos, String email, String password, String rol) {
        this.id_usr = id_usr;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public int getId_usr() {
        return id_usr;
    }

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Método para cifrar una contraseña usando SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Método para verificar si la contraseña introducida coincide con la contraseña almacenada
    public static boolean verifyPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        String hashedInputPassword = hashPassword(password);
        return hashedInputPassword.equals(hashedPassword);
    }

    public boolean insertarUsuario(Usuario usuario, Connection conn) {
        try {
            // Preparación de sentencia con PreparedStatement para evitar inyección SQL
            String sqlStr = "INSERT INTO usuarios (id_usr, nombre, apellidos, email, password, rol) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);

            //Cifrado de password
            String passwordHash = hashPassword(usuario.getPassword());

            // Establecer los valores de los parámetros en la sentencia preparada
            pstmt.setInt(1, usuario.getId_usr());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellidos());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, passwordHash);
            pstmt.setString(6, usuario.getRol());
            // Ejecución de la sentencia preparada para insertar el usuario
            pstmt.executeUpdate();
            // Cerrar el PreparedStatement
            pstmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean comprobarUsuario(String email, String password, Connection conn) {
        try {
            String sqlStr = "SELECT password FROM usuarios WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            pstmt.setString(1, email);
            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                String hashedPassword = rset.getString("password");
                return verifyPassword(password, hashedPassword);
            } else {
                return false; // El usuario no fue encontrado
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String comprobarRol(String email, Connection conn) {

        try {
            String sqlStr = "SELECT rol FROM usuarios WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            // Establecer los valores de los parámetros en la consulta preparada
            pstmt.setString(1, email);
            // Ejecutar la consulta
            ResultSet rset = pstmt.executeQuery();

            String respuesta = "";

            // Verificar si hay resultados
            if (rset.next()) {
                respuesta = rset.getString("rol");
            }

            // Cerrar los recursos
            if (rset != null) {
                rset.close();
            }
            pstmt.close();

            return respuesta;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public static int idUsuario(String email, Connection conn){
        try {
            String sqlStr = "SELECT id_usr FROM usuarios WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStr);
            // Establecer los valores de los parámetros en la consulta preparada
            pstmt.setString(1, email);
            // Ejecutar la consulta
            ResultSet rset = pstmt.executeQuery();

            int respuesta = 0;

            // Verificar si hay resultados
            if (rset.next()) {
                respuesta = rset.getInt("id_usr");
                System.out.println("el ID del usuario es " + respuesta);
            }

            // Cerrar los recursos
            if (rset != null) {
                rset.close();
            }
            pstmt.close();

            return respuesta;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}

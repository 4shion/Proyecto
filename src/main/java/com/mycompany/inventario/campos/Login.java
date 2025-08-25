/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.encriptacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Login extends conexion {
    
    private String usuario;
    private String contra;
    
    private static String usuarioActual = null;

    public Login(String usuario, String contra) {
        this.usuario = usuario;
        this.contra = contra;
    }
    
    public Login(){
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
    
    public static String getUsuarioActual() {
        return usuarioActual;
    }
    
    public static void cerrarSesion() {
        usuarioActual = null;
    }
    
    public boolean verificar(){
        
        String sql = "SELECT codigo FROM usuario WHERE nombre = ? and estado != false";
    
        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, this.usuario);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String ContraEncriptada = rs.getString("codigo");

                if (encriptacion.verify(this.contra, ContraEncriptada)) {

                    usuarioActual = this.usuario;
                    return true;
                    
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
        
    }
    
}

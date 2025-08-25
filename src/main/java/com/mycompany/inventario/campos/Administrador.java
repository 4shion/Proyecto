/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.encriptacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Administrador extends conexion {
    
    private String cod;
    private int id;
    private String nombre;
    private String correo;
    private String codAdmi;
    
    public Administrador() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodAdmi() {
        return codAdmi;
    }

    public void setCodAdmi(String codAdmi) {
        this.codAdmi = codAdmi;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
    
    public boolean insertar(){
        
        String codEncriptado = encriptacion.hash(this.cod);
        
        String sqlUsuario = "INSERT INTO usuario (codigoAdmin, nombre, IdUsuario, correo, codigo, estado) VALUES (?, ?, NULL, ?, ?, true)";
        String sqlPermisos = "INSERT INTO permisos (materiales, pedido, cliente, facturacion, proveedores, usuarios, Usuario_idUsuario) VALUES (?, ?, ?, ?, ?, ?, LAST_INSERT_ID())";
        
        try (Connection con = getCon();
             PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario);
             PreparedStatement stmPermisos = con.prepareStatement(sqlPermisos)) {
            
            // Insertar en la tabla usuario
            stmUsuario.setString(1, this.codAdmi);
            stmUsuario.setString(2, this.nombre);
            stmUsuario.setString(3, this.correo);
            stmUsuario.setString(4, codEncriptado);
            stmUsuario.executeUpdate();
            
            // Insertar en la tabla permisos
            stmPermisos.setBoolean(1, true);
            stmPermisos.setBoolean(2, true);
            stmPermisos.setBoolean(3, true);
            stmPermisos.setBoolean(4, true);
            stmPermisos.setBoolean(5, true);
            stmPermisos.setBoolean(6, true);
            stmPermisos.executeUpdate();
            
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }
    
}

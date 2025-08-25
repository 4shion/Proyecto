/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class historial extends conexion{
    
    Timestamp timestampActual = new Timestamp(System.currentTimeMillis());
    private String detalle;
    private Integer idUsuario;
    private String accion;

    
    usuario usu = new usuario();
    
    public historial(){}

    public historial(String accion, String detalle, Integer idUsuario) {
        this.accion = accion;
        this.detalle = detalle;
        this.idUsuario = idUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getTimestampActual() {
        return timestampActual;
    }

    public void setTimestampActual(Timestamp timestampActual) {
        this.timestampActual = timestampActual;
    }
    
    public void insert(String accion, String detalles, String NomUsuario){
        
        usu.buscarUsuario(NomUsuario);
        setIdUsuario(usu.getId());
        String sql = "INSERT INTO historial (idHistorial, accion, fecha, detalle, Usuario_idUsuario) VALUES (NULL, ?, ?, ?, ?)";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            
            stm.setString(1, accion);
            stm.setTimestamp(2, this.timestampActual);
            stm.setString(3, detalles);
            stm.setInt(4, this.idUsuario);
            stm.executeUpdate();
            System.out.println("Agregado correctamente al historial");
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido agregar al historial");
            
        }

    }
    
}

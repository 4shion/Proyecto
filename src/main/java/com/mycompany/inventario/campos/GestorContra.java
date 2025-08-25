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
public class GestorContra extends conexion {
    
    private String SelecUsuario; 
    private String ContraN;
    
    public GestorContra(){
    }
    
    public String getSelecUsuario() {
        return SelecUsuario;
    }

    public void setSelecUsuario(String SelecUsuario) {
        this.SelecUsuario = SelecUsuario;
    }

    public String getContraN() {
        return ContraN;
    }

    public void setContraN(String ContraN) {
        this.ContraN = ContraN;
    }
    
    public boolean verificarContraSeleccionada(String contraIngresada) {
        String sqlUsu = "SELECT codigo FROM usuario WHERE nombre = ? and estado != false";
        String sqlAdmi = "SELECT codigo FROM usuario WHERE codigoAdmin is NOT NULL and estado != false";
        
        try (Connection con = getCon();
             PreparedStatement pstmtUsu = con.prepareStatement(sqlUsu);
             PreparedStatement pstmtAdmi = con.prepareStatement(sqlAdmi)) {

            pstmtUsu.setString(1, this.SelecUsuario);

            ResultSet rs = pstmtUsu.executeQuery();
            if (rs.next()) {
                String contraBD = rs.getString("codigo");
                if (encriptacion.verify(contraIngresada, contraBD)) {
                    return true;
                }
            }
            
            ResultSet rsAdmi = pstmtAdmi.executeQuery();
            if (rsAdmi.next()) {
                String codAdmiBD = rsAdmi.getString("codigo");
                if (encriptacion.verify(contraIngresada, codAdmiBD)) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestorContra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean verificarCodAdmi(String contraIngresada){
        
        String sqlAdmi = "SELECT codigoAdmin FROM usuario WHERE codigoAdmin is NOT NULL and estado != false";
        
        try (Connection con = getCon();
            PreparedStatement pstmtAdmi = con.prepareStatement(sqlAdmi)) {
            
            ResultSet rsAdmi = pstmtAdmi.executeQuery();

            if (rsAdmi.next()) {
                String codAdmiBD = rsAdmi.getString("codigoAdmin");
                
                if (codAdmiBD.equals(contraIngresada)) {
                    return true;
                }
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestorContra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;        
    }
    
    public boolean Modificar(){
        
        String codEncriptado = encriptacion.hash(this.ContraN);
        
        String sql="UPDATE usuario SET codigo = ? WHERE nombre = ? and estado != false"; 
        
        try( 
               Connection con=getCon();
               PreparedStatement stm=con.prepareStatement(sql)){
               stm.setString(1,codEncriptado);
               stm.setString(2,this.SelecUsuario);

               stm.executeUpdate();
               return true;
               
           } 
        catch (SQLException ex) {
            
               Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
               return false;
               
           }            
    }
    
}

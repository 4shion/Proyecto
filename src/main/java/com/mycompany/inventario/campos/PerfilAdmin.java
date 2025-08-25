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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class PerfilAdmin extends conexion {
    
    private String nombre;
    private String codigo;
    private String codAdmi;
    private String Correo;
    
    public PerfilAdmin(){
        
    }

    public PerfilAdmin(String nombre, String codigo, String codAdmi, String Correo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.codAdmi = codAdmi;
        this.Correo = Correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodAdmi() {
        return codAdmi;
    }

    public void setCodAdmi(String codAdmi) {
        this.codAdmi = codAdmi;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }
    
    public boolean Modificar(){
        
        encriptacion encriptador = new encriptacion();

        // Encripta la contraseña antes de guardarla
        String contraseñaEncriptada = encriptador.hash(this.codigo);

        String sql = "UPDATE usuario SET codigoAdmin = ?, nombre = ?, correo = ?, codigo = ?"
                    + " WHERE codigoAdmin is NOT NULL and estado != false";

        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setString(1, this.codAdmi);
            stm.setString(2, this.nombre);
            stm.setString(3, this.Correo);
            stm.setString(4, contraseñaEncriptada); // Guarda la contraseña encriptada
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public ArrayList<PerfilAdmin> Consulta(){
        
        ArrayList<PerfilAdmin> perfilA = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE codigoAdmin is not NULL and estado != false";
        
        try(

            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                String codAdmi = rs.getString("codigoAdmin");
                String nom = rs.getString("nombre");
                String co = rs.getString("correo");
                String cod = rs.getString("codigo");
                PerfilAdmin p = new PerfilAdmin(nom, cod, codAdmi, co);
                perfilA.add(p);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return perfilA;
        
    }
    
}

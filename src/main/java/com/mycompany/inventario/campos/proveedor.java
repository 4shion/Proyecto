/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class proveedor extends conexion implements sentencias {
    
    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    historial hs = new historial();
    Login login = new Login();

    public proveedor(int id, String nombre, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public proveedor() {
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public boolean insertar() {
        
        String sql = "insert into proveedor values(null, ?, ?, ?, true)";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.nombre);
            stm.setString(2, this.correo);
            stm.setString(3, this.telefono);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }

    @Override
    public ArrayList<proveedor> consulta() {
        
        ArrayList<proveedor> proveedores = new ArrayList<>();
        String sql = "select * from proveedor where estado != false";
        
        try(

            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                int cod = rs.getInt("idProveedor");
                String nom = rs.getString("nombre");
                String co = rs.getString("correo");
                String te = rs.getString("telefono");
                proveedor c = new proveedor(cod, nom, co, te);
                proveedores.add(c);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return proveedores;
        
    }

    @Override
    public boolean modificar() {
        
        String sql = "update proveedor set nombre = ?, correo = ?, telefono = ?" +
                "where idProveedor = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.nombre);
            stm.setString(2, this.correo);
            stm.setString(3, this.telefono);
            stm.setInt(4, this.id);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }

    @Override
    public boolean eliminar() {
        
        String sql = "update proveedor set estado = false where idProveedor = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setInt(1, this.id);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        } 
        
    }
    
    public void buscarDatosProveedor(String nombreProveedor) {
        String correo;
        int telf;
        String consulta = "SELECT correo, telefono FROM proveedor WHERE nombre = ? and estado != false";

        try (PreparedStatement stmt = getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correo = rs.getString("correo");
                telf = rs.getInt("telefono");
                this.setNombre(nombreProveedor);
                this.setCorreo(correo); 
                this.setTelefono(String.valueOf(telf));
            }
        } catch (SQLException ex) {
            Logger.getLogger(proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean existeProveedor() {
        String query = "SELECT COUNT(*) FROM proveedor WHERE nombre = ? and estado != false";
        try (Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(query)) {
            
            stm.setString(1, this.nombre);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}

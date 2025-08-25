/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import static com.mycompany.inventario.clases.alertas.ShowAlert;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class cliente extends conexion implements sentencias{
    
    private int id;
    private String nombre;
    private String correo;
    private String telefono;

    public cliente(int id, String nombre, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public cliente(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    public cliente() {
        
        
        
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public boolean insertar() {
        
        
        String sql = "INSERT INTO cliente values(NULL, ?, ?, ?, true)";
        
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
    public ArrayList<cliente> consulta() {
        
        ArrayList<cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.idCliente, c.nombre, c.correo, c.telefono FROM cliente c WHERE nombre != 'Sin nombre' and estado != false";
        
        try(
            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                int cod = rs.getInt("idCliente");
                String nom = rs.getString("nombre");
                String co = rs.getString("correo");
                String te = rs.getString("telefono");
                cliente c = new cliente(cod, nom, co, te);
                clientes.add(c);
                
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return clientes;
        
    }

    @Override
    public boolean modificar() {
        
        String sql = "UPDATE cliente SET nombre = ?, correo = ?, telefono = ?"
                + " WHERE idCliente = ?";
        
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
        
        String sql = "UPDATE cliente SET estado = false WHERE idCliente = ?";
        
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
    
    public void buscarDatosCliente(String nombreCliente) {
        String correo;
        int idC;
        int telf;
        String consulta = "SELECT correo, telefono, IdCliente FROM cliente WHERE nombre = ? and estado != false";

        try (PreparedStatement stmt = getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correo = rs.getString("correo");
                telf = rs.getInt("telefono");
                idC = rs.getInt("IdCliente");
                this.setNombre(nombreCliente);
                this.setCorreo(correo); 
                this.setTelefono(String.valueOf(telf));
                this.setId(idC);
            } else {
                ShowAlert(Alert.AlertType.ERROR, "Error", "Cliente no registrado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean existeCliente() {
        String query = "SELECT COUNT(*) FROM cliente WHERE nombre = ? and estado != false";
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

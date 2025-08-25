/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
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
public class factura extends conexion {
    
    private double subTotal;
    private double Total;
    private Integer IdPedido;
    private int Idcliente;
    private int numFactura;
    
    public factura() {
    }

    public factura(double subTotal, double Total, int Idcliente, int numFactura) {
        this.subTotal = subTotal;
        this.Total = Total;
        this.Idcliente = Idcliente;
        this.numFactura = numFactura;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public int getIdcliente() {
        return Idcliente;
    }

    public void setIdcliente(int Idcliente) {
        this.Idcliente = Idcliente;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }

    public Integer getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(Integer IdPedido) {
        this.IdPedido = IdPedido;
    }
    
    public boolean insertar() {
        
        String sql = "INSERT INTO factura (numFactura, subtotal, total, Pedido_idPedido, Cliente_IdCliente) VALUES (NULL, ?, ?, ?, ?)";

        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setDouble(1, this.subTotal);
            stm.setDouble(2, this.Total);
            stm.setInt(3, this.IdPedido);  
            stm.setInt(4, this.Idcliente);  
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }    

    public void obtenerNumFac() {
        String sql = "SELECT numFactura FROM factura ORDER BY numFactura DESC LIMIT 1"; // Ejemplo de consulta

        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            if (rs.next()) {
                this.numFactura = rs.getInt("numFactura");
            }

        } catch (SQLException ex) {
            Logger.getLogger(factura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}



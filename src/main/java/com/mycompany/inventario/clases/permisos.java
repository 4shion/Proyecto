/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author User
 */
public class permisos extends conexion{
    
    public boolean tienePermiso(String usuarioNombre, String tipoPermiso) {
        String sql = "SELECT " + tipoPermiso + " FROM permisos p " +
                     "JOIN usuario u ON p.Usuario_idUsuario = u.idUsuario " +
                     "WHERE u.nombre = ?";


        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, usuarioNombre);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int permisoCount = rs.getInt(1);
                return permisoCount > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean Materiales(String usuarioNombre) {
        return tienePermiso(usuarioNombre, "materiales");
    }

    public boolean Pedidos(String usuarioNombre) {
        return tienePermiso(usuarioNombre, "pedido");
    }

    public boolean Clientes(String usuarioNombre) {
        return tienePermiso(usuarioNombre, "cliente");
    }

    public boolean Facturacion(String usuarioNombre) {
        return tienePermiso(usuarioNombre, "facturacion");
    }

    public boolean Proveedores(String usuarioNombre) {
        return tienePermiso(usuarioNombre, "proveedores");
    }

    public boolean Usuarios(String usuarioNombre) {
        return tienePermiso(usuarioNombre, "usuarios");
    }
    
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.encriptacion;
import com.mycompany.inventario.clases.sentencias;
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
 * @author Acer
 */
public class usuario extends conexion implements sentencias {
    
    private String nombre;
    private String codigo;
    private String Correo;
    private String codAdmi;
    private int id;
    private boolean pmateriales;
    private boolean ppedido;
    private boolean pproveedor;
    private boolean pcliente;
    private boolean pfactura;
    private boolean pusuarios;

    public usuario(String nombre, String codigo, String Correo, String codAdmi, int id, boolean pmateriales, boolean ppedido, boolean pproveedor, boolean pcliente, boolean pfactura, boolean pusuarios) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.Correo = Correo;
        this.codAdmi = codAdmi;
        this.id = id;
        this.pmateriales = pmateriales;
        this.ppedido = ppedido;
        this.pproveedor = pproveedor;
        this.pcliente = pcliente;
        this.pfactura = pfactura;
        this.pusuarios = pusuarios;
    }
    
    public usuario(){    
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

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public boolean isPmateriales() {
        return pmateriales;
    }

    public void setPmateriales(boolean pmateriales) {
        this.pmateriales = pmateriales;
    }

    public boolean isPpedido() {
        return ppedido;
    }

    public void setPpedido(boolean ppedido) {
        this.ppedido = ppedido;
    }

    public boolean isPproveedor() {
        return pproveedor;
    }

    public void setPproveedor(boolean pproveedor) {
        this.pproveedor = pproveedor;
    }

    public boolean isPcliente() {
        return pcliente;
    }

    public void setPcliente(boolean pcliente) {
        this.pcliente = pcliente;
    }

    public boolean isPfactura() {
        return pfactura;
    }

    public void setPfactura(boolean pfactura) {
        this.pfactura = pfactura;
    }

    public boolean isPusuarios() {
        return pusuarios;
    }

    public void setPusuarios(boolean pusuarios) {
        this.pusuarios = pusuarios;
    }

    public String getCodAdmi() {
        return codAdmi;
    }

    public void setCodAdmi(String codAdmi) {
        this.codAdmi = codAdmi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    @Override
    public boolean insertar() {
        
        String codEncriptado = encriptacion.hash(this.codigo);
        
        String sqlUsuario = "INSERT INTO usuario (codigoAdmin, nombre, IdUsuario, correo, codigo, estado) VALUES (?, ?, NULL, ?, ?, true)";
        String sqlPermisos = "INSERT INTO permisos (materiales, pedido, cliente, facturacion, proveedores, usuarios, Usuario_idUsuario) VALUES (?, ?, ?, ?, ?, ?, LAST_INSERT_ID())";
        
        try (Connection con = getCon();
             PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario);
             PreparedStatement stmPermisos = con.prepareStatement(sqlPermisos)) {
            
            // Insertar en la tabla usuario
            stmUsuario.setString(1, this.codAdmi);
            stmUsuario.setString(2, this.nombre);
            stmUsuario.setString(3, this.Correo);
            stmUsuario.setString(4, codEncriptado);
            stmUsuario.executeUpdate();
            
            // Insertar en la tabla permisos
            stmPermisos.setBoolean(1, pmateriales);
            stmPermisos.setBoolean(2, ppedido);
            stmPermisos.setBoolean(3, pcliente);
            stmPermisos.setBoolean(4, pfactura);
            stmPermisos.setBoolean(5, pproveedor);
            stmPermisos.setBoolean(6, pusuarios);
            stmPermisos.executeUpdate();
            
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }    
    }

    @Override
    public ArrayList<usuario> consulta() {
        
        ArrayList<usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nombre, u.correo, u.codigo, u.codigoAdmin, " +
                 "p.materiales, p.pedido, p.cliente, p.facturacion, p.proveedores, p.usuarios " +
                 "FROM usuario u " +
                 "JOIN permisos p ON u.idUsuario = p.Usuario_idUsuario " +
                 "WHERE u.codigoAdmin IS NULL and estado != false";


        try (Connection con = getCon();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                String codAdmi = rs.getString("codigoAdmin");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String codigo = rs.getString("codigo");
                int id = rs.getInt("idUsuario");
                boolean pmateriales = rs.getBoolean("materiales");
                boolean ppedido = rs.getBoolean("pedido");
                boolean pproveedor = rs.getBoolean("proveedores");
                boolean pcliente = rs.getBoolean("cliente");
                boolean pfactura = rs.getBoolean("facturacion");
                boolean pusuarios = rs.getBoolean("usuarios");

                usuario u = new usuario(nombre, codigo, correo, codAdmi, id, pmateriales, ppedido, pproveedor, pcliente, pfactura, pusuarios);
                usuarios.add(u);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuarios;
        
    }

    @Override
    public boolean modificar() {
        
        String codEncriptado = encriptacion.hash(this.codigo);
    
        String sqlUsuario = "UPDATE usuario SET nombre = ?, correo = ?, codigo = ? WHERE idUsuario = ?";
        String sqlPermisos = "UPDATE permisos SET materiales = ?, pedido = ?, cliente = ?, facturacion = ?, proveedores = ?, usuarios = ? WHERE Usuario_idUsuario = ?";

        try (Connection con = getCon();
             PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario);
             PreparedStatement stmPermisos = con.prepareStatement(sqlPermisos)) {

            // Actualizar en la tabla usuario
            stmUsuario.setString(1, this.nombre);
            stmUsuario.setString(2, this.Correo);
            stmUsuario.setString(3, codEncriptado);
            stmUsuario.setInt(4, this.id);
            stmUsuario.executeUpdate();

            // Actualizar en la tabla permisos
            stmPermisos.setBoolean(1, pmateriales);
            stmPermisos.setBoolean(2, ppedido);
            stmPermisos.setBoolean(3, pcliente);
            stmPermisos.setBoolean(4, pfactura);
            stmPermisos.setBoolean(5, pproveedor);
            stmPermisos.setBoolean(6, pusuarios);
            stmPermisos.setInt(7, this.id); // Asegúrate de usar el id correcto aquí
            stmPermisos.executeUpdate();

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

    @Override
    public boolean eliminar() {
        
        String sqlUsuario = "update usuario set estado = false where idUsuario = ?";
        
        try(Connection con = getCon();
            PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario))
        {
            
            stmUsuario.setInt(1, this.id);
            stmUsuario.execute();
            
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }         
    }
    
    public boolean existeUsuario(String nombre) {
        
        String query = "SELECT COUNT(*) FROM usuario WHERE nombre = ? and estado != false";
        try (Connection con=getCon();
             PreparedStatement stmt=con.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void buscarUsuario(String nombreUsuario) {
        String consulta = "SELECT IdUsuario FROM usuario WHERE nombre = ? and estado != false";

        try (PreparedStatement stmt = getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int IdUsua = rs.getInt("IdUsuario");
                this.setId(IdUsua);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean verificar(String nom){
        
        String query = "SELECT COUNT(*) FROM usuario WHERE codigoAdmin is not NULL and nombre = ? and estado != false";
        try (Connection con=getCon();
             PreparedStatement stmt=con.prepareStatement(query)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si hay al menos un material con ese nombre
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return false;
        
    }
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author User
 */
public class materia extends conexion implements sentencias {

    private int id;
    private String nombre;
    private double precio;
    public double cantidad;
    public double cantidad_min;
    private int idProveedor;
    private String nombreproveedor;
    private String unidadMedida;

    public materia() {
    }

    public materia(int id, String nombre, double precio, double cantidad, double cantidad_min, String nombreproveedor, int idProveedor, String unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.cantidad_min = cantidad_min;
        this.nombreproveedor = nombreproveedor;
        this.idProveedor = idProveedor;
        this.unidadMedida = unidadMedida;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidad_min() {
        return cantidad_min;
    }

    public void setCantidad_min(double cantidadMin) {
        this.cantidad_min = cantidadMin;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
    public boolean necesitaRestock() {
        return cantidad_min <= cantidad;
    }
    
    @Override
    public boolean insertar() {
        String sql="INSERT INTO materiaprima(idMaterial,nombre,precio,cantidad,cantidad_min,Proveedor_idProveedor,UnidadMedida, estado) VALUES (?,?,?,?,?,?,?,true)";
                try( 
                    Connection con=getCon();
                    PreparedStatement stm=con.prepareStatement(sql)){
                    stm.setInt(1,this.id);
                    stm.setString(2,this.nombre);
                    stm.setDouble(3,this.precio);
                    stm.setDouble(4,this.cantidad);
                    stm.setDouble(5,this.cantidad_min);
                    stm.setInt(6,this.idProveedor);
                    stm.setString(7,this.unidadMedida);
                    stm.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }    
    }

    @Override
    public ArrayList<materia> consulta() {
        
        ArrayList<materia> materia=new ArrayList<>();

        String sql;
        sql = "SELECT `m`.*, `p`.`nombre` AS nombrep " + 
              "FROM `materiaprima` AS `m` " + 
              "LEFT JOIN `proveedor` AS `p` ON `m`.`Proveedor_idProveedor` = `p`.`idProveedor` " +
              "WHERE `m`.`estado` != false";
        
        try( 
                Connection con=getCon();
                Statement stm=con.createStatement();
                ResultSet rs=stm.executeQuery(sql)){
                while(rs.next())
            {
                int cod=rs.getInt("idMaterial");
                String nom=rs.getString("nombre");
                double pre=rs.getDouble("precio");
                double cant=rs.getDouble("cantidad");
                double can_min=rs.getDouble("cantidad_min");
                int Idpro=rs.getInt("Proveedor_idProveedor");
                String nomPro=rs.getString("nombrep");
                String uni=rs.getString("UnidadMedida");
                materia m=new materia(cod,nom,pre,cant,can_min,nomPro,Idpro,uni);
                materia.add(m);                     
            }
            } catch (SQLException ex) {
                
             Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
             
         }     
          return materia;
         
    }

    @Override
    public boolean modificar() {
        
        String sql="UPDATE materiaprima SET nombre=?,precio=?,cantidad=?,cantidad_min=?,Proveedor_idProveedor=?,UnidadMedida=? WHERE idMaterial=?"; 
        
        try( 
               Connection con=getCon();
               PreparedStatement stm=con.prepareStatement(sql)){
               stm.setString(1,this.nombre);
               stm.setDouble(2,this.precio);
               stm.setDouble(3,this.cantidad);
               stm.setDouble(4,this.cantidad_min);
               stm.setInt(5,this.idProveedor);
               stm.setString(6,this.unidadMedida);
               stm.setInt(7,this.id);
               stm.executeUpdate();
               return true;
               
           } 
        catch (SQLException ex) {
            
               Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
               return false;
               
           }    
        
    }

    @Override
    public boolean eliminar() {
        
        String sql = "UPDATE materiaprima SET estado = false WHERE idMateria = ?";
        
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
    
    public String getCantidadConUnidad() {
        return cantidad + " " + unidadMedida;
    }

    public String getCant_MinConUnidad() {
        return cantidad_min + " " + unidadMedida;
    }
    
    public String getPrecioConEuro(){
        return precio + " " + "€";
    }
    
    public boolean existeMaterial(String nombre) {
        
        String query = "SELECT COUNT(*) FROM materiaprima WHERE nombre = ? and estado != false";
        try (Connection con=getCon();
             PreparedStatement stmt=con.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si hay al menos un material con ese nombre
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return false; // Si ocurre un error o no se encuentra, retornar false
    }
    
    public void buscarCantMaterial(String nombreMaterial) {
        String consulta = "SELECT cantidad, cantidad_min FROM materiaprima WHERE nombre = ? and estado != false";

        try (PreparedStatement stmt = getCon().prepareStatement(consulta)) {
            stmt.setString(1, nombreMaterial);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double Cant = rs.getDouble("cantidad");
                double CantM = rs.getDouble("cantidad_min");
                this.setCantidad(Cant);
                this.setCantidad_min(CantM);
            }
        } catch (SQLException ex) {
            Logger.getLogger(materia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<materia> obtenerListaMateriales() {
        List<materia> listaMateriales = new ArrayList<>();

        String query = "SELECT nombre, cantidad, cantidad_min, UnidadMedida FROM materiaprima WHERE estado != false";

        try (Connection conn = getCon(); 
             PreparedStatement pstmt = conn.prepareStatement(query); 
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Obtener los datos de la consulta
                String nombreM = rs.getString("nombre");
                double cantidad = rs.getDouble("cantidad");
                double cantidad_min = rs.getDouble("cantidad_min");
                String Unidad = rs.getString("UnidadMedida");

                // Crear el objeto 'materia' y agregarlo a la lista
                materia material = new materia();
                material.setNombre(nombreM);
                material.setCantidad(cantidad);
                material.setCantidad_min(cantidad_min);
                material.setUnidadMedida(Unidad);

                listaMateriales.add(material);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores en caso de fallo en la conexión o consulta
        }

        return listaMateriales;
    }
    
    public boolean verificarStockBajo() {
        String sql = "SELECT COUNT(*) FROM materiaprima WHERE cantidad < cantidad_min and estado != false";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si hay al menos un material con stock bajo, retornar true
            }
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return false; // Si no hay stock bajo o hay un error, retornar false
    }
    
}

package com.mycompany.inventario.campos;

import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class pedido extends conexion {

    private Integer idPedido;
    private String servicio;
    private Date fechaActual = new Date(System.currentTimeMillis());
    private Double totalPedido;
    private Integer idCliente;
    private Double Cant;
    private String nombreC;
    private String nombreM;
    private Double StockRestante;
    private Double precio;
    private String unidad;
    private int[] listaMaterialesId;
    private double[] listaCant;
    private String[] listaMaterialesN;
    private Integer numFactura;

    public pedido() {}
    alertas alert = new alertas();

    public pedido(Integer idPedido, String servicio, Double totalPedido, Integer idCliente, Double Cant, String nombreC, String nombreM, Double StockRestante, Double precio, String unidad) {
        this.idPedido = idPedido;
        this.servicio = servicio;
        this.totalPedido = totalPedido;
        this.idCliente = idCliente;
        this.Cant = Cant;
        this.nombreC = nombreC;
        this.nombreM = nombreM;
        this.StockRestante = StockRestante;
        this.precio = precio;
        this.unidad = unidad;
    }

    public pedido(Double Cant, String nombreM, Double StockRestante, Double precio, String unidad) {
        this.Cant = Cant;
        this.nombreM = nombreM;
        this.StockRestante = StockRestante;
        this.precio = precio;
        this.unidad = unidad;
    }
    
    

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Double getCant() {
        return Cant;
    }

    public void setCant(Double Cant) {
        this.Cant = Cant;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getNombreM() {
        return nombreM;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public Double getStockRestante() {
        return StockRestante;
    }

    public void setStockRestante(Double StockRestante) {
        this.StockRestante = StockRestante;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int[] getListaMaterialesId() {
        return listaMaterialesId;
    }

    public void setListaMaterialesId(int[] listaMaterialesId) {
        this.listaMaterialesId = listaMaterialesId;
    }

    public double[] getListaCant() {
        return listaCant;
    }

    public void setListaCant(double[] listaCant) {
        this.listaCant = listaCant;
    }

    public String[] getListaMaterialesN() {
        return listaMaterialesN;
    }

    public void setListaMaterialesN(String[] listaMaterialesN) {
        this.listaMaterialesN = listaMaterialesN;
    }

    public Integer getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(Integer numFactura) {
        this.numFactura = numFactura;
    }
    
    
    
    public ArrayList<pedido> consulta() {
        ArrayList<pedido> pedidos = new ArrayList<>();
        String sql = "SELECT pedido.*, detallepedido.*, cliente.nombre AS nombreC, materiaprima.nombre AS nombreM, detallepedido.cantidad, materiaprima.precio, materiaprima.UnidadMedida AS uni "
                   + "FROM pedido "
                   + "LEFT JOIN detallepedido ON detallepedido.Pedido_idPedido = pedido.idPedido "
                   + "LEFT JOIN cliente ON pedido.Cliente_idCliente = cliente.idCliente "
                   + "LEFT JOIN materiaprima ON detallepedido.materiaPrima_idMaterial = materiaprima.idMaterial";

        try (Connection con = getCon();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                pedidos.add(new pedido(
                    rs.getInt("idPedido"),
                    rs.getString("servicio"),
                    rs.getDouble("totalPedido"),
                    rs.getInt("Cliente_idCliente"),
                    rs.getDouble("cantidad"),
                    rs.getString("nombreC"),
                    rs.getString("nombreM"),
                    rs.getDouble("cantTotal"),
                    rs.getDouble("precio"),
                    rs.getString("uni")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pedidos;
    }

    public String getCantidadConUnidad() {
        return Cant + " " + unidad;
    }

    public String getStockRestanteConUnidad() {
        return StockRestante + " " + unidad;
    }

    public boolean insertar() {
        obtenerIdClientePorNombre(nombreC);
        setListaMaterialesId(obtenerIdMaterial(listaMaterialesN));
        String sqlPedido = "INSERT INTO pedido (idPedido, servicio, fecha, totalPedido, Cliente_idCliente) VALUES (null, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detallepedido (Pedido_idPedido, materiaPrima_idMaterial, cantidad) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE materiaprima SET cantidad = cantidad - ? WHERE idMaterial = ?";

        try (Connection con = getCon()) {
            con.setAutoCommit(false);

            try (PreparedStatement stmPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmDetalle = con.prepareStatement(sqlDetalle);
                 PreparedStatement stmUpdateStock = con.prepareStatement(sqlUpdateStock)) {

                stmPedido.setString(1, this.servicio);
                stmPedido.setDate(2, this.fechaActual);
                stmPedido.setDouble(3, this.totalPedido);
                stmPedido.setInt(4, this.idCliente);
                stmPedido.executeUpdate();

                ResultSet generatedKeys = stmPedido.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idPedidoGenerado = generatedKeys.getInt(1);

                    for (int i = 0; i < listaMaterialesId.length; i++) {
                        stmDetalle.setInt(1, idPedidoGenerado);
                        stmDetalle.setInt(2, listaMaterialesId[i]);
                        stmDetalle.setDouble(3, listaCant[i]);
                        stmDetalle.executeUpdate();

                        stmUpdateStock.setDouble(1, listaCant[i]);
                        stmUpdateStock.setInt(2, listaMaterialesId[i]);
                        stmUpdateStock.executeUpdate();
                    }
                } else {
                    throw new SQLException("No se pudo obtener el ID del pedido insertado.");
                }

                con.commit();
                return true;
            } catch (SQLException ex) {
                con.rollback();
                Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void obtenerIdClientePorNombre(String nombreCliente) {
        String sql = "SELECT idCliente FROM cliente WHERE nombre = ?";

        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setString(1, nombreCliente);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                this.idCliente = rs.getInt("idCliente");
            } else {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Cliente no registrado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int[] obtenerIdMaterial(String[] listaM) {
        String sql = "SELECT idMaterial FROM materiaprima WHERE nombre = ?";
        int[] listaMaterialesIds = new int[listaM.length];

        try (Connection con = getCon();
             PreparedStatement stm = con.prepareStatement(sql)) {

            for (int i = 0; i < listaM.length; i++) {
                stm.setString(1, listaM[i]);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        listaMaterialesIds[i] = rs.getInt("idMaterial");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaMaterialesIds;
    }

    public double obtenerPrecioMaterial(String nombreMaterial) {
        return (double) obtenerValorMaterial("precio", nombreMaterial);
    }

    public double obtenerStockActual(String nombreMaterial) {
        return (double) obtenerValorMaterial("cantidad", nombreMaterial);
    }

    public String obtenerUnidadMedida(String nombreMaterial) {
        return obtenerValorMaterial("UnidadMedida", nombreMaterial).toString();
    }

    private Object obtenerValorMaterial(String columna, String nombreMaterial) {
        String query = "SELECT " + columna + " FROM materiaprima WHERE nombre = ?";
        try (Connection con = getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, nombreMaterial);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject(columna);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, "Error al obtener " + columna + " del material", e);
        }
        return null;
    }

    public void searchId() {
        String sql = "SELECT MAX(idPedido) AS idPedido FROM pedido";
        try (Connection con = getCon();
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {

            if (rs.next()) {
                this.idPedido = rs.getInt("idPedido");
            }
        } catch (SQLException ex) {
            Logger.getLogger(pedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

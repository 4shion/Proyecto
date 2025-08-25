package com.mycompany.inventario.clases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class reportes extends conexion {

    public reportes() {
    }
    
    public JasperPrint generarReporte(String ubicacion, String titulo) {
        JasperPrint jasperPrint = null;

        try {
            // Ruta al archivo .jasper
            String reportPath = getClass().getResource(ubicacion).getPath();

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RUTA_IMAGEN", getClass().getResource("/reportes/frameexperts/logo_e_corner.png").getPath());

            // Agrega parámetros según sea necesario

            // Llenar el informe
            jasperPrint = JasperFillManager.fillReport(reportPath, parameters, getCon());

            // Mostrar el informe en una nueva ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jasperPrint; // Devolver el objeto JasperPrint
    }

    public JasperPrint generarFactura(String ubicacion, String titulo, int Nrofactura) {
        JasperPrint jasperPrint = null;
        String reportPath = getClass().getResource(ubicacion).getPath();

        try (Connection connection = getCon()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("factNumero", Nrofactura);
            parameters.put("RUTA_IMAGEN", getClass().getResource("/reportes/frameexperts/logo_e_corner.png").getPath());

            // Llenar el informe
            jasperPrint = JasperFillManager.fillReport(reportPath, parameters, connection);

            // Verificar que el informe se llenó correctamente
            if (jasperPrint == null) {
                System.err.println("El informe no se llenó correctamente.");
                return null;
            }

            // Mostrar el informe
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);

        } catch (JRException | SQLException ex) {
            Logger.getLogger(reportes.class.getName()).log(Level.SEVERE, "Error al generar el informe: ", ex);
        }

        return jasperPrint;
    }
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventario.clases;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author User
 */
public class ruta {
    
    public ruta(){};
    
    public static String obtenerRutaDescargas() {
        String home = System.getProperty("user.home"); // Obtiene la ruta del directorio del usuario
        Path descargasPath = Paths.get(home, "Downloads"); // Crea la ruta de la carpeta Descargas
        return descargasPath.toString();
    }
    
}

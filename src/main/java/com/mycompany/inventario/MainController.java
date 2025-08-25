/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.permisos;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainController extends conexion implements Initializable {

    @FXML
    private Button btnSesion;

    private boolean sesionIniciada = false;

    alertas alert = new alertas();
    Login login = new Login();
    permisos p = new permisos();
    materia m = new materia();
    historial h = new historial();

    private Stage ventanaEmergente = null; 
    @FXML
    private Button btnMateriales;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnPedidos;
    @FXML
    private Button btnProveedores;
    @FXML
    private Button btnUsuarios;
    @FXML
    private StackPane materialesStackPane;
    
    private boolean pPro = false;
    private boolean pUsu = false;
    private boolean pMate = false;
    private boolean pCli = false;
    private boolean pPe = false;
    
    @FXML
    private Pane configuracion;
    
    @FXML
    private ImageView engranaje;
    
    Label burbuja = crearBurbuja("!", "white");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verificarUsuario();
        
        materialesStackPane.getChildren().add(1, burbuja);
        actualizarMain(burbuja);
    }

    public MainController() {
    }
    
    public Label crearBurbuja(String mensaje, String color) {
        Label burbuja = new Label(mensaje);
        burbuja.setStyle("-fx-background-color: " + color + "; -fx-text-fill: #AD1316; -fx-padding: 2px 3px; -fx-background-radius: 20; -fx-font-size: 1;");

        burbuja.setTranslateX(44);  // horizontal
        burbuja.setTranslateY(0);   // vertical
        burbuja.setVisible(false);    
        return burbuja;
    }
    
    public void mostrarBurbuja(Label burbuja, double cantidad, double cantidad_min) {
        burbuja.setVisible(true);
        System.out.println("burbuja mostrada en main con exito");
    }
    
    public Tooltip TextButton(String s){
        
        Tooltip t = new Tooltip(s);
        return t;
        
    }
    
    public void verificacionPermisos(){
        
        String usuarioActual = login.getUsuarioActual();

        // Verifica los permisos del usuario actual
        pPro = p.Proveedores(usuarioActual);
        pUsu = p.Usuarios(usuarioActual);
        pMate = p.Materiales(usuarioActual);
        pCli = p.Clientes(usuarioActual);
        pPe = p.Pedidos(usuarioActual);
        
        String USi = "Este usuario tiene permiso para modificar esta vista";
        String UNo = "Este usuario no tiene permiso para modificar esta vista";
        
        if(pPro){
            
            btnProveedores.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnProveedores.setTooltip(TextButton(UNo));
            
        }
        
        if(pUsu){
            
            btnUsuarios.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnUsuarios.setTooltip(TextButton(UNo));
            
        }
        
        if(pMate){
            
            btnMateriales.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnMateriales.setTooltip(TextButton(UNo));
            
        }
        
        if(pCli){
            
            btnClientes.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnClientes.setTooltip(TextButton(UNo));
            
        }
        
        if(pPe){
            
            btnPedidos.setTooltip(TextButton(USi));
            
        }
        else{
            
            btnPedidos.setTooltip(TextButton(UNo));
            
        }
        
    }
    
    private void configurarTooltips() {
        // Configurar Tooltips iniciales
        btnMateriales.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnClientes.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnPedidos.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnProveedores.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
        btnUsuarios.setTooltip(TextButton("Verifica si el usuario tiene permisos"));
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("materia");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("usuario");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("cliente");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("pedido");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void swicthToProveedor(ActionEvent event) {
        if (sesionIniciada) {
            try {
                App.setRoot("proveedor");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
    }

    @FXML
    private void Sesion(ActionEvent event) {
        if (btnSesion.getText().equals("Registrarse")) {
            abrirformularios("loginAdmi.fxml", "Registro de Administrador");
        } else if (btnSesion.getText().equals("Iniciar Sesión")) {
            abrirformularios("login.fxml", "Iniciar Sesión");
        } else if (btnSesion.getText().equals("Cerrar Sesión")) {
            cerrarSesion();
        }
    }

    private void verificarUsuario() {
        String usuarioActual = Login.getUsuarioActual();

        if (usuarioActual != null) {
            btnSesion.setText("Cerrar Sesión");
            sesionIniciada = true;
            verificacionPermisos();
        } else {
            String sql = "SELECT COUNT(*) FROM usuario";

            try (Connection con = getCon();
                 PreparedStatement pstmt = con.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    int userCount = rs.getInt(1);

                    if (userCount > 0) {
                        btnSesion.setText("Iniciar Sesión");
                    } else {
                        btnSesion.setText("Registrarse");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (usuarioActual == null){
            
            configurarTooltips();
            
        }
         
    }

    public void abrirformularios(String fxml, String titulo) {
        try {
            // Cierra la ventana emergente actual si existe
            if (ventanaEmergente != null) {
                ventanaEmergente.close();
                ventanaEmergente = null;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            if (fxml.equals("loginAdmi.fxml")) {
                LoginAdmiController controller = loader.getController();
                controller.setMainController(this);
            } else if (fxml.equals("login.fxml")) {
                LoginController controller = loader.getController();
                controller.setMainController(this);
            }

            ventanaEmergente = new Stage();
            ventanaEmergente.setTitle(titulo);
            ventanaEmergente.setScene(new Scene(root));
            ventanaEmergente.setX(350);
            ventanaEmergente.setY(100);
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            ventanaEmergente.getIcons().add(icon);

            ventanaEmergente.setOnCloseRequest(event -> ventanaEmergente = null);

            ventanaEmergente.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void actualizarBotonSesion() {
        verificarUsuario();
    }

    public void iniciarSesion() {
        sesionIniciada = true;
        btnSesion.setText("Cerrar Sesión");
        verificacionPermisos();
        App.cargarControllerMateria();
        App.cargarControllerGestor();
        App.cargarControllerPedido();
    }

    public void cerrarSesion() {
        sesionIniciada = false;
        PswdAdminController.intentosFallidos = 0;
        btnSesion.setText("Iniciar Sesión");
        alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Sesión cerrada correctamente");
        h.insert("Sesión cerrada","El usuario " + login.getUsuarioActual() + " ha cerrado sesión",login.getUsuarioActual());
        login.cerrarSesion();
        configurarTooltips();
        App.getLoadedViews().clear();
        
    }
    
    @FXML
    private void Config(ActionEvent event) {

        if (sesionIniciada) {
            
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), configuracion);
            slideIn.setFromX(800); 
            slideIn.setToX(0);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), configuracion);
            slideOut.setFromX(0);
            slideOut.setToX(800);

            RotateTransition rotateTransition = new RotateTransition(Duration.millis(350), engranaje);

            if (configuracion.isVisible()) {

                slideOut.setOnFinished(event1 -> configuracion.setVisible(false));
                slideOut.play();

                rotateTransition.setByAngle(60); 
                rotateTransition.setCycleCount(1); 
                rotateTransition.setAutoReverse(false); 

                rotateTransition.playFromStart();

            } else {

                configuracion.setVisible(true);
                slideIn.play();
                rotateTransition.setByAngle(-60); 
                rotateTransition.setCycleCount(1); 
                rotateTransition.setAutoReverse(false); 

                rotateTransition.playFromStart();

            } 
            
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Acceso Denegado", "Debe iniciar sesión para acceder a esta sección.");
        }
        
    }
    
    private void bajarPDF() {
        
        String filePath = getClass().getResource("/ayuda/manualFrameExperts.hnd").getPath();
        File file = new File(filePath);
          if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
          } else {
              System.out.println("El archivo CHM no existe.");
          }
    }
    
    @FXML
    private void abrirGestorContra() {
    
        abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    @FXML
    private void abrirPerfilAdmin() {
    
        abrirformularios("pswdAdmin.fxml", "Ingrese su contraseña de Administrador");
    
    }
    
    public void mostrarAlertaStockBajo() {
        // Obtener la lista de materiales desde la base de datos
        List<materia> listaMateriales = m.obtenerListaMateriales();

        // Filtrar los materiales con cantidad menor a la cantidad mínima
        List<materia> materialesStockBajo = listaMateriales.stream()
                .filter(material -> material.getCantidad() < material.getCantidad_min())
                .collect(Collectors.toList());

        // Si hay materiales con stock bajo, mostrar alerta
        if (!materialesStockBajo.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Los siguientes materiales tienen stock bajo:\n");

            for (materia material : materialesStockBajo) {
                mensaje.append("Nombre: ").append(material.getNombre())
                        .append(", Cantidad: ").append(material.getCantidad()).append(" ").append(material.getUnidadMedida())  // Concatenar cantidad con su unidad
                        .append(", Cantidad mínima: ").append(material.getCantidad_min()).append(" ").append(material.getUnidadMedida()) // Concatenar cantidad mínima con su unidad
                        .append("\n");
            }
            // mostrar la alerta
            Alert alertaStockBajo = new Alert(Alert.AlertType.WARNING);
            alertaStockBajo.setHeaderText("Stock bajo");
            alertaStockBajo.setContentText(mensaje.toString());
            Stage stage = (Stage) alertaStockBajo.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
            alertaStockBajo.showAndWait();
        }
    }

    
    public void actualizarMain(Label burbuja) {
    
        MateriaController materiaC = (MateriaController) App.getController("materia");

        if (materiaC!= null) {
            materiaC.verificarStockBajo(burbuja);
        } else {
            System.out.println("Error: No se encontro el controlador de Materia.");
        }
        
    }

    @FXML
    public void manualUsuario(ActionEvent event) {
    
        String filePath = getClass().getResource("/ayuda/manualFrameExperts.chm").getPath();
        File file = new File(filePath);
          if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
                }
           } else {
               System.out.println("El archivo CHM no existe.");
           }
        }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.permisos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Acer
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContra;
    @FXML
    private Button btnIngresar;

    Login login = new Login();
    alertas alert = new alertas();
    historial h = new historial();
    permisos p = new permisos();
    
    private MainController mainController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
    public void setMainController(MainController mainController) {
        
        this.mainController = mainController;
        
    }

    @FXML
    private void Ingresar(ActionEvent event) {
        
        if (txtUsuario.getText().isEmpty() || txtContra.getText().isEmpty()) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios");
            return;
        }
        
        login.setUsuario(txtUsuario.getText());
        login.setContra(txtContra.getText());
        
        if(login.verificar()){
                
            Alert alertSesionIniciada = new Alert(Alert.AlertType.CONFIRMATION);
            alertSesionIniciada.setTitle("Aviso");
            alertSesionIniciada.setHeaderText(null);
            alertSesionIniciada.setContentText("Sesión iniciada correctamente");
            Stage stage1 = (Stage) alertSesionIniciada.getDialogPane().getScene().getWindow();
            stage1.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
            alertSesionIniciada.showAndWait();
            
            
            mainController.iniciarSesion(); 
            
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            // Cerrar la ventana
            stage.close();
            if(p.Materiales(login.getUsuarioActual())){
                mainController.mostrarAlertaStockBajo();
            }
                
            h.insert("Inicio de sesión", "El usuario " + login.getUsuarioActual() + " ha iniciado sesión", login.getUsuarioActual());
            
        }
        else {
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "Error. Usuario y/o Contraseña incorrectos");
            txtContra.clear();
            
        }
        
    }
    
}

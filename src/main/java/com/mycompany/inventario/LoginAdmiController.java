/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Administrador;
import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.clases.alertas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginAdmiController implements Initializable {


    @FXML
    private TextField codAdmin;
    @FXML
    private TextField nombreAdmin;
    @FXML
    private TextField idAdmin;
    @FXML
    private TextField correoAdmin;
    
    Administrador admi = new Administrador();
    alertas alert = new alertas();
    historial h = new historial();
    Login login = new Login();
    
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    
    private MainController mainController;
    @FXML
    private PasswordField pswd;
    @FXML
    private PasswordField pswdVerificacion;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void switchToMain(ActionEvent event) {
        
        try {
            App.setRoot("main");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void Aceptar(ActionEvent event) {
        
        
        if (nombreAdmin.getText().isEmpty() || correoAdmin.getText().isEmpty() || codAdmin.getText().isEmpty() || pswd.getText().isEmpty() || pswdVerificacion.getText().isEmpty()) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios");
            return;
        }
        if (nombreAdmin.getText().matches("\\d+")) { // Comprueba si el nombre solo contiene dígitos
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El nombre no puede contener solo números");
            return;
        }
        if (!correoAdmin.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El formato del correo es incorrecto");
            return;
        }
        if (!codAdmin.getText().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El codigo debe tener un mínimo de 6 caracteres y debe contener letras y números");
            return;
        }
        if (!pswd.getText().matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La contraseña debe tener un mínimo de 6 caracteres, contener letras, números, al menos una mayúscula y un carácter especial.");
            return;
        }

        admi.setNombre(nombreAdmin.getText());
        admi.setCorreo(correoAdmin.getText());
        admi.setCodAdmi(codAdmin.getText());
        
        if(pswd.getText().equals(pswdVerificacion.getText())) {
            
            admi.setCod(pswdVerificacion.getText());
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden");
            return;
            
        }
        
        if(admi.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Administrador agregado correctamente");
                mainController.actualizarBotonSesion();
                h.insert("Creación de administrador","Se ha creado correctamente el administrador " + admi.getNombre(), admi.getNombre());
            }
            else{

                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido agregar correctamente al administrador");

            }
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // Cerrar la ventana
        stage.close();
        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // Cerrar la ventana
        stage.close();
        
    }
    
}

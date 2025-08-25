/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.Pswd;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.clases.alertas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Acer
 */
public class PswdAdminController implements Initializable {


    @FXML
    private PasswordField TxtContraAdmin;
    
    Pswd ps = new Pswd();
    alertas alert = new alertas();
    Login login = new Login();
    historial hs = new historial();
    MainController main = new MainController();
    public static int intentosFallidos = 0;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TxtContraAdmin.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                    verificarContra();
            }   
        });
        
    }

    @FXML
    private void verificarContra() {
        
        if (TxtContraAdmin.getText().isEmpty()) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Error. Debes ingresar una contraseña");
            return;        
        }
        
        ps.setCod(TxtContraAdmin.getText());
        
        if (ps.verificar()) {
            
            main.abrirformularios("perfilAdmin.fxml", "Perfil de Administrador");
            TxtContraAdmin.getScene().getWindow().hide();
            intentosFallidos = 0;
            
            
        } else {
            
            intentosFallidos++;

            // Si los intentos fallidos son menos de 3, muestra el mensaje de "contraseña incorrecta"
            if (intentosFallidos < 3) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "Error. Contraseña incorrecta.");
                TxtContraAdmin.clear();
            } 

            // Si los intentos fallidos son exactamente 3, muestra la alerta final y cierra el programa
            if (intentosFallidos == 3) {
                javafx.application.Platform.runLater(() -> {
                    Alert alertaFinal = new Alert(Alert.AlertType.ERROR, "Demasiados intentos fallidos. Cerrando el programa.");
                    Stage stage = (Stage) alertaFinal.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
                    alertaFinal.setTitle("Aviso");
                    alertaFinal.setHeaderText(null);
                    
                    hs.insert("Error", "El usuario " + login.getUsuarioActual() + " ha fallado mas de 3 veces al intentar acceder al perfil de administrador", login.getUsuarioActual());      
                    
                    // Cerrar el programa después de la alerta
                    alertaFinal.setOnHidden(evt -> System.exit(0));
                    alertaFinal.showAndWait();
                });
            
            }
            
        }
        
    }
    
}
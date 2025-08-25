/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.campos.usuario;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.permisos;
import com.mycompany.inventario.clases.reportes;
import com.mycompany.inventario.clases.ruta;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
/**
 * FXML Controller class
 *
 * @author User
 */
public class UsuarioController implements Initializable {


    @FXML
    private TableView<usuario> table;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TableColumn<usuario, Integer> columId;
    @FXML
    private TableColumn<usuario, String> columNombre;
    @FXML
    private TableColumn<usuario, String> columCorreo;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private CheckBox checkMateriales;
    @FXML
    private CheckBox checkCliente;
    @FXML
    private CheckBox checkPedido;
    @FXML
    private CheckBox checkFacturacion;
    @FXML
    private CheckBox checkProveedor;
    @FXML
    private CheckBox checkUsuarios;
    @FXML
    private ImageView engranaje;
    @FXML
    private StackPane materialesStackPane;
    
    boolean modificar = false;
    
    ObservableList<usuario> listaFiltrada;
    ObservableList<usuario> listaUsuario;
    
    usuario one = new usuario();
    alertas alert = new alertas();
    Login login = new Login();
    permisos per = new permisos();
    reportes r = new reportes();
    ruta rut = new ruta();
    historial hs = new historial();
    
    boolean permiso = false;
    String h = "Boton Inhabilitado";
    @FXML
    private Pane configuracion;
    MainController m = new MainController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        permiso = per.Usuarios(login.getUsuarioActual());
        
        Label burbuja = crearBurbuja("!", "white"); // 
        materialesStackPane.getChildren().add(1, burbuja);
        actualizarUsuarios(burbuja);
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        checkCliente.setDisable(true);
        checkFacturacion.setDisable(true);
        checkMateriales.setDisable(true);
        checkPedido.setDisable(true);
        checkProveedor.setDisable(true);
        checkUsuarios.setDisable(true);
        
        if(permiso){

            btnGuardar.setDisable(true);
            btnCancelar.setDisable(true);
            btnEliminar.setDisable(true);
            btnModificar.setDisable(true);
            
        }
        else{
            
            btnGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            btnEliminar.setDisable(false);
            btnModificar.setDisable(false);
            btnNuevo.setDisable(false);
            btnLimpiar.setDisable(false);
            
            btnNuevo.setTooltip(TextButton(h));
            btnCancelar.setTooltip(TextButton(h));
            btnEliminar.setTooltip(TextButton(h));
            btnGuardar.setTooltip(TextButton(h));
            btnModificar.setTooltip(TextButton(h));
            btnLimpiar.setTooltip(TextButton(h));
            
            btnGuardar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Guardar ha sido presionado.");
            });
            
            btnNuevo.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Nuevo ha sido presionado.");
            });
            
            btnEliminar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Eliminar ha sido presionado.");
            });
            
            btnCancelar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Cancelar ha sido presionado.");
            });
            
            btnModificar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Modificar ha sido presionado.");
            });
            
            btnLimpiar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Limpiar ha sido presionado.");
            });
            
        }
        
        mostrardatos();
    } 
    
    public Label crearBurbuja(String mensaje, String color) {
        Label burbuja = new Label(mensaje);
        burbuja.setStyle("-fx-background-color: " + color + "; -fx-text-fill: #AD1316; -fx-padding: 2px 3px; -fx-background-radius: 20; -fx-font-size: 1;");

        burbuja.setTranslateX(40);  // horizontal
        burbuja.setTranslateY(-10);   // vertical
        burbuja.setVisible(false);    
        return burbuja;
    }
    
    public void mostrarBurbuja(Label burbuja, double cantidad, double cantidad_min) {
        burbuja.setVisible(true);
        System.out.println("burbuja mostrada con exito");
    }
    
    public Tooltip TextButton(String s){

        Tooltip t = new Tooltip(s);
        return t;
        
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
    private void swicthToProveedor(ActionEvent event) {
        
        try {
            App.setRoot("proveedor");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToUsuarios(ActionEvent event) {
        
        try {
            App.setRoot("usuario");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToHistorial(ActionEvent event) {
        
        if(one.verificar(login.getUsuarioActual())){
            String ubicacion= "/reportes/frameexperts/Historial.jasper";
            String titulo= "Informe de Actividades";
            JasperPrint jasperPrint = r.generarReporte(ubicacion, titulo);
            
            String ruta = rut.obtenerRutaDescargas();
            // Obtener la fecha actual en formato "dd-MM"
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM");
            String fechaFormateada = fechaActual.format(formatoFecha);

            // Crear el nombre del archivo con la fecha incluida
            String relativePath = ruta + "/Informe de Actividades " + fechaFormateada + ".pdf";
    
            // Crear un objeto File con la ruta relativa
            File file = new File(relativePath);

            try {
                // Exportar el reporte a la ruta relativa
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
                System.out.println("Reporte generado correctamente en " + file.getPath());
            } catch (JRException e) {
                System.out.println("Error al generar el reporte");
                e.printStackTrace();
            }
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "No tiene permiso para acceder a esta información");
            
        }
        
    }

    @FXML
    private void switchToMateriales(ActionEvent event) {
        
        try {
            App.setRoot("materia");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToCliente(ActionEvent event) {
        
        try {
            App.setRoot("cliente");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToPedido(ActionEvent event) {
        
        try {
            App.setRoot("pedido");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void abrirGestorContra() {
    
        m.abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    @FXML
    private void abrirPerfilAdmin() {
    
        m.abrirformularios("pswdAdmin.fxml", "Ingrese su contraseña de Administrador");
    
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtCorreo.setDisable(false);
        txtCodigo.setDisable(false);
        
        checkCliente.setDisable(false);
        checkFacturacion.setDisable(false);
        checkMateriales.setDisable(false);
        checkPedido.setDisable(false);
        checkProveedor.setDisable(false);
        checkUsuarios.setDisable(false);
        
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        
        btnNuevo.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
    }

    @FXML
    private void Modificar(ActionEvent event) {
        
       txtNombre.setDisable(false);
       txtCorreo.setDisable(false);
       
       checkCliente.setDisable(false);
       checkFacturacion.setDisable(false);
       checkMateriales.setDisable(false);
       checkPedido.setDisable(false);
       checkProveedor.setDisable(false);
       checkUsuarios.setDisable(false);
        
       btnEliminar.setDisable(true);
       btnNuevo.setDisable(true);
       btnModificar.setDisable(true);
       btnLimpiar.setDisable(true);
       
       btnGuardar.setDisable(false);
       btnCancelar.setDisable(false);
       
       modificar = true;
        
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        
        if(txtNombre.getText().equals(login.getUsuarioActual())){
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No es posible darse de baja a uno mismo");
            return;
            
        }
        
        one.setNombre(txtNombre.getText());
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        
        Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
        alerta1.setTitle("Aviso");
        alerta1.setHeaderText(null);
        alerta1.setContentText("¿Desea dar de baja al usuario seleccionado?");
        Stage stage = (Stage) alerta1.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
        Optional<ButtonType> opcion = alerta1.showAndWait();
        
        if(opcion.get() == ButtonType.OK){
            
            one.setId(Integer.parseInt(txtID.getText()));

            if(one.eliminar()){

                    alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Usuario dado de baja correctamente");
                    hs.insert("Dar de baja", "El usuario " + login.getUsuarioActual() + " ha dado de baja al usuario " + one.getNombre() + " de la tabla usuarios", login.getUsuarioActual());

                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido dar de baja correctamente");

                }
        } 
        else{
            
            Cancelar(event);
            
        }
        
        txtCorreo.clear();
        txtNombre.clear();
        txtCodigo.clear();
        
        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        mostrardatos();
        actualizarGestos();
        
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        if (txtNombre.getText().isEmpty() || 
            txtCorreo.getText().isEmpty()){

            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios");
            return;
        }
        if (!txtNombre.getText().matches("[a-zA-Z ]+")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El nombre no debe contener números");
            return;
        }
        if (!txtCorreo.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El formato del correo es incorrecto");
            return;
        }
        
        if(!txtCodigo.getText().isEmpty()){
            if (!txtCodigo.getText().matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La contraseña debe tener un mínimo de 6 caracteres, contener letras, números, al menos una mayúscula y un carácter especial.");
                return;
            }
        }
        one.setNombre(txtNombre.getText());
        one.setCodigo(txtCodigo.getText());
        one.setCorreo(txtCorreo.getText());
        
        one.setPcliente(checkCliente.isSelected());
        one.setPfactura(checkFacturacion.isSelected());
        one.setPmateriales(checkMateriales.isSelected());
        one.setPpedido(checkPedido.isSelected());
        one.setPproveedor(checkProveedor.isSelected());
        one.setPusuarios(checkUsuarios.isSelected());
        
        
        if(modificar){
            
            one.setId(Integer.parseInt(txtID.getText()));

            
            if(one.modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                hs.insert("Modificar", "El usuario " + login.getUsuarioActual() + " ha modificar los datos de " + one.getNombre() + " en la tabla usuarios", login.getUsuarioActual());
                
            }
            else{
                
                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");

                
            }
            
        }
        else{
            
            if (one.existeUsuario(txtNombre.getText())) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El usuario ya existe en la base de datos");
                return;
            }

            if(one.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");
                hs.insert("Crear", "El usuario " + login.getUsuarioActual() + " ha agregado un nuevo usuario " + one.getNombre() + " en la tabla usuarios", login.getUsuarioActual());

            }
            else{

                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");

            }
            
        }
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        checkCliente.setDisable(true);
        checkFacturacion.setDisable(true);
        checkMateriales.setDisable(true);
        checkPedido.setDisable(true);
        checkProveedor.setDisable(true);
        checkUsuarios.setDisable(true);
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        btnLimpiar.setDisable(false);

        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        
        modificar = false;
        
        mostrardatos();
        actualizarGestos();
        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        
        btnNuevo.setDisable(false);        
        btnLimpiar.setDisable(false);        
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtCodigo.setDisable(true);
        
        checkCliente.setDisable(true);
        checkFacturacion.setDisable(true);
        checkMateriales.setDisable(true);
        checkPedido.setDisable(true);
        checkProveedor.setDisable(true);
        checkUsuarios.setDisable(true);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        
        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        txtBusqueda.clear();
        
        modificar = false;
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        
        checkCliente.setSelected(false);
        checkFacturacion.setSelected(false);
        checkMateriales.setSelected(false);
        checkPedido.setSelected(false);
        checkProveedor.setSelected(false);
        checkUsuarios.setSelected(false);
        
        txtBusqueda.clear();
        
    }

    @FXML
    private void Busqueda(ActionEvent event) {
        
       listaFiltrada = FXCollections.observableArrayList();
       String buscar = txtBusqueda.getText();
        if(buscar.isEmpty()){
            
            table.setItems(listaUsuario);
            
        }
        else{
            
            listaFiltrada.clear();
            for (usuario listasUsuarios : listaUsuario) {
                
                if(listasUsuarios.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listasUsuarios);
                    
                }
                
            }
            
            table.setItems(listaUsuario);
            
        }
        
    }

    private void swicthToAdmi(ActionEvent event) {
        
        try {
            App.setRoot("perfil");
        } catch (IOException ex) {
            Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void mostrardatos(){
        
    listaUsuario = FXCollections.observableArrayList(one.consulta());
    columId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    columCorreo.setCellValueFactory(new PropertyValueFactory<>("Correo"));
    table.setItems(listaUsuario);    
        
    }

    @FXML
    private void Click(MouseEvent event) {
        
        if (!permiso) {
            event.consume(); // Consume el evento para evitar cualquier otra acción
            return; // Salir del método
        }
        
        usuario one = table.getSelectionModel().getSelectedItem();
        
        if(one != null){
            txtNombre.setText(one.getNombre());
            txtCorreo.setText(one.getCorreo());
            txtID.setText(String.valueOf(one.getId()));

            checkCliente.setSelected(one.isPcliente());
            checkFacturacion.setSelected(one.isPfactura());
            checkMateriales.setSelected(one.isPmateriales());
            checkPedido.setSelected(one.isPpedido());
            checkProveedor.setSelected(one.isPproveedor());
            checkUsuarios.setSelected(one.isPusuarios());
            System.out.println(one.isPcliente());

            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
            btnCancelar.setDisable(false);

            txtNombre.setDisable(true);
            txtCorreo.setDisable(true);
            txtCodigo.setDisable(true);

            btnNuevo.setDisable(true);
        }
        
    }


    @FXML
    private void Config(ActionEvent event) {

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
    }

    @FXML
    private void bajarPDF(ActionEvent event) {
        
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
    
    public void actualizarUsuarios(Label burbuja) {
    
        MateriaController materiaController = (MateriaController) App.getController("materia");

        if (materiaController != null) {
            materiaController.verificarStockBajo(burbuja);
        } else {
            System.out.println("Error: No se encontro el controlador de Materia.");
        }
        
    }
    
    public void actualizarGestos() {
    
        GestorContraController Controller = (GestorContraController) App.getController("gestorContra");

        if (Controller != null) {
            Controller.cargarUsuario();
        } else {
            System.out.println("Error: No se encontro el controlador de GestorContraseñas.");
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
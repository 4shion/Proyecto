/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.campos.proveedor;
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
public class ProveedorController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<proveedor> table;
    @FXML
    private TableColumn<proveedor, Integer> ColumID;
    @FXML
    private TableColumn<proveedor, String> ColumNombre;
    @FXML
    private TableColumn<proveedor, String> ColumCorreo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtId;
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
    private Pane configuracion;
    @FXML
    private ImageView engranaje;
    @FXML
    private StackPane materialesStackPane;
    
    proveedor one = new proveedor();
    
    private boolean modificar = false;
    
    ObservableList<proveedor> lista;
    ObservableList<proveedor> listaFiltrada;
    
    alertas alert = new alertas();
    Login login = new Login();
    permisos per = new permisos();
    usuario u = new usuario();
    reportes r = new reportes();
    ruta rut = new ruta();
    historial hs = new historial();
    boolean permiso = false;
    String h = "Boton Inhabilitado";
    
    @FXML
    private TableColumn<proveedor, String> ColumTelefono;
    @FXML
    private TextField txtTelefono;
    MainController m = new MainController();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
        permiso = per.Proveedores(login.getUsuarioActual());
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtTelefono.setDisable(true);
        
        Label burbuja = crearBurbuja("!", "white"); // 
        materialesStackPane.getChildren().add(1, burbuja);
        actualizarPBurbuja(burbuja);
        
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
    private void Busqueda(ActionEvent event) {
        
        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBusqueda.getText();
        if(buscar.isEmpty()){
            
            table.setItems(lista);
            
        }
        else{
            
            listaFiltrada.clear();
            for (proveedor listas : lista) {
                
                if(listas.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listas);
                    
                }
                
            }
            
            table.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void click(MouseEvent event) {
        
        if (!permiso) {
            event.consume(); // Consume el evento para evitar cualquier otra acción
            return; // Salir del método
        }
        
        proveedor one = table.getSelectionModel().getSelectedItem();
        
        if(one != null){
            txtNombre.setText(one.getNombre());
            txtCorreo.setText(one.getCorreo());
            txtTelefono.setText(one.getTelefono());
            txtId.setText(String.valueOf(one.getId()));

            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
            btnCancelar.setDisable(false);

            txtNombre.setDisable(true);
            txtCorreo.setDisable(true);
            txtTelefono.setDisable(true);

            btnNuevo.setDisable(true);
        }
        
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtCorreo.setDisable(false);
        txtTelefono.setDisable(false);
        
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
       txtTelefono.setDisable(false);
        
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
        one.setId(Integer.parseInt(txtId.getText()));   
        one.setNombre(txtNombre.getText());
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);

        btnNuevo.setDisable(false);

        // Confirmación para eliminar
        Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
        alerta1.setTitle("Aviso");
        alerta1.setHeaderText(null);
        alerta1.setContentText("¿Desea dar de baja el registro seleccionado?");
        Stage stage = (Stage) alerta1.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
        Optional<ButtonType> opcion = alerta1.showAndWait();

        if (opcion.isPresent() && opcion.get() == ButtonType.OK) {
            
            // Eliminar el proveedor
            if (one.eliminar()) {
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Proveedor dado de baja correctamente");
            } else {
                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido dar de baja al proveedor correctamente");
            }
        } else {
            Cancelar(event); // Si no se confirma la eliminación, cancelar
        }

        // Limpiar campos
        txtCorreo.clear();
        txtNombre.clear();
        txtTelefono.clear();

        // Actualizar datos en la interfaz
        mostrardatos();
        actualizarProveedor();
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        if (txtNombre.getText().isEmpty() || 
            txtCorreo.getText().isEmpty() || 
            txtTelefono.getText().isEmpty()) {

            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios");
            return;
        }
        
        if (txtNombre.getText().matches("\\d+")) { // Comprueba si el nombre solo contiene dígitos
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El nombre no puede contener solo números");
            return;
        }
        String telefono = txtTelefono.getText();
        if (!telefono.matches("\\d+")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "El número de teléfono solo puede contener números.");
            return;
        }
        if (!txtCorreo.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El formato del correo es incorrecto");
            return;
        }
        
        one.setNombre(txtNombre.getText());
        one.setCorreo(txtCorreo.getText());
        one.setTelefono(txtTelefono.getText());
        
        if(one.existeProveedor()){
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El proveedor ya existe");
            return;
            
        }
        
        if(modificar){
            
            one.setId(Integer.parseInt(txtId.getText()));

            
            if(one.modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                hs.insert("Modificar", "El usuario " + login.getUsuarioActual() + " ha modificar los datos de " + one.getNombre() + " en la tabla proveedores", login.getUsuarioActual());
                
            }
            else{
                
                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");
                
            }
            
        }
        else{
        
            if(one.existeProveedor()){
            
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El proveedor ya existe");
                return;

            }
            
            if(one.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");
                hs.insert("Crear", "El usuario " + login.getUsuarioActual() + " ha agregado a " + one.getNombre() + " en la tabla proveedores", login.getUsuarioActual());

            }
            else{

                alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");

            }
            
        }
        
        txtNombre.setDisable(true);
        txtCorreo.setDisable(true);
        txtTelefono.setDisable(true);
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        btnLimpiar.setDisable(false);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
        modificar = false;
        
        mostrardatos();
        actualizarProveedor();
        
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
        txtTelefono.setDisable(true);
        
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
        txtBusqueda.clear();
        
        modificar = false;
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        
        txtBusqueda.clear();
        
    }
    
    public void mostrardatos(){
        
    lista = FXCollections.observableArrayList(one.consulta());
    ColumID.setCellValueFactory(new PropertyValueFactory<>("id"));
    ColumNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    ColumCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
    ColumTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    table.setItems(lista);
        
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
        
        if(u.verificar(login.getUsuarioActual())){
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
    private void verificar() {
        m.abrirformularios("pswdAdmin.fxml", "Verificar Identidad");
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
    
    public void actualizarProveedor() {

        MateriaController materiaController = (MateriaController) App.getController("materia");

        if (materiaController != null) {
            // Volver a cargar los datos de la tabla
            materiaController.cargarProveedor();
        } else {
            System.out.println("Error: No se encontro el controlador de Materia.");
        }
        }
    
    public void actualizarPBurbuja(Label burbuja) {
    
        MateriaController materiaController = (MateriaController) App.getController("materia");

        if (materiaController != null) {
            materiaController.verificarStockBajo(burbuja);
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

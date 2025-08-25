/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.campos.materia;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
public class MateriaController extends App implements Initializable{

    @FXML
    private TableView<materia> table;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private ComboBox<String> cboSelProov;
    @FXML
    private TextField txtCantidad;
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
    private TableColumn<materia, Integer> colId;
    @FXML
    private TableColumn<materia, String> colNombre;
    @FXML
    private TableColumn<materia, String> colPrecio;
    @FXML
    private TableColumn<materia, String> colCantidad;
    @FXML
    private TableColumn<materia, String> colProveedor;
    
    //Hola
    materia m = new materia();
    proveedor p = new proveedor();
    alertas alert = new alertas();
    MainController main = new MainController();
    historial hs = new historial();
    reportes r = new reportes();
    usuario u = new usuario();
    ruta rut = new ruta();
    
    ObservableList<materia> listaMateria;
    ObservableList<proveedor> listaProveedor;
    ObservableList<materia> listaFiltrada;

    @FXML
    private TableColumn<materia, String> colCantMin;
    
    boolean bandera = false;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtCamMín;
    @FXML
    private Pane configuracion;
    @FXML
    private ImageView engranaje;
    @FXML
    private TextField TxtUniMed;
    @FXML
    private StackPane materialesStackPane;
   
    Login login = new Login();
    permisos per = new permisos();
    boolean permiso = false;
    String h = "Boton Inhabilitado";
    double cantidad = m.cantidad;
    double cantidad_min = m.cantidad_min;
    @FXML
    private Button btnReporte;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        permiso = per.Materiales(login.getUsuarioActual());

        txtNombre.setDisable(true);
        txtPrecio.setDisable(true);
        txtCantidad.setDisable(true);
        cboSelProov.setDisable(true);
        txtCamMín.setDisable(true);
        TxtUniMed.setDisable(true);
        
    Label burbuja = crearBurbuja("!", "#D6454A"); // 
    materialesStackPane.getChildren().add(1, burbuja);
    verificarStockBajo(burbuja);
        
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
            btnReporte.setDisable(false);
            
            btnNuevo.setTooltip(TextButton(h));
            btnCancelar.setTooltip(TextButton(h));
            btnEliminar.setTooltip(TextButton(h));
            btnGuardar.setTooltip(TextButton(h));
            btnModificar.setTooltip(TextButton(h));
            btnLimpiar.setTooltip(TextButton(h));
            btnReporte.setTooltip(TextButton(h));
            
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
            
            btnReporte.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Reporte ha sido presionado.");
            });
            
        }
                
        mostrarDatos();
        
        
        table.setRowFactory(tv -> new TableRow<materia>() {
        @Override
        protected void updateItem(materia item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setStyle("");
                return;
            } else {
                if (item.getCantidad() < item.getCantidad_min()) {
                    setStyle("-fx-background-color: #ff6969;");
                    verificarStockBajo(burbuja);
                } else if (item.getCantidad() == item.getCantidad_min()){
                    setStyle("-fx-background-color: #ffd569");
                    verificarStockBajo(burbuja);
                } else {
                    setStyle("");
                    verificarStockBajo(burbuja);
                } 
            }
        }
        });
        
    }
    
    public Tooltip TextButton(String s){
        
        Tooltip t = new Tooltip(s);
        return t;
        
    }
    
    // metodo para crear la burbuja de notificacion
    public Label crearBurbuja(String mensaje, String color) {
        Label burbuja = new Label(mensaje);
        burbuja.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-padding: 2px 3px; -fx-background-radius: 20; -fx-font-size: 1;");

        burbuja.setTranslateX(40);  // horizontal
        burbuja.setTranslateY(-10);   // vertical
        burbuja.setVisible(false);    
        return burbuja;
    }

    
    public void mostrarBurbuja(Label burbuja, double cantidad, double cantidad_min) {
        burbuja.setVisible(true);
        System.out.println("burbuja mostrada con exito");
    }
    
    public void verificarStockBajo(Label burbuja) {
        boolean hayStockBajo = false;

        for (materia item : table.getItems()) {
            if (item.getCantidad() < item.getCantidad_min()) {
                hayStockBajo = true;
                System.out.println("stock bajo encontrado");
                break; 
            }
        }

        if (hayStockBajo) {
            mostrarBurbuja(burbuja, cantidad, cantidad_min);
        } else {
            burbuja.setVisible(false); 
        }
    }


    public MateriaController(){
    }
    
    @FXML
    private void Busqueda(ActionEvent event) {
        
       
       listaFiltrada = FXCollections.observableArrayList();
       String buscar = txtBusqueda.getText();
        if(buscar.isEmpty()){
            
            table.setItems(listaMateria);
            
        }
        else{
            
            listaFiltrada.clear();
            for (materia listasMateria : listaMateria) {
                
                if(listasMateria.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listasMateria);
                    
                }
                
            }
            
            table.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void Nuevo(ActionEvent event) {
        
        // hablitar los campos y botones
        txtNombre.setDisable(false);
        txtPrecio.setDisable(false);
        txtCantidad.setDisable(false);
        txtCamMín.setDisable(false);
        TxtUniMed.setDisable(false);
        
        cboSelProov.setDisable(false);
        
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnNuevo.setDisable(true);
        
        //se carga los prompt
        cboSelProov.setPromptText("Proveedor");
        
        //se carga los datos en los combos
        cargarProveedor();
        
    }

    @FXML
    private void Modificar(ActionEvent event) {
        
        txtNombre.setDisable(false);
        txtPrecio.setDisable(false);
        txtCantidad.setDisable(false);
        txtCamMín.setDisable(false);
        TxtUniMed.setDisable(false);
        
        cboSelProov.setDisable(false);
        
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        
        btnModificar.setDisable(true);
        btnNuevo.setDisable(true);
        btnEliminar.setDisable(true);
        btnLimpiar.setDisable(true);
        
        cargarProveedor();
        
        bandera = true;

        
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        
        btnNuevo.setDisable(false);
        
        Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
        alerta1.setTitle("Aviso");
        alerta1.setHeaderText(null);
        alerta1.setContentText("¿Desea dar de baja el registro seleccionado?");
        Stage stage = (Stage) alerta1.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
        Optional<ButtonType> opcion = alerta1.showAndWait();
        
        if(opcion.get() == ButtonType.OK){
            
            m.setId(Integer.parseInt(txtId.getText()));
            m.setNombre(txtNombre.getText());

            if(m.eliminar()){

                    alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Material dado de baja correctamente");
                    hs.insert("Eliminar", "El usuario " + login.getUsuarioActual() + " ha dado de baja " + m.getNombre() + " de la tabla materiales", login.getUsuarioActual());

                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido dar de baja correctamente");

                }
        } 
        else{
            
            Cancelar(event);
            
        }
        
        txtId.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        cboSelProov.getItems().clear();
        txtCamMín.clear();
        TxtUniMed.clear();
        
        mostrarDatos();
        actualizarMateriales();
        
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        try{
            String nombrePro = cboSelProov.getSelectionModel().getSelectedItem();
            if (txtNombre.getText().isEmpty() || 
                txtPrecio.getText().isEmpty() || 
                txtCantidad.getText().isEmpty() || 
                txtCamMín.getText().isEmpty() || 
                TxtUniMed.getText().isEmpty() ||
                nombrePro == null) {

                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios");
                return;
            }

            // Validar que el precio, cantidad y camMín sean números válidos
            try {
                double precio = Double.parseDouble(txtPrecio.getText());
                if (precio <= 0) {
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El precio debe ser un valor positivo mayor a 0.");
                    return;
                }
                m.setPrecio(precio);
            } catch (NumberFormatException e) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El precio debe ser un número válido");
                return;
            }

            try {
                double c = Double.parseDouble(txtCantidad.getText());
                double cm = Double.parseDouble(txtCamMín.getText());

                if (c <= 0 || cm <= 0) {
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad y la cantidad mínima deben ser valores positivos mayores a 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Por favor ingrese un número válido en los campos de cantidad.");
                return;
            }

            if (!txtNombre.getText().matches("[a-zA-Z ]+")) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El nombre no debe contener números");
                return;
            }

            if (!TxtUniMed.getText().matches("[a-zA-Z]{1,2}")) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La unidad de medida debe tener como máximo dos letras");
                return;
            }
            
            m.setNombre(txtNombre.getText());
            int pro = buscarProveedor();
            m.setIdProveedor(pro);
            double c = Double.parseDouble(txtCantidad.getText());
            double cm = Double.parseDouble(txtCamMín.getText());
            m.setUnidadMedida(TxtUniMed.getText());
            
            if(c <= cm){

                Alert alerta2 = new Alert(Alert.AlertType.CONFIRMATION);
                alerta2.setHeaderText(null);
                alerta2.setContentText("La Cantidad Total es inferior a la Cantidad mínima. ¿Desea continuar?");
                Stage stage = (Stage) alerta2.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
                ButtonType btnSi = new ButtonType("Sí");
                ButtonType btnNo = new ButtonType("No");
                alerta2.getButtonTypes().setAll(btnSi, btnNo);

                Optional<ButtonType> result = alerta2.showAndWait();
                if (result.get() == btnNo) {
                    return; 
                }

            }

            m.setCantidad(c);
            m.setCantidad_min(cm);

            if(bandera){//modificar

                m.setId(Integer.parseInt(txtId.getText()));

                if(m.modificar()){
                
                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Modificado correctamente");
                hs.insert("Modificar", "El usuario " + login.getUsuarioActual() + " ha modificado " + m.getNombre() + " en la tabla materiales", login.getUsuarioActual());
                
                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido modificado correctamente");

                }

            bandera = false; 

            }else{

                if(m.existeMaterial(m.getNombre())){
            
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Ese material ya existe");
                    return;

                }
                
                if (m.existeMaterial(txtNombre.getText())) {
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "El material ya existe en la base de datos");
                    return;
                }
                
                if(m.insertar()){

                alert.ShowAlert(Alert.AlertType.CONFIRMATION, "Aviso", "Insertado correctamente");
                hs.insert("Crear", "El usuario " + login.getUsuarioActual() + " ha creado " + m.getNombre() + " en la tabla materiales", login.getUsuarioActual());

                }
                else{

                    alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");

                }

            } 

            mostrarDatos();
            actualizarMateriales();
            Cancelar(event);
            Limpiar(event);
        }
        catch (NumberFormatException e) {
            
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error en el formato de número: " + e.getMessage());
            Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
            alerta.show();
            
        }

        
    }

    @FXML
    private void Cancelar(ActionEvent event) {
        // Llamamos a la versión sobrecargada que no recibe eventos
        cboSelProov.getSelectionModel().clearSelection();
        cargarProveedor();
        Cancelar();
    }

    public void Cancelar() {
        // Limpiar los campos de texto
        txtId.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCamMín.clear();
        TxtUniMed.clear();

        // Limpiar y deshabilitar el comboBox
        cboSelProov.getItems().clear();
        cboSelProov.setDisable(true);

        // Deshabilitar los campos de texto
        txtNombre.setDisable(true);
        txtPrecio.setDisable(true);
        txtCantidad.setDisable(true);
        txtCamMín.setDisable(true);
        TxtUniMed.setDisable(true);

        // Deshabilitar botones
        btnGuardar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnCancelar.setDisable(true);

        // Habilitar otros botones
        btnNuevo.setDisable(false);
        btnLimpiar.setDisable(false);
    }
    
    public void mostrarDatos(){
        
       listaMateria = FXCollections.observableArrayList(m.consulta());
       colId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
       colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreproveedor"));
//       ColumUni.setCellValueFactory(new PropertyValueFactory<>("unidadMedida"));
       colCantidad.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCantidadConUnidad()));
       colCantMin.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCant_MinConUnidad()));
       colPrecio.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPrecioConEuro()));
       table.setItems(listaMateria);
       
   }
    
    public void cargarProveedor() {
        listaProveedor = FXCollections.observableArrayList(new proveedor().consulta());
        
        cboSelProov.getItems().clear();
        
        cboSelProov.getItems().addAll(
            listaProveedor.stream().map(proveedor::getNombre).toList()
        );
    }
    
    private int buscarProveedor() {
        
        for (proveedor object : listaProveedor) {
            
            if (object.getNombre().contains(cboSelProov.getSelectionModel().getSelectedItem())) {
                
                return object.getId();                
                
            }
            
        }  
        
        return 0;
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        
        txtId.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCamMín.clear();
        txtBusqueda.clear();
        TxtUniMed.clear();
        
        cboSelProov.getItems().clear();
        
    }

    @FXML
    private void Click(MouseEvent event) {
        // Verificar si el permiso es falso
        if (!permiso) {
            event.consume(); // Consume el evento para evitar cualquier otra acción
            return; // Salir del método
        }

        // Continuar con la funcionalidad del evento si el permiso es verdadero
        materia m = table.getSelectionModel().getSelectedItem();

        // Asegúrate de que hay un elemento seleccionado
        if (m != null) {
            txtId.setText(String.valueOf(m.getId()));
            txtNombre.setText(m.getNombre());
            TxtUniMed.setText(m.getUnidadMedida());
            txtPrecio.setText(String.valueOf(m.getPrecio()));
            txtCantidad.setText(String.valueOf(m.getCantidad()));
            txtCamMín.setText(String.valueOf(m.getCantidad_min()));
            cboSelProov.setValue(m.getNombreproveedor());

            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
            btnCancelar.setDisable(false);

            txtNombre.setDisable(true);
            txtPrecio.setDisable(true);
            txtCantidad.setDisable(true);
            cboSelProov.setDisable(true);
            txtCamMín.setDisable(true);
            TxtUniMed.setDisable(true);

            btnNuevo.setDisable(true);
        }
        
        if (m.getCantidad() < m.getCantidad_min()){
            
            Alert alerta3 = new Alert(Alert.AlertType.CONFIRMATION);
            alerta3.setHeaderText(null);
            alerta3.setContentText("La Cantidad Total es inferior a la Cantidad mínima. ¿Desea visualizar los datos del proveedor?");
            Stage stage = (Stage) alerta3.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
            ButtonType btnSi = new ButtonType("Sí");
            ButtonType btnNo = new ButtonType("No");
            alerta3.getButtonTypes().setAll(btnSi, btnNo);

            Optional<ButtonType> result = alerta3.showAndWait();
            if (result.isPresent() && result.get() == btnSi) {
                
                String proveedorNombre = m.getNombreproveedor();
                p.buscarDatosProveedor(proveedorNombre);
                String proveedorCorreo = p.getCorreo();
                String proveedorTelefono = p.getTelefono();

                // Mostrar los datos del proveedor en una alerta
                Alert alertaProveedor = new Alert(Alert.AlertType.INFORMATION);
                alertaProveedor.setHeaderText("Datos del Proveedor");
                alertaProveedor.setContentText(
                    "Nombre: " + proveedorNombre + "\n" +
                    "Correo: " + proveedorCorreo + "\n" +
                    "Teléfono: " + proveedorTelefono
                );
                Stage stageP = (Stage) alertaProveedor.getDialogPane().getScene().getWindow();
                stageP.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
                alertaProveedor.showAndWait();
                
            }
            
        }
        
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
    
        main.abrirformularios("gestorContra.fxml", "Gestor de Contraseñas");
    
    }
    @FXML
    private void abrirPerfilAdmin() {
    
        main.abrirformularios("pswdAdmin.fxml", "Ingrese su contraseña de Administrador");
    
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
              System.out.println("El archivo HND no existe.");
          }
        
    }
    
    @FXML
    private void generarReporteMateriales(ActionEvent event) {
        
        // Verificar si hay stock por debajo de la cantidad mínima
        boolean hayStockBajo = m.verificarStockBajo();

        if (hayStockBajo) {
            // Mostrar alerta con dos opciones si hay stock bajo
            Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacion.setTitle("Visualización de Stock");
            alertaConfirmacion.setHeaderText("¿Qué stock deseas visualizar?");
            alertaConfirmacion.setContentText("Selecciona el tipo de stock que deseas ver:");
            Stage stage = (Stage) alertaConfirmacion.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));

            ButtonType btnStockActual = new ButtonType("Stock Actual");
            ButtonType btnStockBajo = new ButtonType("Stock Bajo");
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alertaConfirmacion.getButtonTypes().setAll(btnStockActual, btnStockBajo, btnCancelar);

            Optional<ButtonType> result = alertaConfirmacion.showAndWait();

            if (result.isPresent()) {
                if (result.get() == btnStockActual) {
                    // Visualizar el reporte de materiales
                    String ubicacion = "/reportes/frameexperts/Materiales.jasper";
                    String titulo = "Stock Actual de Materiales";
                    
                    JasperPrint jasperPrint = r.generarReporte(ubicacion, titulo);
            
                    String ruta = rut.obtenerRutaDescargas();
                    // Obtener la fecha actual en formato "dd-MM"
                    LocalDate fechaActual = LocalDate.now();
                    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM");
                    String fechaFormateada = fechaActual.format(formatoFecha);

                    // Crear el nombre del archivo con la fecha incluida
                    String relativePath = ruta + "/Stock Actual " + fechaFormateada + ".pdf";

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
                    
                } else if (result.get() == btnStockBajo) {
                    // Visualizar el reporte de stock bajo
                    String ubicacion = "/reportes/frameexperts/MaterialesBajos.jasper";
                    String titulo = "Materiales con Stock Bajo";
                    
                    JasperPrint jasperPrint = r.generarReporte(ubicacion, titulo);
            
                    String ruta = rut.obtenerRutaDescargas();
                    // Obtener la fecha actual en formato "dd-MM"
                    LocalDate fechaActual = LocalDate.now();
                    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM");
                    String fechaFormateada = fechaActual.format(formatoFecha);

                    // Crear el nombre del archivo con la fecha incluida
                    String relativePath = ruta + "/Stock Bajo " + fechaFormateada + ".pdf";

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
            }
        } else {
            // Si no hay stock bajo, visualizar directamente el reporte de materiales
            String ubicacion = "/reportes/frameexperts/Materiales.jasper";
            String titulo = "Stock Actual de Materiales";
            
            JasperPrint jasperPrint = r.generarReporte(ubicacion, titulo);
            
            String ruta = rut.obtenerRutaDescargas();
            // Obtener la fecha actual en formato "dd-MM"
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM");
            String fechaFormateada = fechaActual.format(formatoFecha);

            // Crear el nombre del archivo con la fecha incluida
            String relativePath = ruta + "/Stock Actual " + fechaFormateada + ".pdf";

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
        
    }
    
    public void actualizarMateriales() {

        PedidoController pedidoController = (PedidoController) App.getController("pedido");

        if (pedidoController != null) {
            // Volver a cargar los datos de la tabla
            pedidoController.cargarMaterial();
        } else {
            System.out.println("Error: No se encontró el controlador de pedidos/.");
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



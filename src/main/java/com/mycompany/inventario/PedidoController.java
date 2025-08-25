package com.mycompany.inventario;

import com.mycompany.inventario.campos.Login;
import com.mycompany.inventario.campos.cliente;
import com.mycompany.inventario.campos.factura;
import com.mycompany.inventario.campos.historial;
import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.campos.pedido;
import com.mycompany.inventario.campos.usuario;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import com.mycompany.inventario.clases.permisos;
import com.mycompany.inventario.clases.reportes;
import com.mycompany.inventario.clases.ruta;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

public class PedidoController implements Initializable {

    @FXML private TextArea TxtServicio;
    @FXML private TableView<pedido> table;
    @FXML private TableColumn<pedido, String> ColumMaterial;
    @FXML private TableColumn<pedido, String> ColumCantidad;
    @FXML private TableColumn<pedido, String> ColumStock;
    @FXML private ComboBox<String> CbmMateriales;
    @FXML private Text txtCosto;
    @FXML private Button BtnFactura;
    @FXML private TextField TxtCant;
    @FXML private Button BtnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;
    @FXML private Pane configuracion;
    @FXML private ImageView engranaje;
    @FXML private TextField txtNomCliente;

    private conexion conexionDB = new conexion();
    private ObservableList<materia> listaMateriales;
    private alertas alert = new alertas();
    private pedido p = new pedido();
    private factura f = new factura();
    private cliente client = new cliente();
    private reportes r = new reportes();
    private MainController main = new MainController();
    private materia m = new materia();
    private ruta  rut = new ruta();
    private usuario u = new usuario();
    
    Login login = new Login();
    permisos per = new permisos();
    historial hs = new historial();
    boolean permiso = false;
    String h = "Boton Inhabilitado";
    
    @FXML
    private Button btnNoName;
    @FXML
    private StackPane materialesStackPane;
    @FXML
    private Button BtnPedidos;
    @FXML
    private TextField idPedido;
    @FXML
    private TextField correoCliente;
    @FXML
    private TextField telfCliente;
    @FXML
    private TextField numFactura;
    @FXML
    private ImageView engranaje1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarMaterial();
        configurarColumnas();
                
        permiso = per.Pedidos(login.getUsuarioActual());
        
        Label burbuja = crearBurbuja("!", "white"); // 
        materialesStackPane.getChildren().add(1, burbuja);
        actualizarPedido(burbuja);
        
        
        if(permiso){

            btnEliminar.setDisable(true);
            TxtServicio.setPrefWidth(200);
            TxtServicio.setWrapText(true);
            
        }
        else{
            
            btnGuardar.setDisable(false);
            BtnAgregar.setDisable(false);
            btnEliminar.setDisable(false);
            btnNoName.setDisable(false);
            BtnFactura.setDisable(false);
            btnLimpiar.setDisable(false);
            BtnPedidos.setDisable(false);
            
            TxtCant.setDisable(true);
            TxtServicio.setDisable(true);
            txtNomCliente.setDisable(true);
            CbmMateriales.setDisable(true);
            
            BtnAgregar.setTooltip(TextButton(h));
            BtnFactura.setTooltip(TextButton(h));
            btnEliminar.setTooltip(TextButton(h));
            btnGuardar.setTooltip(TextButton(h));
            btnNoName.setTooltip(TextButton(h));
            btnLimpiar.setTooltip(TextButton(h));
            BtnPedidos.setTooltip(TextButton(h));
            
            btnGuardar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Guardar ha sido presionado.");
            });
            
            BtnAgregar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Agregar ha sido presionado.");
            });
            
            btnEliminar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Eliminar ha sido presionado.");
            });
            
            btnNoName.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Cliente Sin Nombre ha sido presionado.");
            });
            
            BtnFactura.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Factura ha sido presionado.");
            });
            
            btnLimpiar.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Limpiar ha sido presionado.");
            });
            
            BtnPedidos.setOnAction(event -> {
                boolean shouldCancel = true;
                if (shouldCancel) {
                    event.consume();
                    return;
                }
                System.out.println("Botón Generar Pedidos ha sido presionado.");
            });
            
        }
        
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
    private void Eliminar(ActionEvent event) {
        pedido materialseleccionado = table.getSelectionModel().getSelectedItem();
        if (materialseleccionado != null) {
            String nombreMaterial = materialseleccionado.getNombreM();
            table.getItems().remove(materialseleccionado);
            CbmMateriales.getItems().add(nombreMaterial);
            limpiarCampos();
            calcularSubtotal();
            if (table.getItems().isEmpty()) {
                txtCosto.setText("SubTotal:");
            }
        } else {
            alert.ShowAlert(Alert.AlertType.WARNING, "Advertencia", "Seleccione un material para eliminar");
        }
        CbmMateriales.setDisable(false);
    }

    @FXML
    private void Guardar(ActionEvent event) {
        
        if (TxtServicio.getText().isEmpty() || 
            txtNomCliente.getText().isEmpty()) {

            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Los campos de servicio y nombre del cliente son obligatorios");
            return;
        }
        
        p.setServicio(TxtServicio.getText());
        p.setNombreC(txtNomCliente.getText());
        p.obtenerIdClientePorNombre(txtNomCliente.getText());
        p.setTotalPedido(calcularSubtotal());

        int numFilas = table.getItems().size();
        if (numFilas == 0) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La tabla de materiales no puede estar vacía");
            return;
        }
        
        String[] listaMaterialesN = new String[numFilas];
        double[] listaCant = new double[numFilas];

        for (int i = 0; i < numFilas; i++) {
            pedido materiales = table.getItems().get(i);
            listaMaterialesN[i] = materiales.getNombreM();
            listaCant[i] = materiales.getCant();
        }

        p.setListaMaterialesN(listaMaterialesN);
        p.setListaCant(listaCant);

        if (p.insertar()) {
            
            Alert alerta1 = new Alert(Alert.AlertType.CONFIRMATION);
            alerta1.setHeaderText(null);
            alerta1.setContentText("Insertado correctamente");
            hs.insert("Crear", "El usuario " + login.getUsuarioActual() + " ha realizado un pedido con fecha " + p.getFechaActual(), login.getUsuarioActual());
            Stage stage1 = (Stage) alerta1.getDialogPane().getScene().getWindow();
            stage1.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
            alerta1.showAndWait(); // Esperar a que el usuario cierre la alerta     
            
            p.searchId();
            f.setIdPedido(p.getIdPedido());

            List<String> materialesPorDebajoMinimo = new ArrayList<>();
            for (int i = 0; i < numFilas; i++) {
                pedido material = table.getItems().get(i);
                m.buscarCantMaterial(material.getNombreM()); // Método para obtener la cantidad actual del material desde la base de datos
                System.out.println(m.getCantidad_min());
                if (m.getCantidad() < m.getCantidad_min()) {
                    materialesPorDebajoMinimo.add(material.getNombreM());
                }
            }

            // Si hay materiales por debajo de la cantidad mínima, mostrar alerta
            if (!materialesPorDebajoMinimo.isEmpty()) {
                Alert alertaMateriales = new Alert(Alert.AlertType.INFORMATION);
                alertaMateriales.setHeaderText("Materiales por debajo del mínimo");
                alertaMateriales.setContentText("Los siguientes materiales están por debajo de la cantidad mínima:\n" + 
                    String.join("\n", materialesPorDebajoMinimo));
                Stage stageMateriales = (Stage) alertaMateriales.getDialogPane().getScene().getWindow();
                stageMateriales.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
                alertaMateriales.showAndWait();
            }

            // Mostrar la segunda alerta para generar factura
            Alert alerta2 = new Alert(Alert.AlertType.CONFIRMATION);
            alerta2.setHeaderText(null);
            alerta2.setContentText("¿Desea generar factura del pedido?");
            Stage stage = (Stage) alerta2.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/com/mycompany/inventario/logo_e_corner.png").toExternalForm()));
            ButtonType btnSi = new ButtonType("Sí");
            ButtonType btnNo = new ButtonType("No");
            alerta2.getButtonTypes().setAll(btnSi, btnNo);

            Optional<ButtonType> result = alerta2.showAndWait();
            if (result.get() == btnSi) {
                Factura(event);
            }
            
        } else {
            alert.ShowAlert(Alert.AlertType.ERROR, "Aviso", "No se ha podido insertar correctamente");
        }
        
        CbmMateriales.getSelectionModel().clearSelection();
        cargarMaterial();
        
    }

    @FXML
    private void Limpiar(ActionEvent event) {
        limpiarCampos();
        table.getItems().clear();
        CbmMateriales.setDisable(false);
        btnGuardar.setDisable(false);
        txtNomCliente.setDisable(false);
        
        CbmMateriales.getSelectionModel().clearSelection();
        cargarMaterial();
    }

    @FXML
    private void Agregar(ActionEvent event) {
        try {
            String nombreMaterial = CbmMateriales.getSelectionModel().getSelectedItem();
            if (nombreMaterial == null || TxtCant.getText().isEmpty()) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un material y una cantidad");
                return;
            }
            double cantidad;
            try {
                cantidad = Double.parseDouble(TxtCant.getText());
                if (cantidad <= 0) {
                    alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad debe ser un valor positivo mayor a 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad debe ser un número válido");
                return;
            }

            double precio = p.obtenerPrecioMaterial(nombreMaterial);
            double stockActual = p.obtenerStockActual(nombreMaterial);
            double stockRestante = stockActual - cantidad;
            String unidad = p.obtenerUnidadMedida(nombreMaterial);

            if (stockRestante < 0) {
                alert.ShowAlert(Alert.AlertType.ERROR, "Error", "La cantidad solicitada excede la cantidad del stock");
                return;
            }

            pedido pedidoExistente = table.getItems().stream()
                .filter(p -> p.getNombreM().equals(nombreMaterial))
                .findFirst()
                .orElse(null);

            if (pedidoExistente != null) {
                pedidoExistente.setCant(cantidad);
                pedidoExistente.setStockRestante(stockRestante);
                pedidoExistente.setPrecio(precio);
                pedidoExistente.setUnidad(unidad);
                table.refresh();
            } else {
                pedido nuevoPedido = new pedido(cantidad,nombreMaterial, stockRestante, precio, unidad);
                table.getItems().add(nuevoPedido);
                CbmMateriales.getItems().remove(nombreMaterial);
            }

            CbmMateriales.setDisable(false);
            btnGuardar.setDisable(false);
            TxtCant.clear();
            mostrarDatos();
            calcularSubtotal();

        } catch (NumberFormatException e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error en la entrada de cantidad", e);
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al agregar el pedido", e);
        }
    }

    @FXML
    private void Factura(ActionEvent event) {
        
        if (f.getIdPedido() == null) {
            alert.ShowAlert(Alert.AlertType.ERROR, "Error", "Debe guardar el pedido antes de generar la factura.");
            return;
        }
        
        f.setSubTotal(calcularSubtotal());
        f.setTotal(calcularTotal());
        client.buscarDatosCliente(txtNomCliente.getText());
        f.setIdcliente(client.getId());
        f.insertar();
        f.obtenerNumFac();
        int numFactura = f.getNumFactura();

        try {
            String ubicacion = "/reportes/frameexperts/factura.jasper";
            String titulo = "Factura N~" + numFactura;
            System.out.println(f.getSubTotal());
            System.out.println(f.getTotal());
            JasperPrint jasperPrint = r.generarFactura(ubicacion, titulo, numFactura);
            
            String ruta = rut.obtenerRutaDescargas();
            String relativePath = ruta + "/Factura_" + numFactura + ".pdf";
    
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
            
        } catch (Exception e) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, "Error al generar el reporte", e);
        }
        
        limpiarCampos();
        table.getItems().clear();
        txtNomCliente.setDisable(false);
        btnEliminar.setDisable(true);
        btnGuardar.setDisable(false);
        CbmMateriales.setDisable(false);
        CbmMateriales.getSelectionModel().clearSelection();
        cargarMaterial();
        f.setIdPedido(null);
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
    private void Click(MouseEvent event) {
        pedido seleccionado = table.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            TxtCant.setText(String.valueOf(seleccionado.getCant()));
            CbmMateriales.setValue(seleccionado.getNombreM());
            btnEliminar.setDisable(false);
            btnGuardar.setDisable(true);
            CbmMateriales.setDisable(true);
        }
    }

    private void mostrarDatos() {
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        ColumCantidad.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCantidadConUnidad()));
        ColumStock.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStockRestanteConUnidad()));
    }

    public void cargarMaterial() {
        listaMateriales = FXCollections.observableArrayList(new materia().consulta());
        
        CbmMateriales.getItems().clear();
        
        CbmMateriales.getItems().addAll(
            listaMateriales.stream().map(materia::getNombre).toList()
        );
    }

    private void limpiarCampos() {
        TxtServicio.clear();
        txtNomCliente.clear();
        TxtCant.clear();
        txtCosto.setText("SubTotal:");
        CbmMateriales.getSelectionModel().clearSelection();
    }

    private double calcularSubtotal() {
         double subtotal = table.getItems().stream()
        .mapToDouble(p -> p.getPrecio() * p.getCant())
        .sum();
    
        // Formatear el subtotal a dos decimales
        String subtotalFormatted = String.format("SubTotal: %.2f" + " €", subtotal);
        txtCosto.setText(subtotalFormatted);

        return subtotal;
    }

    private double calcularTotal() {
        double t = calcularSubtotal() + (calcularSubtotal() * 0.23);
        return Math.round(t * 100.0) / 100.0;
    }

    private void configurarColumnas() {
        ColumMaterial.setCellValueFactory(new PropertyValueFactory<>("nombreM"));
        ColumCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadConUnidad"));
        ColumStock.setCellValueFactory(new PropertyValueFactory<>("stockRestanteConUnidad"));
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

    @FXML
    private void NoName(ActionEvent event) {
        
        txtNomCliente.setText("Sin nombre");
        txtNomCliente.setDisable(true);
        
    }   
    
    public void actualizarPedido(Label burbuja) {

        MateriaController materiaC = (MateriaController) App.getController("materia");

        if (materiaC != null) {
           materiaC.verificarStockBajo(burbuja);
        } else {
            System.out.println("Error: No se encontro el controlador de Materia.");
        }
    }

    @FXML
    private void GenerarPedido(ActionEvent event) {
        
        // Visualizar el reporte de materiales
        String ubicacion = "/reportes/frameexperts/Pedidos.jasper";
        String titulo = "Reporde de Pedidos";

        JasperPrint jasperPrint = r.generarReporte(ubicacion, titulo);

        String ruta = rut.obtenerRutaDescargas();
        // Obtener la fecha actual en formato "dd-MM"
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM");
        String fechaFormateada = fechaActual.format(formatoFecha);

        // Crear el nombre del archivo con la fecha incluida
        String relativePath = ruta + "/Reporte de pedidos " + fechaFormateada + ".pdf";

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

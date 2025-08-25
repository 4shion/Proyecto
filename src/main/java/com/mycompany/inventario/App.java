package com.mycompany.inventario;
import com.mycompany.inventario.campos.materia;
import com.mycompany.inventario.clases.alertas;
import com.mycompany.inventario.clases.conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

/**
 * JavaFX App
 */
public class App extends Application {

    //Nat Contra:123
    //Walter Contra:6291291 CodAdmi:321
    
    private static Scene scene;
    alertas alert = new alertas();
    private static Parent root;
    private static Map<String, Parent> loadedViews = new HashMap<>();
    private static Map<String, Object> loadedControllers = new HashMap<>();


    @Override
    public void start(Stage stage) throws IOException {
        conexion conec = new conexion();
        if(conec.getCon() != null){
           
            root = (Pane) loadFXML("main");
            scene = new Scene(root, 1200, 700);
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.setResizable(false);

            // Cargar la imagen del icono
            Image icon = new Image(getClass().getResourceAsStream("logo_e_corner.png"));
            // Establecer el icono de la ventana
            stage.getIcons().add(icon);

            Pane configuracion = new Pane();
            configuracion.setPrefSize(300, 300);
            configuracion.setTranslateX(1200);
            ((Pane) root).getChildren().add(configuracion);
            
            stage.show();   
            
        }
        else{
            
            alert.ShowAlert(Alert.AlertType.ERROR, "Error de conexión", "Por favor, revise la conexión a la base de datos");
            
        }
    }
    
    public static void cargarControllerMateria() {
        try {
            // Cargar la vista de Materia
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/mycompany/inventario/Materia.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            MateriaController materiaController = loader.getController();

            // Almacenar la vista y el controlador en sus respectivas listas
            loadedViews.put("materia", root);
            loadedControllers.put("materia", materiaController);

            System.out.println("Controller de Materia almacenado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar el controlador de Materia: " + e.getMessage());
        }
    }
    
    public static void cargarControllerPedido() {
        try {
            // Cargar la vista de Materia
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/mycompany/inventario/pedido.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            PedidoController pedidoController = loader.getController();

            // Almacenar la vista y el controlador en sus respectivas listas
            loadedViews.put("pedido", root);
            loadedControllers.put("pedido", pedidoController);

            System.out.println("Controller de pedido almacenado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar el controlador de pedido: " + e.getMessage());
        }
    }
    
    public static void cargarControllerGestor() {
        try {
            // Cargar la vista de Materia
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/mycompany/inventario/gestorContra.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            GestorContraController gestorController = loader.getController();

            // Almacenar la vista y el controlador en sus respectivas listas
            loadedViews.put("gestorContra", root);
            loadedControllers.put("gestorContra", gestorController);

            System.out.println("Controller de Gestor de contraseñas almacenado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar el controlador de Controlador de contraseñas: " + e.getMessage());
        }
    }

    public static Scene getScene() {
        return scene;
    }

    static void setRoot(String fxml) throws IOException {
        if (loadedViews.containsKey(fxml)) {
            // Si la vista ya está cargada, la usamos directamente
            scene.setRoot(loadedViews.get(fxml));
        } 
        else {
            // Si no está cargada, la cargamos y almacenamos en el Map
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = loader.load();

            // Almacenar tanto la vista como el controlador
            loadedViews.put(fxml, root);
            loadedControllers.put(fxml, loader.getController());

            scene.setRoot(root);
        }
    }

    public static Object getController(String fxml) {
        return loadedControllers.get(fxml);
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static Map<String, Parent> getLoadedViews() {
        return loadedViews;
    }
    
}

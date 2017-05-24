package panaderiaFinalSim;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import panaderiaFinalSim.Simulacion.ResultadoIteracion;

import java.io.IOException;

/**
 * Created by gaston on 22/05/17.
 */
public class MainApp extends Application{

    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("guiSim.fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Final SIM 24/05/2017 Di Giuseppe");
        stage.setScene(scene);
        stage.show();

        /*ResultadoIteracion resultadoIteracion = new ResultadoIteracion("caledoug", "cant", 24, 72);
        System.out.println(resultadoIteracion.getNombre()+""+resultadoIteracion.getApellido());
        resultadoIteracion.nombreProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable + " "+ oldValue + " "+ newValue);
        });

        resultadoIteracion.setNombre("gaston");
        resultadoIteracion.setNombre("pablo");
        */
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

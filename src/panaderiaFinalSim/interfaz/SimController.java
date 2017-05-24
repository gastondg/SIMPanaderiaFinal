package panaderiaFinalSim.interfaz;

/**
 * Created by gaston on 22/05/17.
 */

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import panaderiaFinalSim.Simulacion.ResultadoIteracion;
import panaderiaFinalSim.Simulacion.Simulacion;
import panaderiaFinalSim.eventos.AtencionCliente;
import panaderiaFinalSim.eventos.LlegadaCliente;
import panaderiaFinalSim.eventos.PedidoCliente;
import panaderiaFinalSim.negocio.Cliente;
import panaderiaFinalSim.negocio.Estadistica;
import panaderiaFinalSim.negocio.Horno;

import java.net.URL;
import java.util.*;

public class SimController implements Initializable{
    @FXML
    private TextField intervaloHornoTxt;

    @FXML
    private TextField finSimulacionTxt;

    @FXML
    private TextField mediaTxt;

    @FXML
    private TextField aTxt;
    //Spinner<Double>
    @FXML
    private TextField bTxt;

    @FXML
    private TextField tempInicialTxt;

    @FXML
    private TextField stockInicialTxt;

    @FXML
    private Button simularBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private TableView<ResultadoIteracion> matrizTable;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // uniformATxt.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,99,0.5,0.1));
        simularBtn.setOnAction(event -> initializeSimulation());


        TableColumn<ResultadoIteracion, Number> relojColumn, randomLlegadaColumn,
                tiempoLlegadaColumn, proximaLlegadaColumn, randomAtencionColumn, tiempoAtencionColumn,
                finDeAtencionColumn, randomPedidoColumn, stockColumn, pedidoColumn, inicioHornoColumn ;
        TableColumn<ResultadoIteracion, String> relojColumn2,eventoColumn;

        /*relojColumn= new TableColumn<>("Reloj");
        relojColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(this.formatTime(p.getValue().getReloj())));
        */
        relojColumn= new TableColumn<>("Reloj");
        relojColumn.setCellValueFactory(p -> new ReadOnlyDoubleWrapper(p.getValue().getReloj()));

        eventoColumn = new TableColumn<>("Evento");
        eventoColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getTipo().toString()));
        randomLlegadaColumn = new TableColumn<>("RNDLlegada");
        randomLlegadaColumn.setCellValueFactory(p -> new ReadOnlyDoubleWrapper(p.getValue().getRandomLlegada()));
        tiempoLlegadaColumn = new TableColumn<>("TiempoLlegada");
        tiempoLlegadaColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getTiempoLlegada()));
        proximaLlegadaColumn = new TableColumn<>("ProximaLlegada");
        proximaLlegadaColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getProximaLlegada()));
        randomAtencionColumn = new TableColumn<>("RNDAtencion");
        randomAtencionColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getRandomAtencion()));
        tiempoAtencionColumn = new TableColumn<>("TiempoAtencion");
        tiempoAtencionColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getTiempoAtencion()));
        finDeAtencionColumn = new TableColumn<>("FinAtencion");
        finDeAtencionColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getFinDeAtencion()));
        randomPedidoColumn = new TableColumn<>("RNDPedido");
        randomPedidoColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getRandomPedido()));
        pedidoColumn = new TableColumn<>("Pedido");
        pedidoColumn.setCellValueFactory(p -> new ReadOnlyIntegerWrapper(p.getValue().getPedido()));
        stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(p -> new ReadOnlyIntegerWrapper(p.getValue().getStock()));
        inicioHornoColumn = new TableColumn<>("InicioHorno");
        inicioHornoColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getInicioHorno()));


        matrizTable.getColumns().addAll(relojColumn, eventoColumn, randomLlegadaColumn, tiempoLlegadaColumn,
                proximaLlegadaColumn, randomAtencionColumn, tiempoAtencionColumn, finDeAtencionColumn,
                randomPedidoColumn, pedidoColumn, stockColumn, inicioHornoColumn);
    }

    /*public String formatTime(double reloj) {
        int s = (int) (reloj*60);
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }*/

    public void initializeSimulation() {
        double media = Double.parseDouble(mediaTxt.getText());
        double a = Double.parseDouble(aTxt.getText());
        double b = Double.parseDouble(bTxt.getText());
        double tempInicialHorno = Double.parseDouble(tempInicialTxt.getText());
        double intervaloHorno = Double.parseDouble(intervaloHornoTxt.getText());
        int stockInicial = Integer.parseInt(stockInicialTxt.getText());
        double finSimulacion = Double.parseDouble(finSimulacionTxt.getText());


        Simulacion simulacion = new Simulacion(a, b, finSimulacion, intervaloHorno, media, tempInicialHorno, stockInicial,
                () -> {showDialog("Fin Simulacion", "Estadistica va aca");},
                (resultadoIteracion) -> matrizTable.getItems().add(resultadoIteracion));
        ResultadoIteracion inicializacion = simulacion.initializeSim();
        matrizTable.getItems().addAll(inicializacion);

        Thread thread = new Thread(simulacion);
        thread.start();
    }

    public void showDialog(String titulo, String contenido) {
        Platform.runLater( ()-> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText(contenido);

            alert.showAndWait();
        });
    }

}

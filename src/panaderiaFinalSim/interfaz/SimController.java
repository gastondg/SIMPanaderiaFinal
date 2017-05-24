package panaderiaFinalSim.interfaz;

/**
 * Created by gaston on 22/05/17.
 */

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import panaderiaFinalSim.Simulacion.ResultadoIteracion;
import panaderiaFinalSim.Simulacion.Simulacion;
import panaderiaFinalSim.negocio.Estadistica;
import java.net.URL;
import java.util.*;

public class SimController implements Initializable{
    @FXML
    private Spinner<Double> intervaloHornoSpinner;

    @FXML
    private Spinner<Double> finSimulacionSpinner;

    @FXML
    private Spinner<Double> mediaSpinner;

    @FXML
    private Spinner<Double> aSpinner;
    //Spinner<Double>
    @FXML
    private Spinner<Double> bSpinner;

    @FXML
    private Spinner<Double> tempInicialSpinner;

    @FXML
    private Spinner<Integer> stockInicialSpinner;

    @FXML
    private Button simularBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private TableView<ResultadoIteracion> matrizTable;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        aSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,99,0.5,0.1));
        bSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,99,1.5,0.1));
        mediaSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1,99,3,0.1));
        tempInicialSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,66,5,0.5));
        stockInicialSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 30, 1));
        finSimulacionSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1,960,480,1));
        intervaloHornoSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1,961,35,0.5));

        bSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue <= aSpinner.getValue()) {
                bSpinner.getValueFactory().setValue(aSpinner.getValue()+0.1);
            }
        });

        aSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue >= bSpinner.getValue() ) {
                bSpinner.getValueFactory().setValue(aSpinner.getValue()+0.1);
            }
        });

        simularBtn.setOnAction(event -> initializeSimulation());
        resetBtn.setOnAction(event -> matrizTable.getItems().clear());

        TableColumn<ResultadoIteracion, Number> randomLlegadaColumn,randomAtencionColumn,randomPedidoColumn, stockColumn, pedidoColumn,
                clientesArriveColumn, clientesGoneColumn, clientesEnColaColumn, productosColumn;
        TableColumn<ResultadoIteracion, String> relojColumn, tiempoLlegadaColumn, proximaLlegadaColumn, proxFinEsperaColumn,
                tiempoAtencionColumn, finDeAtencionColumn, inicioHornoColumn, eventoColumn, finCoccionColumn;
        TableColumn<ResultadoIteracion, Boolean>  estadoHornoColumn;

        relojColumn= new TableColumn<>("Reloj");
        relojColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(this.formatTime(p.getValue().getReloj())));
        eventoColumn = new TableColumn<>("Evento");
        eventoColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getTipo().toString()));
        randomLlegadaColumn = new TableColumn<>("RNDLlegada");
        randomLlegadaColumn.setCellValueFactory(p -> new ReadOnlyDoubleWrapper(p.getValue().getRandomLlegada()));
        tiempoLlegadaColumn = new TableColumn<>("TiempoLlegada");
        tiempoLlegadaColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getTiempoLlegada())));
        proximaLlegadaColumn = new TableColumn<>("ProximaLlegada");
        proximaLlegadaColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getProximaLlegada())));
        randomAtencionColumn = new TableColumn<>("RNDAtencion");
        randomAtencionColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getRandomAtencion()));
        tiempoAtencionColumn = new TableColumn<>("TiempoAtencion");
        tiempoAtencionColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getTiempoAtencion())));
        finDeAtencionColumn = new TableColumn<>("FinAtencion");
        finDeAtencionColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getFinDeAtencion())));
        randomPedidoColumn = new TableColumn<>("RNDPedido");
        randomPedidoColumn.setCellValueFactory(param -> new ReadOnlyDoubleWrapper(param.getValue().getRandomPedido()));
        pedidoColumn = new TableColumn<>("Pedido");
        pedidoColumn.setCellValueFactory(p -> new ReadOnlyIntegerWrapper(p.getValue().getPedido()));
        stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(p -> new ReadOnlyIntegerWrapper(p.getValue().getStock()));
        inicioHornoColumn = new TableColumn<>("InicioHorno");
        inicioHornoColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getInicioHorno())));
        estadoHornoColumn = new TableColumn<>("EstadoHorno");
        estadoHornoColumn.setCellValueFactory(param -> new ReadOnlyBooleanWrapper(param.getValue().getEstadoHorno()));
        productosColumn = new TableColumn<>("ProductosCocinando");
        productosColumn.setCellValueFactory(param -> new ReadOnlyIntegerWrapper(param.getValue().getProductos()));
        finCoccionColumn = new TableColumn<>("FinCoccion");
        finCoccionColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getFinCoccion())));
        clientesArriveColumn = new TableColumn<>("CliLlegaron");
        clientesArriveColumn.setCellValueFactory(param -> new ReadOnlyIntegerWrapper(param.getValue().getClientesArrive()));
        clientesGoneColumn = new TableColumn<>("CliSeFueron");
        clientesGoneColumn.setCellValueFactory(param -> new ReadOnlyIntegerWrapper(param.getValue().getClientesGone()));
        clientesEnColaColumn = new TableColumn<>("CliEnCola");
        clientesEnColaColumn.setCellValueFactory(param -> new ReadOnlyIntegerWrapper(param.getValue().getClientesEnCola()));
        proxFinEsperaColumn = new TableColumn<>("proxFinEspera");
        proxFinEsperaColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(this.formatTime(param.getValue().getFinEsperaCliente())));

        //hacer todas las columnas unsortable

        matrizTable.getColumns().addAll(relojColumn, eventoColumn, randomLlegadaColumn, tiempoLlegadaColumn,
                proximaLlegadaColumn, randomAtencionColumn, tiempoAtencionColumn, finDeAtencionColumn,
                randomPedidoColumn, pedidoColumn, stockColumn, inicioHornoColumn, estadoHornoColumn, productosColumn,
                finCoccionColumn, clientesEnColaColumn, proxFinEsperaColumn, clientesArriveColumn, clientesGoneColumn);

        matrizTable.getColumns().forEach(tableColumn -> {
            tableColumn.setSortable(false);
        });
    }

    public String formatTime(double reloj) {
        int s = (int) (reloj*60);
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }



    public void initializeSimulation() {
        double media = (mediaSpinner.getValue());
        double a = (aSpinner.getValue());
        double b = (bSpinner.getValue());
        double tempInicialHorno = (tempInicialSpinner.getValue());
        double intervaloHorno = (intervaloHornoSpinner.getValue());
        int stockInicial = (stockInicialSpinner.getValue());
        double finSimulacion = (finSimulacionSpinner.getValue());


        Simulacion simulacion = new Simulacion(a, b, finSimulacion, intervaloHorno, media, tempInicialHorno, stockInicial,
                (Estadistica estadistica) -> {
            StringBuilder contenido = new StringBuilder();
            StringBuilder informacion = new StringBuilder();
            contenido.append("Llegaron ").append(estadistica.getClientesLlegaron()).append(" clientes. \n");
            contenido.append("Se fueron ").append(estadistica.getClientesSeFueron()).append(" clientes");
            informacion.append("Se fueron un %").append(estadistica.getPorcentajeSeFueron()).append(" de los clientes que llegaron");
            showDialog("Fin Simulacion", informacion.toString(), contenido.toString());},
            (resultadoIteracion) -> matrizTable.getItems().add(resultadoIteracion));
        ResultadoIteracion inicializacion = simulacion.initializeSim();
        matrizTable.getItems().addAll(inicializacion);

        Thread thread = new Thread(simulacion);
        thread.start();
    }

    public void showDialog(String titulo, String informacion, String contenido) {
        Platform.runLater( ()-> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(informacion);
            alert.setContentText(contenido);

            alert.showAndWait();
        });
    }

}

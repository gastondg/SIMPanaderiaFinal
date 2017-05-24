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

        //hacer todas las columnas un sortable
        relojColumn.setSortable(false);
        eventoColumn.setSortable(false);
        randomLlegadaColumn.setSortable(false);
        tiempoLlegadaColumn.setSortable(false);
        proximaLlegadaColumn.setSortable(false);
        randomAtencionColumn.setSortable(false);
        tiempoAtencionColumn.setSortable(false);
        finDeAtencionColumn.setSortable(false);
        randomPedidoColumn.setSortable(false);
        pedidoColumn.setSortable(false);
        stockColumn.setSortable(false);
        inicioHornoColumn.setSortable(false);
        estadoHornoColumn.setSortable(false);
        productosColumn.setSortable(false);
        finCoccionColumn.setSortable(false);
        clientesArriveColumn.setSortable(false);
        clientesGoneColumn.setSortable(false);
        clientesEnColaColumn.setSortable(false);
        proxFinEsperaColumn.setSortable(false);

        matrizTable.getColumns().addAll(relojColumn, eventoColumn, randomLlegadaColumn, tiempoLlegadaColumn,
                proximaLlegadaColumn, randomAtencionColumn, tiempoAtencionColumn, finDeAtencionColumn,
                randomPedidoColumn, pedidoColumn, stockColumn, inicioHornoColumn, estadoHornoColumn, productosColumn,
                finCoccionColumn, clientesEnColaColumn, proxFinEsperaColumn, clientesArriveColumn, clientesGoneColumn);
    }

    public String formatTime(double reloj) {
        int s = (int) (reloj*60);
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }

    public void initializeSimulation() {
        double media = Double.parseDouble(mediaTxt.getText());
        double a = Double.parseDouble(aTxt.getText());
        double b = Double.parseDouble(bTxt.getText());
        double tempInicialHorno = Double.parseDouble(tempInicialTxt.getText());
        double intervaloHorno = Double.parseDouble(intervaloHornoTxt.getText());
        int stockInicial = Integer.parseInt(stockInicialTxt.getText());
        double finSimulacion = Double.parseDouble(finSimulacionTxt.getText());


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

package panaderiaFinalSim.Simulacion;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by gaston on 22/05/17.
 */
public class ResultadoIteracion {

    EventoSimulacion.TYPE tipo;
    double reloj ;
    int stock, pedido;
    double inicioHorno, randomLlegada, tiempoLlegada, proximaLlegada, randomAtencion, tiempoAtencion, finDeAtencion, randomPedido;

    public ResultadoIteracion(EventoSimulacion.TYPE tipo, double reloj, int stock, int pedido) {
        this.tipo = tipo;
        this.reloj = reloj;
        this.stock=stock;
        this.pedido=pedido;
    }

    public double getInicioHorno() {
        return inicioHorno;
    }

    public double getRandomLlegada() {
        return randomLlegada;
    }

    public double getTiempoLlegada() {
        return tiempoLlegada;
    }

    public double getProximaLlegada() {
        return proximaLlegada;
    }

    public double getRandomAtencion() {
        return randomAtencion;
    }

    public double getTiempoAtencion() {
        return tiempoAtencion;
    }

    public double getFinDeAtencion() {
        return finDeAtencion;
    }

    public double getRandomPedido() {
        return randomPedido;
    }

    public ResultadoIteracion(double reloj, EventoSimulacion.TYPE tipo, double randomLlegada, double tiempoLlegada,
                              double proximaLlegada, double randomAtencion, double tiempoAtencion, double finDeAtencion,
                              double randomPedido, int pedido, int stock, double inicioHorno) {
        this.tipo = tipo;
        this.reloj = reloj;
        this.stock = stock;
        this.pedido = pedido;
        this.inicioHorno = inicioHorno;
        this.randomLlegada = randomLlegada;
        this.tiempoLlegada = tiempoLlegada;
        this.proximaLlegada = proximaLlegada;
        this.randomAtencion = randomAtencion;
        this.tiempoAtencion = tiempoAtencion;
        this.finDeAtencion = finDeAtencion;

        this.randomPedido = randomPedido;
    }

    public int getStock() {
        return stock;
    }

    public int getPedido() {
        return pedido;
    }

    public EventoSimulacion.TYPE getTipo() {
        return tipo;
    }

    public void setTipo(EventoSimulacion.TYPE tipo) {
        this.tipo = tipo;
    }

    public double getReloj() {
        return reloj;
    }

    public void setReloj(double reloj) {
        this.reloj = reloj;
    }

    @Override
    public String toString() {
        return "ResultadoIteracion{" +
                "tipo=" + tipo +
                ", reloj=" + reloj +
                ", stock=" + stock +
                ", pedido=" + pedido +
                '}';
    }
}

package panaderiaFinalSim.negocio;

import panaderiaFinalSim.Simulacion.EventoSimulacion;

/**
 * Created by gaston on 21/05/17.
 */
public class Cliente {

    int id;
    private boolean siendoAtendido;
    private double comienzaEspera;
    private double finEspera;
    private EventoSimulacion eventoSeVa;

    public Cliente(int id) {
        this.id=id;
        this.siendoAtendido = true;
        this.comienzaEspera=0;
        this.finEspera=0;
    }

    public void isSiendoAtendido() {
        this.siendoAtendido = true;
        this.comienzaEspera=0;
        this.finEspera=0;
    }

    public void enEspera(double reloj) {
        siendoAtendido = false;
        this.comienzaEspera = reloj;
        this.finEspera = reloj+5;
    }

    public void finAtencion(){
        this.siendoAtendido = false;
        this.comienzaEspera=0;
        this.finEspera=0;
    }

    public void setSiendoAtendido(boolean siendoAtendido) {
        this.siendoAtendido = siendoAtendido;
    }

    public double getComienzaEspera() {
        return comienzaEspera;
    }

    private void setComienzaEspera(double reloj) {
        this.comienzaEspera = comienzaEspera;
    }

    public double getFinEspera() {
        return finEspera;
    }

    public void setEventoSeVa(EventoSimulacion eventoSeVa) {
        this.eventoSeVa = eventoSeVa;
    }

    public EventoSimulacion getEventoSeVa() {
        return eventoSeVa;
    }
}

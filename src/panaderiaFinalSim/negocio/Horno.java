package panaderiaFinalSim.negocio;

import panaderiaFinalSim.eventos.TiempoTemperatura;

/**
 * Created by gaston on 21/05/17.
 */
public class Horno {
    private double proximaPrendida; //inicia a los 35 minutos de comenzar la simulacion
    private double intervalo;
    private boolean estado; //false = apagado, true= prendido
    private double finCoccion;
    private int stock;

    /*public Horno() {
        this.proximaPrendida = 35;
        this.estado = false;
        this.finCoccion = 0;
    }*/

    public Horno(double intervalo) {
        this.proximaPrendida = intervalo;
        this.intervalo = intervalo;
        this.estado = false;
        this.finCoccion = 0;
    }

    public double getProximaPrendida() {
        return proximaPrendida;
    }

    public void setProximaPrendida(double reloj) {
        this.proximaPrendida = reloj+this.intervalo; //le paso el tiempo actual y le sumo el intervalo(mins) para volver a prenderlo
    }

    public boolean getEstado() {
        return estado;
    }

    public int getStock() {
        return this.stock;
    }

    public void setEstado(boolean estado) {
        this.estado = estado; //cambiar el estado segun prendido/apagado
    }

    public void empezarHorneado(int stockSim, double reloj, double temperaturaInicial) {
        this.estado=true; //prendido
        if (stockSim == 0) {
            finCoccion = reloj + TiempoTemperatura.calcularFinHorno(temperaturaInicial,45);
            this.stock = 45;
        } else {
            finCoccion = reloj + TiempoTemperatura.calcularFinHorno(temperaturaInicial, 30);
            this.stock = 30;
        }
    }

    public double getFinCoccion() {
        return finCoccion;
    }
}

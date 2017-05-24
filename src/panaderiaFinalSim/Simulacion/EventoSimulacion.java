package panaderiaFinalSim.Simulacion;

/**
 * Created by gaston on 23/05/17.
 */
public class EventoSimulacion implements Comparable<EventoSimulacion> {

        private double time;
        private boolean cancelado = false;

        @Override
        public int compareTo(EventoSimulacion o) {
            if (this.getTime() < o.getTime()) {
                return -1;
            } else if (o.getTime() < this.getTime()) {
                return 1;
            } else return 0;
        }

        public enum TYPE {
            INICIO_SIMULACION,
            LLEGADA_CLIENTE,
            CLIENTE_SEVA,
            FIN_ATENCION,
            INICIO_HORNO,
            FIN_HORNO;
        }

        private TYPE t;

        public EventoSimulacion(double time, TYPE t) {
            this.time = time;
            this.t = t;
        }

        public void cancelar() {
            this.cancelado=true;
        }

        public boolean isCancelado() {
            return cancelado;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

        public TYPE getT() {
            return t;
        }

        public void setT(TYPE t) {
            this.t = t;
        }
    }


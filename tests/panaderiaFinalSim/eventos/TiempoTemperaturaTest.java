package panaderiaFinalSim.eventos;

import org.junit.Test;

/**
 * Created by gaston on 21/05/17.
 */
public class TiempoTemperaturaTest {
    @Test
    public void calcularFinHorno() throws Exception {

        TiempoTemperatura tt = new TiempoTemperatura();
        double tiempo = tt.calcularFinHorno(5,30);
        System.out.println(tiempo);
    }

}
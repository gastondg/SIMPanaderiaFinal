package panaderiaFinalSim.eventos;

/**
 * Created by gaston on 21/05/17.
 */
public class TiempoTemperatura {

    //RungeKutta class
    //h es 1 igual a 1 min
    static public double h = 0.5;

    public static double calcularFinHorno(double T, int p) {
        double t=0;
        return calcularFinHorno(t, T, p);
    }
    //dT/dt= -0*4*T + 800/p;

    private static double calcularFinHorno(double tiempo, double Temperature, int p) {
        //calculo k0, k1, k2, k3
        double k0,k1,k2,k3,Tprox;
        double cte=800/p;
        k0= -0.4*Temperature+cte;
        k1= -0.4*(Temperature+(k0*h)/2)+cte;
        k2= -0.4*(Temperature+(k1*h)/2)+cte;
        k3= -0.4*(Temperature+k2*h)+cte;
        Tprox= Temperature+((h/6)*(k0+2*k1+2*k2+k3));

        if((Tprox-Temperature) < 0.01) {
            return (tiempo+18);
        } else {
            return calcularFinHorno(tiempo+h, Tprox, p);
        }
    }
}

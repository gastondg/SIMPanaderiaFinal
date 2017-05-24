package panaderiaFinalSim.eventos;

/**
 * Created by gaston on 21/05/17.
 */
public class AtencionCliente {

    public static double calcularTiempoAtencion(double random, double A, double B) {
        return (A+random*(B-A)); //uniforme (A, B)
    }

    /*public static void main(String[] args) {
        double random= Math.random();
        System.out.println("random "+random);
        System.out.println("FinAtencion "+calcularTiempoAtencion(random, 0.5,1.5));
        double random1= Math.random();
        System.out.println("random "+random1);
        System.out.println("FinAtencion "+calcularLlegada(random,3));
        double random2= Math.random();
        System.out.println("random "+random2);
        System.out.println("FinAtencion "+calcularLlegada(random,3));
        double random3= Math.random();
        System.out.println("random "+random3);
        System.out.println("FinAtencion "+calcularLlegada(random,3));
    }*/
}

package panaderiaFinalSim.eventos;

/**
 * Created by gaston on 21/05/17.
 */
public class LlegadaCliente {

    static public double calcularLlegada(double random, double media) {
        //Exponencial Negativa
        double tiempoLlegada = -media*Math.log(1-random);
        return tiempoLlegada;
    }

   /* public static void main(String[] args) {
        double random= Math.random();
        System.out.println("random "+random);
        System.out.println("Aleatorio Exp Neg "+calcularLlegada(random,3));
        double random1= Math.random();
        System.out.println("random "+random1);
        System.out.println("Aleatorio Exp Neg "+calcularLlegada(random,3));
        double random2= Math.random();
        System.out.println("random "+random2);
        System.out.println("Aleatorio Exp Neg "+calcularLlegada(random,3));
        double random3= Math.random();
        System.out.println("random "+random3);
        System.out.println("Aleatorio Exp Neg "+calcularLlegada(random,3));
    }*/
}

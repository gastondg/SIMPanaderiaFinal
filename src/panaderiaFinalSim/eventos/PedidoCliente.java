package panaderiaFinalSim.eventos;

/**
 * Created by gaston on 21/05/17.
 */
public class PedidoCliente {

    static public int calcularPedido(double random) {

        if (random < 0.25) {
            return 1;
        } else if( random < 0.50) {
            return 2;
        } else if( random < 0.75) {
            return 3;
        } else
            return 4;  //random mayor a 0,75 lleva 4 productos

    }

}

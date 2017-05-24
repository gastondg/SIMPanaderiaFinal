package panaderiaFinalSim.negocio;

/**
 * Created by gaston on 21/05/17.
 */
public class Estadistica {

    public int getClientesLlegaron() {
        return clientesLlegaron;
    }
    public int getClientesSeFueron() {
        return clientesSeFueron;
    }

    public Estadistica() {
        this.clientesLlegaron = 0;
        this.clientesSeFueron = 0;
    }

    private int clientesLlegaron;
    private int clientesSeFueron;

    public void llegaCliente(){
        clientesLlegaron++;
    }

    public void seFueCliente(int stock){
        if (stock == 0) {
            clientesSeFueron++;  //SOLO SE DEBE CONTAR SI SE VAN PORQUE NO HAY STOCK, NO POR ESPERAR ATENCION
        }
        }

    public double getPorcentajeSeFueron() {
        return ((((double)this.clientesSeFueron)/((double)this.clientesLlegaron))*100);
    }


}

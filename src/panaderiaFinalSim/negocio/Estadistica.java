package panaderiaFinalSim.negocio;

/**
 * Created by gaston on 21/05/17.
 */
public class Estadistica {

    public double getClientesLlegaron() {
        return clientesLlegaron;
    }



    public double getClientesSeFueron() {
        return clientesSeFueron;
    }

    public Estadistica() {
        this.clientesLlegaron = 0;
        this.clientesSeFueron = 0;
    }

    private double clientesLlegaron;
    private double clientesSeFueron;

    public void llegaCliente(){
        clientesLlegaron++;
    }

    public void seFueCliente(int stock){
        if (stock == 0) {
            clientesSeFueron++;  //SOLO SE DEBE CONTAR SI SE VAN PORQUE NO HAY STOCK, NO POR ESPERAR ATENCION
        }
        }

    public double getPorcentajeSeFueron() {
        return ((this.clientesSeFueron/this.clientesLlegaron)*100);
    }


}

package panaderiaFinalSim.Simulacion;

/**
 * Created by gaston on 23/05/17.
 */
import panaderiaFinalSim.eventos.AtencionCliente;
import panaderiaFinalSim.eventos.LlegadaCliente;
import panaderiaFinalSim.eventos.PedidoCliente;
import panaderiaFinalSim.negocio.Cliente;
import panaderiaFinalSim.negocio.Estadistica;
import panaderiaFinalSim.negocio.Horno;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Simulacion implements Runnable{

    private final SimualtionEndCallback simulationEndCallback;
    private final SimulationIterationEnd simulationIterationEnd;
    private double a;
    private double b;
    public boolean running = false;
    public boolean finised = false;
    Queue<Cliente> colaClientesEspera, colaClientesAtendidos;
    Queue<EventoSimulacion> colaEventos;
    Estadistica estadistica;
    private EventoSimulacion proximoEventoHorno;

    Horno horno;
    private double reloj, media, temperaturaInicial, inicioHorno, finSimulacion, tiempoLlegada, proximaLlegada, tiempoAtencion, finDeAtencion, randomLlegada, randomAtencion, randomPedido;
    int  stock, pedido, i, numIteracion;

    public Simulacion(double a, double b, double finSimulacion, double intervaloHorno, double media,
                      double temperaturaInicial, int stock, SimualtionEndCallback callback,
                      SimulationIterationEnd simulationIterationEnd) {
        this.simulationIterationEnd = simulationIterationEnd;
        this.i=1;
        this.a = a;
        this.b = b;
        this.stock = stock;
        this.temperaturaInicial = temperaturaInicial;
        this.finSimulacion = finSimulacion;
        this.media = media;
        this.colaEventos = new PriorityQueue<>();
        this.colaClientesEspera = new LinkedList<>();
        this.simulationEndCallback = callback;
        this.reloj = 0;
        this.randomLlegada= Math.random();
        this.tiempoLlegada = LlegadaCliente.calcularLlegada(randomLlegada,media);
        this.proximaLlegada = tiempoLlegada+reloj;
        this.horno = new Horno(intervaloHorno);
        this.inicioHorno = horno.getProximaPrendida();
        this.estadistica = new Estadistica();

    }

    public ResultadoIteracion initializeSim() {
        colaEventos.add(new EventoSimulacion(proximaLlegada, EventoSimulacion.TYPE.LLEGADA_CLIENTE));
        proximoEventoHorno = new EventoSimulacion(inicioHorno, EventoSimulacion.TYPE.INICIO_HORNO);
        colaEventos.add(proximoEventoHorno);
        running = true;
        return (new ResultadoIteracion(0, EventoSimulacion.TYPE.INICIO_SIMULACION, randomLlegada, tiempoLlegada, proximaLlegada,
                0,0,0,0,0,stock,inicioHorno, false,0,
                0,0,0,0,0));
    }

    public void nextIteration(){

        if(reloj<finSimulacion) {

            EventoSimulacion evt = colaEventos.poll();
            System.out.println("next iteration "+ evt.getT().toString());
            reloj = evt.getTime();
            if (evt.isCancelado()) {
                System.out.println("Evento " + evt.getT().toString() + " CANCELADO");
                return;
            }
            switch (evt.getT()){
                case LLEGADA_CLIENTE:
                    estadistica.llegaCliente();
                    Cliente cliente = new Cliente(i);
                    i++;

                    if (colaClientesEspera.isEmpty() && finDeAtencion==0 && stock > 0) {//no hay clientes esperando;
                        atenderCliente(cliente);
                    } else { //hay clientes esperando atencion, va a la cola.
                        cliente.enEspera(reloj);
                        EventoSimulacion eventoSeVa = new EventoSimulacion(cliente.getFinEspera(), EventoSimulacion.TYPE.CLIENTE_SE_VA);
                        colaClientesEspera.add(cliente);
                        cliente.setEventoSeVa(eventoSeVa);
                        colaEventos.add(eventoSeVa);
                        this.ponerRndmACero();
                    }
                    //calculo prox llegada
                    randomLlegada = Math.random();
                    tiempoLlegada = LlegadaCliente.calcularLlegada(randomLlegada, media);
                    proximaLlegada = reloj+tiempoLlegada;
                    colaEventos.add(new EventoSimulacion(proximaLlegada, EventoSimulacion.TYPE.LLEGADA_CLIENTE ));

                    break;
                case FIN_ATENCION:
                    this.ponerRndmACero();
                    this.finDeAtencion = 0;
                    if (!colaClientesEspera.isEmpty() && stock > 0) {
                        //this.ponerRndmACero();
                        Cliente nuevo = colaClientesEspera.poll(); //se van agregando en orden, por ende, saco la cabeza y lo borro
                        //sacar evento se va de la cola
                        atenderCliente(nuevo);
                    } else if (stock > 0) { //no hay clientes esperando
                        this.finDeAtencion = 0;
                        //this.ponerRndmACero();
                    } //else HAY EN LA COLA PERO NO HAY STOCK, AVANZO AL SIGUIENTE EVENTO
                    break;
                case CLIENTE_SE_VA:
                    estadistica.seFueCliente(stock);
                    System.out.println("se va el cliente, stock: " + stock);
                    //el primer elemento de los clientes en espera debe ser el que se va
                    colaClientesEspera.remove(); //saco el primero en la lista
                    //debo agregarlo a la lista de clientes atendidos?
                    break;
                case INICIO_HORNO:
                    encenderHorno();
                    break;
                case FIN_HORNO:
                    stock += horno.getStock();
                    horno.finHorno(reloj);
                    inicioHorno = horno.getProximaPrendida();
                    proximoEventoHorno = new EventoSimulacion(inicioHorno, EventoSimulacion.TYPE.INICIO_HORNO);
                    colaEventos.add(proximoEventoHorno);
                    if (!colaClientesEspera.isEmpty()) {
                        Cliente nuevo = colaClientesEspera.poll();
                        this.atenderCliente(nuevo);
                    }

                    break;
                }

            int clientesEsperando = 0;
            double finEspera = 0;
            boolean estadoHorno = horno.getEstado();
            if (!colaClientesEspera.isEmpty()) {
                finEspera = colaClientesEspera.peek().getFinEspera();
                clientesEsperando = colaClientesEspera.size();
            }
            simulationIterationEnd.ended(new ResultadoIteracion(reloj, evt.getT(), randomLlegada,tiempoLlegada, proximaLlegada,
                    randomAtencion, tiempoAtencion,finDeAtencion,randomPedido,pedido, stock, inicioHorno, estadoHorno,
                    horno.getStock(), horno.getFinCoccion(),finEspera, clientesEsperando,
                    estadistica.getClientesLlegaron(), estadistica.getClientesSeFueron()));


        } else {
            System.out.println("simulation ended");
            System.out.println("Se fueron "+estadistica.getClientesSeFueron()+ " clientes");
            System.out.println("Llegaron "+estadistica.getClientesLlegaron()+ " clientes");
            System.out.println("Total clientes que se fueron : %" + estadistica.getPorcentajeSeFueron());
            running = false;
            finised = true;
            simulationEndCallback.ended();
        }

        }


    @Override
    public void run() {
        while (true) {
            if (running) {
                nextIteration();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface SimualtionEndCallback {
        void ended();
    }

    public interface SimulationIterationEnd {
        void ended(ResultadoIteracion resultadoIteracion);
    }

        private void atenderCliente(Cliente cliente) {
            //se atiende al cliente
                //calculo cant productos que lleva
                randomPedido = Math.random();
                pedido = PedidoCliente.calcularPedido(randomPedido);
                if (cliente.getEventoSeVa() != null) {
                        cliente.getEventoSeVa().cancelar();
                }
                if ( (stock-pedido)<=0 ) { //el stock se hace 0 pero al cliente se le vendieron los productos que quedaban
                stock = 0;
                if (!horno.getEstado()) { //horno apagado, prenderlo
                    encenderHorno();
                        }
                    } else { stock -= pedido; }
                //calculo fin de atencion
                randomAtencion = Math.random();
                tiempoAtencion = AtencionCliente.calcularTiempoAtencion(randomAtencion, a, b);
                finDeAtencion = reloj+tiempoAtencion;
                colaEventos.add(new EventoSimulacion(finDeAtencion, EventoSimulacion.TYPE.FIN_ATENCION));
        }


    private void encenderHorno() {
        horno.empezarHorneado(stock, reloj, temperaturaInicial);
        // HAY QUE SACAR DE LA COLA DE EVENTOS, EL EVENTO DE INICIO DE HORNO
        if (proximoEventoHorno != null) {
            proximoEventoHorno.cancelar();
        }
        //agrego fin de horno a la cola de eventos
        colaEventos.add(new EventoSimulacion(horno.getFinCoccion(), EventoSimulacion.TYPE.FIN_HORNO));
        this.inicioHorno=0;
    }

    public void ponerRndmACero () {

        this.randomAtencion = 0;
        this.randomPedido = 0;
        this.randomLlegada = 0;
        this.tiempoLlegada = 0;
        this.tiempoAtencion = 0;
        this.pedido=0;
    }


    public static void main(String[] args) {
        // write your code here
        Simulacion sim = new Simulacion(0.5, 1.5, 960,35,3,5,30, () -> {
            System.out.printf("Simulation ended on main thread... exiting...");
            //MOSTRAR ESTADISTICAS
        }, resultadoIteracion -> {
            System.out.println(resultadoIteracion);
        });
        Thread t = new Thread(sim);
        t.start();
    }



}
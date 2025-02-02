package Actividades.Actividad_16;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloServidorAdivina extends Thread {
    private Socket socket;
    private int idJugador;
    private ObjetoCompartido objetoCompartido;

    public HiloServidorAdivina(Socket socket, int idJugador, ObjetoCompartido objetoCompartido) {
        this.socket = socket;
        this.idJugador = idJugador;
        this.objetoCompartido = objetoCompartido;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Datos datos = new Datos(idJugador, "Bienvenido jugador " + idJugador, 0, false, -1);
            out.writeObject(datos);

            while (!objetoCompartido.isAcabado() && datos.getIntentos() < 5) {
                datos = (Datos) in.readObject();
                String resultado = objetoCompartido.nuevaJugada(idJugador, Integer.parseInt(datos.getCadena()));
                datos.setCadena(resultado);
                datos.setIntentos(datos.getIntentos() + 1);
                datos.setAcabado(objetoCompartido.isAcabado());
                datos.setGanador(objetoCompartido.getGanador());
                out.writeObject(datos);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package Actividades.Actividad_16;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAdivina {
    private static final int PUERTO = 12345;
    private static final int NUMERO_A_ADIVINAR = 42;

    public static void main(String[] args) {
        ObjetoCompartido objetoCompartido = new ObjetoCompartido(NUMERO_A_ADIVINAR);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            int idJugador = 1;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Jugador " + idJugador + " conectado.");
                new HiloServidorAdivina(socket, idJugador, objetoCompartido).start();
                idJugador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
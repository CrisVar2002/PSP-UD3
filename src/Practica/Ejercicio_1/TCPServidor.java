// TCPServidor.java
package Practica.Ejercicio_1;

import java.io.*;
import java.net.*;

public class TCPServidor {
    public static void main(String[] args) throws IOException {
        int numeroPuerto = 6000; // Puerto
        ServerSocket servidor = new ServerSocket(numeroPuerto);
        System.out.println("Esperando al cliente.....");
        Socket clienteConectado = servidor.accept();

        // CREO FLUJO DE ENTRADA DEL CLIENTE
        DataInputStream flujoEntrada = new DataInputStream(clienteConectado.getInputStream());

        // CREO FLUJO DE SALIDA AL CLIENTE
        DataOutputStream flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());

        String mensajeCliente;
        do {
            // EL CLIENTE ME ENVIA UN MENSAJE
            mensajeCliente = flujoEntrada.readUTF();
            System.out.println("Recibiendo del CLIENTE: " + mensajeCliente);

            if (!mensajeCliente.equals("*")) {
                // ENVIO EL NÚMERO DE CARACTERES AL CLIENTE
                int longitud = mensajeCliente.length();
                flujoSalida.writeUTF("Numero de caracteres: " + longitud);
            }
        } while (!mensajeCliente.equals("*"));

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        clienteConectado.close();
        servidor.close();
    }
}
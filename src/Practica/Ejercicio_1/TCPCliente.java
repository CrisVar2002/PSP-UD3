// TCPCliente.java
package Practica.Ejercicio_1;

import java.io.*;
import java.net.*;

public class TCPCliente {
    public static void main(String[] args) {
        String Host = "localhost";
        int Puerto = 6000; // puerto remoto

        System.out.println("PROGRAMA CLIENTE INICIADO....");

        try (Socket Cliente = new Socket(Host, Puerto);
             DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());
             DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            String cadena;
            do {
                System.out.println("Introduce cadena:");
                cadena = in.readLine();
                flujoSalida.writeUTF(cadena); // Enviar mensaje al servidor

                if (!cadena.trim().equals("*")) {
                    // Recibir respuesta del servidor
                    String respuesta = flujoEntrada.readUTF();
                    System.out.println(" => " + respuesta);
                }
            } while (!cadena.trim().equals("*"));

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package Actividades.Actividad_16;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class JugadorAdivina {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            Datos datos = (Datos) in.readObject();
            System.out.println(datos.getCadena());

            while (!datos.isAcabado() && datos.getIntentos() < 5) {
                System.out.print("Introduce un numero: ");
                String numero = scanner.nextLine();
                datos.setCadena(numero);
                out.writeObject(datos);

                datos = (Datos) in.readObject();
                System.out.println(datos.getCadena());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package Practica.Ejercicio_4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPCliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            int clientId = ois.readInt();
            System.out.println("PROGRAMA CLIENTE INICIADO ...");

            while (true) {
                System.out.print("Introduce idprofesor: ");
                String idprofesor = scanner.nextLine();
                if (idprofesor.equals("*")) {
                    break;
                }

                System.out.println("Cliente conectado con ID: " + clientId);

                out.writeUTF(idprofesor);
                out.flush();

                Profesor profesor = (Profesor) ois.readObject();

                System.out.println("Nombre: " + profesor.getNombre() + ", Especialidad: " + profesor.getEspecialidad().getId() + " - " + profesor.getEspecialidad().getNombreespe());
                for (Asignatura asignatura : profesor.getAsignaturas()) {
                    System.out.println("Asignatura: " + asignatura.getId() + " - " + asignatura.getNombreasig());
                }
            }

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
package Practica.Ejercicio_4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Servidor iniciado ...");

            Profesor[] profesores = new Profesor[5];
            profesores[0] = new Profesor(1, "Jose Ignacio", new Asignatura[]{new Asignatura(2, "ADAT"), new Asignatura(3, "PSP"), new Asignatura(4, "PMD")}, new Especialidad(1, "INFORMÁTICA"));
            profesores[1] = new Profesor(2, "Ana Maria", new Asignatura[]{new Asignatura(5, "MATH"), new Asignatura(6, "PHYS"), new Asignatura(7, "CHEM")}, new Especialidad(2, "CIENCIAS"));
            profesores[2] = new Profesor(3, "Luis Alberto", new Asignatura[]{new Asignatura(8, "HIST"), new Asignatura(9, "GEO"), new Asignatura(10, "SOC")}, new Especialidad(3, "HUMANIDADES"));
            profesores[3] = new Profesor(4, "Marta Lopez", new Asignatura[]{new Asignatura(11, "BIO"), new Asignatura(12, "ECO"), new Asignatura(13, "PSY")}, new Especialidad(4, "CIENCIAS SOCIALES"));
            profesores[4] = new Profesor(5, "Carlos Perez", new Asignatura[]{new Asignatura(14, "ENG"), new Asignatura(15, "LIT"), new Asignatura(16, "ART")}, new Especialidad(5, "ARTES"));

            int clientId = 1;

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente " + clientId + " conectado");

                new Thread(new ClientHandler(clientSocket, profesores, clientId)).start();
                clientId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Profesor[] profesores;
    private int clientId;

    public ClientHandler(Socket clientSocket, Profesor[] profesores, int clientId) {
        this.clientSocket = clientSocket;
        this.profesores = profesores;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeInt(clientId);
            out.flush();
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            while (true) {
                try {
                    String idprofesor = in.readUTF();
                    if (idprofesor.equals("*")) {
                        System.out.println("EL CLIENTE " + clientId + " HA FINALIZADO");
                        break;
                    }

                    System.out.println("Consultando id: " + idprofesor + ", solicitado por cliente: " + clientId);

                    Profesor profesor = null;
                    for (Profesor p : profesores) {
                        if (String.valueOf(p.getIdprofesor()).equals(idprofesor)) {
                            profesor = p;
                            break;
                        }
                    }

                    if (profesor == null) {
                        profesor = new Profesor(0, "No existe", new Asignatura[0], new Especialidad(0, "No existe"));
                    }

                    out.writeObject(profesor);
                    out.flush();
                } catch (EOFException | SocketException e) {
                    System.out.println("Cliente " + clientId + " desconectado inesperadamente");
                    break;
                }
            }

            System.out.println("FIN CON: " + clientSocket + " DEL CLIENTE: " + clientId);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
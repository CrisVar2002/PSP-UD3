package Practica.Ejercicio_5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(44444);
            System.out.println("Servidor iniciando ...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("=>Conecta IP " + clientSocket.getInetAddress() + ", Puerto remoto: " + clientSocket.getPort());

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("*")) {
                    System.out.println("=>Desconecta IP " + clientSocket.getInetAddress() + ", Puerto remoto: " + clientSocket.getPort());
                    break;
                }
                out.println(inputLine.toUpperCase());
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
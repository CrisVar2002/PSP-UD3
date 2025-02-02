package Practica.Ejercicio_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TCPServidor {
    private static final int PORT = 44444;
    private static final int MAX_ATTEMPTS = 10;
    private static final int COMBINATION_LENGTH = 4;
    private static int[] combination;
    private static boolean gameWon = false;
    private static int clientIdCounter = 1;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            combination = generateCombination();
            System.out.println("Servidor iniciado ...");
            System.out.println("Combinación de Números: " + combinationToString(combination));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Jugador " + clientIdCounter + " conectado");

                new Thread(new ClientHandler(clientSocket, clientIdCounter++)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateCombination() {
        Random random = new Random();
        Set<Integer> digits = new HashSet<>();
        while (digits.size() < COMBINATION_LENGTH) {
            digits.add(random.nextInt(10));
        }
        int[] combination = new int[COMBINATION_LENGTH];
        int index = 0;
        for (int digit : digits) {
            combination[index++] = digit;
        }
        return combination;
    }

    private static String combinationToString(int[] combination) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < combination.length; i++) {
            sb.append(combination[i]);
            if (i < combination.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private int clientId;
        private int attempts;

        public ClientHandler(Socket clientSocket, int clientId) {
            this.clientSocket = clientSocket;
            this.clientId = clientId;
            this.attempts = 0;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                out.writeInt(clientId);
                out.writeBoolean(gameWon);
                out.flush();

                if (gameWon) {
                    clientSocket.close();
                    return;
                }

                while (attempts < MAX_ATTEMPTS && !gameWon) {
                    int[] guess = (int[]) in.readObject();
                    attempts++;

                    int aciertos = 0;
                    int coincidencias = 0;
                    boolean[] checked = new boolean[COMBINATION_LENGTH];

                    for (int i = 0; i < COMBINATION_LENGTH; i++) {
                        if (guess[i] == combination[i]) {
                            aciertos++;
                            checked[i] = true;
                        }
                    }

                    for (int i = 0; i < COMBINATION_LENGTH; i++) {
                        if (guess[i] != combination[i]) {
                            for (int j = 0; j < COMBINATION_LENGTH; j++) {
                                if (!checked[j] && guess[i] == combination[j]) {
                                    coincidencias++;
                                    checked[j] = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (aciertos == COMBINATION_LENGTH) {
                        gameWon = true;
                        System.out.println("Jugador " + clientId + " ha ganado el juego!");
                    }

                    out.writeInt(aciertos);
                    out.writeInt(coincidencias);
                    out.writeBoolean(gameWon);
                    out.flush();

                    if (gameWon || attempts >= MAX_ATTEMPTS) {
                        System.out.println("==>Desconectando a ID Jugador: " + clientId);
                        clientSocket.close();
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
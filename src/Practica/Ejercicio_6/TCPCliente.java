package Practica.Ejercicio_6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TCPCliente {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 44444;

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static int clientId;
    private static boolean gameWon;
    private static int attemptsLeft = 10;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cliente Mastermind");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JTextField[] inputFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            inputFields[i] = new JTextField(2);
        }

        JTextArea resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        JTextArea muertosArea = new JTextArea(5, 20);
        muertosArea.setEditable(false);

        JTextArea heridosArea = new JTextArea(5, 20);
        heridosArea.setEditable(false);

        JButton sendButton = new JButton("Enviar");
        JButton clearButton = new JButton("Limpiar");
        JButton exitButton = new JButton("Salir");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel();
        idPanel.add(new JLabel("ID del Cliente:"));
        JTextField clientIdField = new JTextField(5);
        clientIdField.setEditable(false);
        idPanel.add(clientIdField);
        panel.add(idPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Ingrese números:"));
        for (JTextField inputField : inputFields) {
            inputPanel.add(inputField);
        }
        panel.add(inputPanel);

        JPanel attemptsPanel = new JPanel();
        attemptsPanel.add(new JLabel("Intentos restantes:"));
        JTextField attemptsField = new JTextField(2);
        attemptsField.setEditable(false);
        attemptsField.setText(String.valueOf(attemptsLeft));
        attemptsPanel.add(attemptsField);
        panel.add(attemptsPanel);

        panel.add(sendButton);
        panel.add(clearButton);
        panel.add(exitButton);

        panel.add(new JLabel("Resultados:"));
        panel.add(new JScrollPane(resultArea));

        panel.add(new JLabel("Muertos:"));
        panel.add(new JScrollPane(muertosArea));

        panel.add(new JLabel("Heridos:"));
        panel.add(new JScrollPane(heridosArea));

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            clientId = in.readInt();
            gameWon = in.readBoolean();
            clientIdField.setText(String.valueOf(clientId));

            if (gameWon) {
                resultArea.append("El juego ya ha sido ganado por otro jugador.\n");
                socket.close();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] guess = new int[4];
                    for (int i = 0; i < 4; i++) {
                        guess[i] = Integer.parseInt(inputFields[i].getText());
                    }

                    out.writeObject(guess);
                    out.flush();

                    int aciertos = in.readInt();
                    int coincidencias = in.readInt();
                    gameWon = in.readBoolean();
                    attemptsLeft--;

                    resultArea.append("Aciertos: " + aciertos + ", Coincidencias: " + coincidencias + "\n");
                    muertosArea.append("Numeros " + java.util.Arrays.toString(guess) + " => Aciertos: " + aciertos + "\n");
                    heridosArea.append("Numeros " + java.util.Arrays.toString(guess) + " => Coincidencias: " + coincidencias + "\n");
                    attemptsField.setText(String.valueOf(attemptsLeft));

                    if (gameWon) {
                        resultArea.append("¡Has ganado el juego!\n");
                        sendButton.setEnabled(false);
                    } else if (attemptsLeft == 0) {
                        resultArea.append("Se han agotado los intentos.\n");
                        sendButton.setEnabled(false);
                    }
                } catch (IOException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JTextField inputField : inputFields) {
                    inputField.setText("");
                }
                resultArea.setText("");
                muertosArea.setText("");
                heridosArea.setText("");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}
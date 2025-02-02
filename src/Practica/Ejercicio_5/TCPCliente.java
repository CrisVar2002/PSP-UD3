package Practica.Ejercicio_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TCPCliente {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cliente TCP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JTextField inputField = new JTextField(20);
        JTextArea outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);

        JButton sendButton = new JButton("Enviar");
        JButton clearButton = new JButton("Limpiar");
        JButton exitButton = new JButton("Salir");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Ingrese texto:"));
        panel.add(inputField);
        panel.add(sendButton);
        panel.add(clearButton);
        panel.add(exitButton);
        panel.add(new JScrollPane(outputArea));

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        try {
            socket = new Socket("localhost", 44444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    out.println(text);
                    try {
                        String response = in.readLine();
                        outputArea.append(response + "\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                outputArea.setText("");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("*");
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
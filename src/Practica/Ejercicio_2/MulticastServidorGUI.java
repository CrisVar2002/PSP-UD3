package Practica.Ejercicio_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServidorGUI extends JFrame {
    private JTextField messageField;
    private JTextArea messageArea;
    private JButton sendButton;
    private JButton exitButton;
    private MulticastSocket socket;
    private InetAddress group;
    private int port = 5000;

    public MulticastServidorGUI() {
        setTitle("Multicast Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageField = new JTextField();
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        sendButton = new JButton("Enviar");
        exitButton = new JButton("Salir");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        try {
            socket = new MulticastSocket();
            group = InetAddress.getByName("230.0.0.0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            try {
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);
                messageArea.append("Enviado: " + message + "\n");
                messageField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exit() {
        socket.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MulticastServidorGUI().setVisible(true);
            }
        });
    }
}
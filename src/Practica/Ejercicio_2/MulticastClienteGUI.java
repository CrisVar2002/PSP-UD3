package Practica.Ejercicio_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClienteGUI extends JFrame {
    private JTextArea messageArea;
    private JButton exitButton;
    private MulticastSocket socket;
    private InetAddress group;
    private int port = 5000;

    public MulticastClienteGUI(String userName) {
        setTitle("Multicast Client - " + userName);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        exitButton = new JButton("Salir");

        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    receiveMessages();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessages() {
        byte[] buffer = new byte[256];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                messageArea.append(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void exit() {
        try {
            socket.leaveGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        String userName = JOptionPane.showInputDialog("Ingrese su nombre:");
        if (userName != null && !userName.trim().isEmpty()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MulticastClienteGUI(userName).setVisible(true);
                }
            });
        }
    }
}
// UDPCliente.java
package Practica.Ejercicio_3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class UDPCliente {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Introduce idalumno: ");
                String idalumno = scanner.nextLine();
                if (idalumno.equals("*")) {
                    break;
                }

                byte[] sendData = idalumno.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Alumno alumno = (Alumno) ois.readObject();

                System.out.println("ID Alumno: " + alumno.getIdalumno());
                System.out.println("Nombre: " + alumno.getNombre());
                System.out.println("Curso ID: " + alumno.getCurso().getId());
                System.out.println("Curso Descripción: " + alumno.getCurso().getDescripcion());
                System.out.println("Nota: " + alumno.getNota());
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
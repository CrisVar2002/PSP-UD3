// UDPServidor.java
package Practica.Ejercicio_3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UDPServidor {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            Alumno[] alumnos = new Alumno[5];
            alumnos[0] = new Alumno("1", "Juan", new Curso("101", "Matemáticas"), 85);
            alumnos[1] = new Alumno("2", "Ana", new Curso("102", "Física"), 90);
            alumnos[2] = new Alumno("3", "Luis", new Curso("103", "Química"), 75);
            alumnos[3] = new Alumno("4", "Marta", new Curso("104", "Biología"), 80);
            alumnos[4] = new Alumno("5", "Carlos", new Curso("105", "Historia"), 95);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String idalumno = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Identificador solicitado: " + idalumno);

                Alumno alumno = null;
                for (Alumno a : alumnos) {
                    if (a.getIdalumno().equals(idalumno)) {
                        alumno = a;
                        break;
                    }
                }

                if (alumno == null) {
                    alumno = new Alumno("0", "No existe", new Curso("0", "No existe"), 0);
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(alumno);
                oos.flush();
                byte[] sendData = baos.toByteArray();

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
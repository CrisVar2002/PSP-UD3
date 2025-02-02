package Actividades.Actividad_16;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JugadorAdivinaGUI extends JFrame {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;

    private JTextField numeroField;
    private JTextArea resultadoArea;
    private JLabel intentosLabel;
    private int intentos;
    private Datos datos;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public JugadorAdivinaGUI() {
        setTitle("Adivina el Número");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Número:"));
        numeroField = new JTextField();
        panel.add(numeroField);

        panel.add(new JLabel("Intentos:"));
        intentosLabel = new JLabel("0");
        panel.add(intentosLabel);

        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new EnviarButtonListener());
        panel.add(enviarButton);

        add(panel, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        conectarAlServidor();
    }

    private void conectarAlServidor() {
        try {
            Socket socket = new Socket(HOST, PUERTO);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            datos = (Datos) in.readObject();
            resultadoArea.append(datos.getCadena() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class EnviarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String numero = numeroField.getText();
                datos.setCadena(numero);
                out.writeObject(datos);

                datos = (Datos) in.readObject();
                resultadoArea.append(datos.getCadena() + "\n");
                intentos = datos.getIntentos();
                intentosLabel.setText(String.valueOf(intentos));

                if (datos.isAcabado() || intentos >= 5) {
                    numeroField.setEnabled(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JugadorAdivinaGUI gui = new JugadorAdivinaGUI();
            gui.setVisible(true);
        });
    }
}
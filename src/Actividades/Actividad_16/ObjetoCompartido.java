package Actividades.Actividad_16;

public class ObjetoCompartido {
    private int numeroAdivinar;
    private boolean acabado;
    private int ganador;

    public ObjetoCompartido(int numeroAdivinar) {
        this.numeroAdivinar = numeroAdivinar;
        this.acabado = false;
        this.ganador = -1;
    }

    public synchronized String nuevaJugada(int idJugador, int numero) {
        if (acabado) {
            return "El juego ya ha terminado. El ganador es el jugador " + ganador;
        }

        if (numero == numeroAdivinar) {
            acabado = true;
            ganador = idJugador;
            return "¡Correcto! El jugador " + idJugador + " ha adivinado el número.";
        } else if (numero < numeroAdivinar) {
            return "El número es mayor.";
        } else {
            return "El número es menor.";
        }
    }

    public boolean isAcabado() {
        return acabado;
    }

    public int getGanador() {
        return ganador;
    }
}

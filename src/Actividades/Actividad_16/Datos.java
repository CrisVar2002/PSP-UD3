package Actividades.Actividad_16;

import java.io.Serializable;

public class Datos implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String cadena;
    private int intentos;
    private boolean acabado;
    private int ganador;

    public Datos(int id, String cadena, int intentos, boolean acabado, int ganador) {
        this.id = id;
        this.cadena = cadena;
        this.intentos = intentos;
        this.acabado = acabado;
        this.ganador = ganador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public boolean isAcabado() {
        return acabado;
    }

    public void setAcabado(boolean acabado) {
        this.acabado = acabado;
    }

    public int getGanador() {
        return ganador;
    }

    public void setGanador(int ganador) {
        this.ganador = ganador;
    }
}
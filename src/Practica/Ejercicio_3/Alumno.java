// Alumno.java
package Practica.Ejercicio_3;

import java.io.Serializable;

public class Alumno implements Serializable {
    private String idalumno;
    private String nombre;
    private Curso curso;
    private int nota;

    public Alumno(String idalumno, String nombre, Curso curso, int nota) {
        this.idalumno = idalumno;
        this.nombre = nombre;
        this.curso = curso;
        this.nota = nota;
    }

    public String getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(String idalumno) {
        this.idalumno = idalumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
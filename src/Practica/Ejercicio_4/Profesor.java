package Practica.Ejercicio_4;

import java.io.Serializable;

public class Profesor implements Serializable {
    private int idprofesor;
    private String nombre;
    private Asignatura[] asignaturas;
    private Especialidad especialidad;

    public Profesor(int idprofesor, String nombre, Asignatura[] asignaturas, Especialidad especialidad) {
        this.idprofesor = idprofesor;
        this.nombre = nombre;
        this.asignaturas = asignaturas;
        this.especialidad = especialidad;
    }

    public int getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(int idprofesor) {
        this.idprofesor = idprofesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Asignatura[] getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Asignatura[] asignaturas) {
        this.asignaturas = asignaturas;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
}
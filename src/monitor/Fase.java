package monitor;

import java.io.Serializable;

public class Fase implements Serializable {
    private String nombre;
    private int dia;

    public Fase(String nombre){
        this.nombre = nombre;
        dia = 0;
    }

    public Fase(String nombre, int dia) {
        this.nombre = nombre;
        this.dia = dia;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}

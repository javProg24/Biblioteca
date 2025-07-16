package main.java.Models;

import lombok.*;

public class Ejemplar implements Entidad{
    private int ID;
    private String Codigo_Interno;
    private boolean Estado;
    private int IDLibro;

    public Ejemplar() {
        this.ID = 0;
        this.Codigo_Interno = "";
        this.Estado = false; // Por defecto, el ejemplar está disponible
        this.IDLibro = 0;
    }

    public Ejemplar(int ID, String codigo, boolean estado, int IDLibro) {
        this.ID = ID;
        Codigo_Interno = codigo;
        Estado = estado;
        this.IDLibro = IDLibro;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCodigo_Interno() {
        return Codigo_Interno;
    }

    public void setCodigo_Interno(String codigo_Interno) {
        Codigo_Interno = codigo_Interno;
    }

    public boolean getEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public int getIDLibro() {
        return IDLibro;
    }

    public void setIDLibro(int IDLibro) {
        this.IDLibro = IDLibro;
    }
}

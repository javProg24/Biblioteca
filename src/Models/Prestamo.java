package Models;

import java.util.Date;

public class Prestamo implements Entidad{
    private int ID;
    private Date FechaPrestamo;
    private Date FechaDevolucion;
    private boolean Estado;
    private int ID_Usuario;
    private int ID_Ejemplar;
    public Prestamo(){

    }

    public Prestamo(int ID, Date fechaPrestamo, Date fechaDevolucion, boolean estado, int ID_Usuario, int ID_Ejemplar) {
        this.ID = ID;
        FechaPrestamo = fechaPrestamo;
        FechaDevolucion = fechaDevolucion;
        Estado = estado;
        this.ID_Usuario = ID_Usuario;
        this.ID_Ejemplar = ID_Ejemplar;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getFechaPrestamo() {
        return FechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        FechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return FechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        FechaDevolucion = fechaDevolucion;
    }

    public boolean getEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public int getID_Ejemplar() {
        return ID_Ejemplar;
    }

    public void setID_Ejemplar(int ID_Ejemplar) {
        this.ID_Ejemplar = ID_Ejemplar;
    }
}

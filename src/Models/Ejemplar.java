package Models;

public class Ejemplar implements Entidad{
    private int ID;
    private String Codigo_Interno;
    private boolean Estado;
    private int ID_Libro;

    public Ejemplar() {
        this.ID = 0;
        this.Codigo_Interno = "";
        this.Estado = false; // Por defecto, el ejemplar está disponible
        this.ID_Libro = 0;
    }

    public Ejemplar(int ID, String codigo, boolean estado, int ID_Libro) {
        this.ID = ID;
        Codigo_Interno = codigo;
        Estado = estado;
        this.ID_Libro = ID_Libro;
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

    public int getID_Libro() {
        return ID_Libro;
    }

    public void setID_Libro(int ID_Libro) {
        this.ID_Libro = ID_Libro;
    }
}

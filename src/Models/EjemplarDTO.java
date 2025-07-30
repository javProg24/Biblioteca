package Models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarDTO {
    private int ID;
    private String Codigo_Interno;
    private boolean Estado;
    private int ID_Libro;
    private String nombreLibro;

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

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }
}

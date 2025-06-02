package main.java.Models;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Prestamo {
    private int ID;
    private Date FechaPrestamo;
    private Date FechaDevolucion;
    private boolean Estado;
    private int ID_Bibliotecario;
    private int ID_Usuario;
    private int ID_Ejemplar;
//    public Prestamo() {
//        this.ID = 0;
//        this.FechaPrestamo = new Date();
//        this.FechaDevolucion = new Date();
//        this.Estado = true; // Por defecto, el préstamo está activo
//        this.ID_Bibliotecario = 0;
//        this.ID_Usuario = 0;
//        this.ID_Ejemplar = 0;
//    }
//    public Prestamo(int ID, Date FechaPrestamo, Date FechaDevolucion, boolean Estado, int ID_Bibliotecario, int ID_Usuario, int ID_Ejemplar) {
//        this.ID = ID;
//        this.FechaPrestamo = FechaPrestamo;
//        this.FechaDevolucion = FechaDevolucion;
//        this.Estado = Estado;
//        this.ID_Bibliotecario = ID_Bibliotecario;
//        this.ID_Usuario = ID_Usuario;
//        this.ID_Ejemplar = ID_Ejemplar;
//    }
}

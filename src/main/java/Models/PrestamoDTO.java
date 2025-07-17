package main.java.Models;

import lombok.*;

import java.util.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrestamoDTO {
    private int ID;
    private String Usuario;
    private String Libro;
    private Date FechaPrestamo;
    private Date FechaDevolucion;
    private String Codigo_Ejemplar;
    private boolean Estado;
}

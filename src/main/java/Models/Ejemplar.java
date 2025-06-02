package main.java.Models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Ejemplar {
    private int ID;
    private String Codigo;
    private boolean Estado;
    private int IDLibro;

//    public Ejemplar() {
//        this.ID = 0;
//        this.Codigo = "";
//        this.Estado = true; // Por defecto, el ejemplar está disponible
//        this.IDLibro = 0;
//    }
//
//    public Ejemplar(int ID, String codigo, boolean estado, int IDLibro) {
//        this.ID = ID;
//        Codigo = codigo;
//        Estado = estado;
//        this.IDLibro = IDLibro;
//    }
}

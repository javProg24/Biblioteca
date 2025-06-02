package main.java.Models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Bibliotecario {
    private int ID;
    private String Usuario;
    private String Contrasena;

//    public Bibliotecario() {
//        this.ID = 0;
//        this.Usuario = "";
//        this.Contrasena = "";
//    }
//
//    public Bibliotecario(int ID, String usuario, String contrasena) {
//        this.ID = ID;
//        Usuario = usuario;
//        Contrasena = contrasena;
//    }
}

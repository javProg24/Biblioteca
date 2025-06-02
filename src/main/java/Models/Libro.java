package main.java.Models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Libro {
    private int ID;
    private String Titulo;
    private String Autor;
    private int AnioPublicacion;
    private char Categoria;
    private String ISBN;
//    public Libro() {
//        this.ID = 0;
//        this.Titulo = "";
//        this.Autor = "";
//        this.AnioPublicacion = 0;
//        this.Categoria = ' ';
//        this.ISBN = "";
//    }
//    public Libro(int ID, String Titulo, String Autor, int AnioPublicacion, char Categoria, String ISBN) {
//        this.ID = ID;
//        this.Titulo = Titulo;
//        this.Autor = Autor;
//        this.AnioPublicacion = AnioPublicacion;
//        this.Categoria = Categoria;
//        this.ISBN = ISBN;
//    }
}

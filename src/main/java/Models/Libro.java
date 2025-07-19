package main.java.Models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Libro implements Entidad{
    private int ID;
    private int ISBN;
    private String Titulo;
    private int Anio_Publicacion;
    private String Autor;
    private String Categoria;

    public Libro(String titulo, String autor) {
        this.Titulo = titulo;
        this.Autor = autor;
    }
    public boolean esValido(){
        return Titulo !=null&&!Titulo.isEmpty()&&
                Autor!=null&&!Autor.isEmpty();
    }
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

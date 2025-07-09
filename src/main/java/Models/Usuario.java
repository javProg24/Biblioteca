package main.java.Models;

import lombok.*;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Usuario implements Entidad {
    private int ID;
    private String Nombre;
    private String Apellido;
    private String Dirreccion;
    private int Telefono;
    private Date Fecha_Nacimiento;
//    public Usuario() {
//        this.ID=0;
//        this.Nombre = "";
//        this.Apellido = "";
//        this.Dirreccion = "";
//        this.Telefono = 0;
//        this.FechaNacimiento = new Date();
//    }
//    public Usuario(int ID, String Nombre, String Apellido, String Dirreccion, int Telefono, Date FechaNacimiento) {
//        this.ID = ID;
//        this.Nombre = Nombre;
//        this.Apellido = Apellido;
//        this.Dirreccion = Dirreccion;
//        this.Telefono = Telefono;
//        this.FechaNacimiento = FechaNacimiento;
//    }
}

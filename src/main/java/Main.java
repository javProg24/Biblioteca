package main.java;

import main.java.Controllers.Operadores.Creators.PropiedadesCreator;
import main.java.Models.Usuario;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        PropiedadesCreator propiedades=new PropiedadesCreator();
        Usuario usuario = crearUsuario();
        System.out.println(propiedades.listarPropiedad(usuario,"ID"));
    }
    private static Usuario crearUsuario() {
        Usuario usuario=new Usuario();
        usuario.setID(967182694);
//        usuario.setNombre("Juan");
//        usuario.setApellido("Cabrera");
//        usuario.setDirreccion("Guayaquil");
//        usuario.setTelefono(967182694);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date fecha = sdf.parse("1990-01-01");
//        usuario.setFechaNacimiento(fecha);
        return usuario;
    }
}

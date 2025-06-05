package main.java;

import main.java.Controllers.Operadores.Creators.PropiedadesCreator;
import main.java.Models.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        PropiedadesCreator propiedades=new PropiedadesCreator();
        Usuario usuario = crearUsuario();
        System.out.println(propiedades.crearPropiedades(usuario));
    }
    private static Usuario crearUsuario() throws ParseException {
        Usuario usuario=new Usuario();
        usuario.setID(1);
        usuario.setNombre("Juan");
        usuario.setApellido("Cabrera");
        usuario.setDirreccion("Guayaquil");
        usuario.setTelefono(967182694);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse("1990-01-01");
        usuario.setFechaNacimiento(fecha);
        return usuario;
    }
}

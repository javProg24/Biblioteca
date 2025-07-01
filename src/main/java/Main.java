package main.java;

import main.java.Controllers.Operadores.Creators.PropiedadesCreator;
import main.java.Controllers.Operadores.Metodos.ControladorUsuario;
import main.java.Models.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static String id= "ID"; // Nombre de la propiedad que se usa para identificar al usuario
    private final static String telefono="Telefono";
    public static void main(String[] args) throws ParseException {
        //PropiedadesCreator propiedades=new PropiedadesCreator();
        Scanner sc= new Scanner(System.in);
        //Usuario usuario = crearUsuario();
        int opcion;
        do{
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Actualizar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Consultar usuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion){
                case 1-> registrarUsuario();
            }
        }while (opcion!=0);
        //System.out.println(propiedades.crearPropiedades(usuario));
    }
    private static void registrarUsuario() throws ParseException {
        System.out.print("Ingrese el nombre: ");
        Scanner sc = new Scanner(System.in);
        String nombre = sc.nextLine();
        System.out.print("Ingrese el apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Ingrese la dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Ingrese el teléfono: ");
        int telefono = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese la fecha de nacimiento (yyyy-MM-dd): ");
        String fechaNacimiento = sc.nextLine();
        //la lista tiene que guardar los datos de los usuarios
        List<Object>datosUsuarios = new ArrayList<>();
        datosUsuarios.add(nombre);
        datosUsuarios.add(apellido);
        datosUsuarios.add(direccion);
        datosUsuarios.add(telefono);
        datosUsuarios.add(fechaNacimiento);
        Usuario usuario = crearUsuario(datosUsuarios);
        if (ControladorUsuario.crearUsuario(usuario)){
            System.out.println("Usuario registrado con éxito.");
        } else {
            System.out.println("Error al registrar el usuario.");
        }
    }
    private static Usuario crearUsuario(List<Object>datos) throws ParseException {
        Usuario usuario=new Usuario();
        usuario.setNombre(datos.get(0).toString());
        usuario.setApellido(datos.get(1).toString());
        usuario.setDirreccion(datos.get(2).toString());
        usuario.setTelefono(Integer.parseInt(datos.get(3).toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse(datos.get(4).toString());
        usuario.setFechaNacimiento(fecha);
        return usuario;
    }
}

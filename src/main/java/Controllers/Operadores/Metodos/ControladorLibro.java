package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorLibro {
    private final static String id = "ID"; // Nombre de la propiedad que se usa para identificar al libro
    private final static String titulo = "Titulo"; // Nombre de la propiedad que se usa para identificar al libro por titulo

    public static <T extends Entidad> boolean crearLibro(T entidad,int indexOutput) { // Metodo para crear un libro
        return ControladorGeneral.crearEntidad(entidad, E_ROL._LIBRO,indexOutput);
    }
    public static <T extends Entidad> boolean actualizarLibro(T entidad) { // Metodo para actualizar un libro
        return ControladorGeneral.actualizarEntidad(entidad, E_ROL._LIBRO);
    }
    public static  <T extends Entidad> boolean eliminarLibro(T entidad) { // Metodo para eliminar un libro
        List<String>parametro = List.of(id);
        return ControladorGeneral.eliminarEntidad(entidad, E_ROL._LIBRO, parametro);
    }
    public static List<Map<String, Object>> obtenerLibros() { // Metodo para obtener todos los libros
        return ControladorGeneral.obtenerEntidad(E_ROL._LIBRO);
    }
    public static  <T extends Entidad> List<Map<String, Object>> obtenerLibroID(T entidad) { // Metodo para obtener un libro por ID
        List<String> parametro = List.of(id);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._LIBRO, E_PARAMETRO._ID, parametro);
    }
    public static <T extends Entidad> List<Map<String, Object>> obtenerLibroTitulo(T entidad) { // Metodo para obtener un libro por titulo
        List<String> parametro = List.of(titulo);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._LIBRO, E_PARAMETRO._TITULO, parametro);
    }
}

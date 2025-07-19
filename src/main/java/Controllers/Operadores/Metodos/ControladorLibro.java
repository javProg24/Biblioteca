package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorLibro {
    private final static String id = "ID";
    private final static String titulo = "Titulo";
    public static <T extends Entidad> boolean crearLibro(T entidad,int indexOutput) {
        return ControladorGeneral.crearEntidad(entidad, E_ROL._LIBRO,indexOutput);
    }
    public static <T extends Entidad> boolean actualizarLibro(T entidad) {
        return ControladorGeneral.actualizarEntidad(entidad, E_ROL._LIBRO);
    }
    public static  <T extends Entidad> boolean eliminarLibro(T entidad) {
        List<String>parametro = List.of(id);
        return ControladorGeneral.eliminarEntidad(entidad, E_ROL._LIBRO, parametro);
    }
    public static List<Map<String, Object>> obtenerLibros() {
        return ControladorGeneral.obtenerEntidad(E_ROL._LIBRO);
    }
    public static  <T extends Entidad> List<Map<String, Object>> obtenerLibroID(T entidad) {
        List<String> parametro = List.of(id);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._LIBRO, E_PARAMETRO._ID, parametro);
    }
    public static <T extends Entidad> List<Map<String, Object>> obtenerLibroTitulo(T entidad) {
        List<String> parametro = List.of(titulo);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._LIBRO, E_PARAMETRO._TITULO, parametro);
    }
}

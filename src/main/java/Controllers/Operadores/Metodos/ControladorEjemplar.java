package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_CODIGO_SP;
import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;

public class ControladorEjemplar {
    private static final String id= "ID";
    private static final String libroID = "LibroID"; // Nombre de la propiedad que se usa para identificar al ejemplar por libro
    public static <T extends Entidad> boolean crearEjemplar(T entidad) {
        return ControladorGeneral.crearEntidad(entidad, E_ROL._EJEMPLAR);
    }
    public static <T extends Entidad> boolean actualizarEstadoEjemplar(T entidad) {
        //Object spNombre= E_ROL._EJEMPLAR.name() + E_PARAMETRO._ESTADO.name();
        return ControladorGeneral.actualizarEntidadParametro(entidad, E_ROL._EJEMPLAR,E_PARAMETRO._ESTADO);
    }
    public static <T extends Entidad> boolean eliminarEjemplar(T entidad) {
        List<String> parametro = List.of(id);
        return ControladorGeneral.eliminarEntidad(entidad, E_ROL._EJEMPLAR, parametro);
    }
    public static java.util.List<java.util.Map<String, Object>> obtenerEjemplares() {
        return ControladorGeneral.obtenerEntidad(E_ROL._EJEMPLAR);
    }
    public static <T extends Entidad> java.util.List<java.util.Map<String, Object>> obtenerEjemplarID(T entidad) {
        List<String> parametro = List.of(id);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._EJEMPLAR, E_PARAMETRO._ID, parametro);
    }
    public static <T extends Entidad> java.util.List<java.util.Map<String, Object>> obtenerEjemplarLibroID(T entidad) {
        List<String> parametro = List.of(libroID);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._EJEMPLAR, E_PARAMETRO._LIBRO_ID, parametro);
    }
}

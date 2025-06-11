package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.Map;
import java.util.List;

public class ControladorPrestamo {
    private static final String id = "ID"; // Nombre de la propiedad que se usa para identificar al prestamo
    private static final String libroId = "LibroID"; // Nombre de la propiedad que se usa para identificar el libro en el prestamo
    private static final String usuarioId = "UsuarioID"; // Nombre de la propiedad que se usa para identificar el usuario en el prestamo
    private static final String fechaPrestamo = "FechaPrestamo"; // Nombre de la propiedad que se usa para identificar la fecha de prestamo
    public static <T extends Entidad> boolean crearPrestamo(T entidad) { // Metodo para crear un prestamo
        return ControladorGeneral.crearEntidad(entidad, E_ROL._PRESTAMO);
    }
    public static <T extends Entidad> boolean actualizarPrestamo(T entidad) { // Metodo para actualizar un prestamo
        return ControladorGeneral.actualizarEntidad(entidad, E_ROL._PRESTAMO);
    }
    public static <T extends Entidad> boolean eliminarPrestamo(T entidad) { // Metodo para eliminar un prestamo
        List<String> parametro = List.of(id);
        return ControladorGeneral.eliminarEntidad(entidad, E_ROL._PRESTAMO, parametro);
    }
    public static List<Map<String, Object>> obtenerPrestamos() { // Metodo para obtener todos los prestamos
        return ControladorGeneral.obtenerEntidad(E_ROL._PRESTAMO);
    }
    public static <T extends Entidad> List<Map<String, Object>> obtenerPrestamoID(T entidad) { // Metodo para obtener un prestamo por ID
        List<String> parametro = List.of(id);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._PRESTAMO, E_PARAMETRO._ID, parametro);
    }
    public static <T extends Entidad> List<Map<String, Object>> obtenerPrestamoFecha(T entidad) { // Metodo para obtener un prestamo por LibroID
        List<String> parametro = List.of(fechaPrestamo);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._PRESTAMO, E_PARAMETRO._FECHA_PRESTAMO, parametro);
    }
}

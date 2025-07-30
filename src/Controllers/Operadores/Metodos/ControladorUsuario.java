package Controllers.Operadores.Metodos;

import Controllers.Operadores.Enums.E_PARAMETRO;
import Controllers.Operadores.Enums.E_ROL;
import Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorUsuario {
    private final static String id= "ID"; // Nombre de la propiedad que se usa para identificar al usuario
    private final static String cedula="Cedula"; // Nombre de la propiedad que se usa para identificar al usuario por cedula
    private final static String nombre="Nombre";

    public static <T extends Entidad> boolean crearUsuario(T entidad){ // Metodo para crear un usuario
        return ControladorGeneral.crearEntidad(entidad, E_ROL._USUARIO);
    }
    public static <T extends Entidad>boolean actualizarUsuario(T entidad){ // Metodo para actualizar un usuario
        return ControladorGeneral.actualizarEntidad(entidad, E_ROL._USUARIO);
    }
//    public static <T extends Entidad> boolean eliminarUsuario(T entidad){ // Metodo para eliminar un usuario
//        return ControladorGeneral.eliminarEntidad(entidad, E_ROL._USUARIO, id);
//    }
    public static <T extends Entidad> boolean eliminarUsuario(T entidad){
        List<String>parametro= List.of(id);
        return ControladorGeneral.eliminarEntidad(entidad,E_ROL._USUARIO,parametro);
    }
    public static List<Map<String,Object>> obtenerUsuarios(){ // Metodo para obtener todos los usuarios
        return ControladorGeneral.obtenerEntidad(E_ROL._USUARIO);
    }
    public static <T extends Entidad>List<Map<String,Object>> obtenerUsuarioID(T entidad){ // Metodo para obtener un usuario por ID
        List<String>parametro= List.of(id);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._USUARIO, E_PARAMETRO._ID, parametro);
    }
    public static  <T extends Entidad>List<Map<String,Object>>obtenerUsuarioNombre(T entidad){ // Metodo para obtener un usuario por cedula
        List<String>parametro = List.of(nombre);
        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._USUARIO, E_PARAMETRO._NOMBRE, parametro);
    }
}

package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorUsuario {
    private final static String id= "ID"; // Nombre de la propiedad que se usa para identificar al usuario
    private final static String cedula="Cedula"; // Nombre de la propiedad que se usa para identificar al usuario por cedula
    private final ControladorCreator controlador;
    public ControladorUsuario(){
        controlador=new ControladorCreator();
    }
    public <T extends Entidad> boolean crearUsuario(T entidad){ // Metodo para crear un usuario
        return controlador.crearEntidad(entidad, E_ROL._USUARIO);
    }
    public <T extends Entidad>boolean actualizarUsuario(T entidad){ // Metodo para actualizar un usuario
        return controlador.actualizarEntidad(entidad,E_ROL._USUARIO);
    }
    public <T extends Entidad> boolean eliminarUsuario(T entidad){ // Metodo para eliminar un usuario
        return controlador.eliminarEntidad(entidad,E_ROL._USUARIO,id);
    }
    public List<Map<String,Object>> obtenerUsuarios(){ // Metodo para obtener todos los usuarios
        return controlador.obtenerEntidad(E_ROL._USUARIO);
    }
    public <T extends Entidad>List<Map<String,Object>> obtenerUsuarioID(T entidad){ // Metodo para obtener un usuario por ID
        return controlador.obtenerEntidadParametro(entidad,E_ROL._USUARIO, E_PARAMETRO._ID,id);
    }
    public <T extends Entidad>List<Map<String,Object>>obtenerUsuarioCedula(T entidad){ // Metodo para obtener un usuario por cedula
        return controlador.obtenerEntidadParametro(entidad,E_ROL._USUARIO,E_PARAMETRO._CEDULA,cedula);
    }
}

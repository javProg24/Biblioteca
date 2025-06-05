package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorUsuario {
    private final static String id= "ID";
    private final static String cedula="Cedula";
    private final ControladorCreator controlador;
    public ControladorUsuario(){
        controlador=new ControladorCreator();
    }
    public <T extends Entidad> boolean crearUsuario(T entidad){
        return controlador.crearEntidad(entidad, E_ROL._USUARIO);
    }
    public <T extends Entidad>boolean actualizarUsuario(T entidad){
        return controlador.actualizarEntidad(entidad,E_ROL._USUARIO);
    }
    public <T extends Entidad> boolean eliminarUsuario(T entidad){
        return controlador.eliminarEntidad(entidad,E_ROL._USUARIO,id);
    }
    public <T extends Entidad> List<Map<String,Object>> obtenerUsuarios(){
        return controlador.obtenerEntidad(E_ROL._USUARIO);
    }
    public <T extends Entidad>List<Map<String,Object>> obtenerUsuarioID(T entidad){
        return controlador.obtenerEntidadParametro(entidad,E_ROL._USUARIO, E_PARAMETRO._ID,id);
    }
    public <T extends Entidad>List<Map<String,Object>>obtenerUsuarioCedula(T entidad){
        return controlador.obtenerEntidadParametro(entidad,E_ROL._USUARIO,E_PARAMETRO._CEDULA,cedula);
    }
}

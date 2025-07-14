package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorBiblioteca {
    private static final String Usuario = "Usuario"; // Nombre de la propiedad que se usa para identificar al bibliotecario
    private static final String Clave = "Clave"; // Nombre de la propiedad que se usa para identificar al bibliotecario por clave
    // metodo que recibe 2 parametros: Usuario y Clave
//    public static <T extends Entidad> List<Map<String,Object>> consultarUsuario(T entidad) {
//        List<String> parametros = List.of(Usuario, Clave);
//        return ControladorGeneral.obtenerEntidadParametro(entidad, E_ROL._BIBLIOTECARIO, E_PARAMETRO._CEDULA, parametros);
//    }
    public static <T extends Entidad> List<Map<String,Object>> validarBibliotecario(T entidad){
        return ControladorGeneral.validarEntidad(entidad,E_ROL._BIBLIOTECARIO);
    }
    public static <T extends Entidad> boolean crearBibliotecario(T entidad) {
        return ControladorGeneral.crearEntidad(entidad, E_ROL._BIBLIOTECARIO);
    }
}

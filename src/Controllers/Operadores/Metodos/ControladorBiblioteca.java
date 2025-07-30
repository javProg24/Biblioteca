package Controllers.Operadores.Metodos;

import Controllers.Operadores.Enums.E_ROL;
import Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorBiblioteca {
    public static <T extends Entidad> List<Map<String,Object>> validarBibliotecario(T entidad){
        return ControladorGeneral.validarEntidad(entidad,E_ROL._BIBLIOTECARIO);
    }
    public static <T extends Entidad> boolean crearBibliotecario(T entidad) {
        return ControladorGeneral.crearEntidad(entidad, E_ROL._BIBLIOTECARIO);
    }
}

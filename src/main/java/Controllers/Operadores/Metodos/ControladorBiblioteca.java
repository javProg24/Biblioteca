package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorBiblioteca {
    public static <T extends Entidad> List<Map<String,Object>> validarBibliotecario(T entidad){
        return ControladorGeneral.validarEntidad(entidad,E_ROL._BIBLIOTECARIO);
    }
}

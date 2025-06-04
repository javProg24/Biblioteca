package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

public class ControladorUsuario {
    private final ControladorCreator controlador;
    public ControladorUsuario(){
        controlador=new ControladorCreator();
    }
    public <T extends Entidad> boolean crearUsuario(T entidad){
        return controlador.crearEntidad(entidad, E_ROL._USUARIO);
    }
}

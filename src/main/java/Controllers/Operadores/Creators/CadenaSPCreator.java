package main.java.Controllers.Operadores.Creators;

import main.java.Controllers.Operadores.Enums.E_CODIGO_SP;
import main.java.Controllers.Operadores.Enums.E_ROL;

public class CadenaSPCreator {
    private final StringBuilder cadenaCompleta;
    public CadenaSPCreator(){
        cadenaCompleta=new StringBuilder();
    }

    private String cadenaFinal(E_CODIGO_SP codigo, E_ROL rol){
        cadenaCompleta.setLength(0);
        cadenaCompleta.append(codigo);
        cadenaCompleta.append(rol);
        return cadenaCompleta.toString();
    }

    public String crearCadenaCompleta(E_CODIGO_SP codigo, E_ROL rol){
        return cadenaFinal(codigo,rol);
    }
}

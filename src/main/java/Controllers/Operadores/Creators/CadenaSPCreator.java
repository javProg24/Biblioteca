package main.java.Controllers.Operadores.Creators;

import main.java.Controllers.Operadores.Enums.E_CODIGO_SP;
import main.java.Controllers.Operadores.Enums.E_ROL;

public class CadenaSPCreator {
    private final StringBuilder cadenaCompleta;
    public CadenaSPCreator(){
        cadenaCompleta=new StringBuilder();
    }
    /**
     * Construye una cadena final concatenando un código y un rol, utilizando un `StringBuilder`.
     *
     * @param codigo El código del procedimiento almacenado representado por un enum `E_CODIGO_SP`.
     * @param rol El rol asociado representado por un enum `E_ROL`.
     * @return La cadena completa resultante de la concatenación del código y el rol.
     */
    private String cadenaFinal(E_CODIGO_SP codigo, E_ROL rol){
        cadenaCompleta.setLength(0);
        cadenaCompleta.append(codigo);
        cadenaCompleta.append(rol);
        return cadenaCompleta.toString();
    }
    /**
     * Crea una cadena completa concatenando un código y un rol.
     *
     * @param codigo El código del procedimiento almacenado representado por un enum `E_CODIGO_SP`.
     * @param rol El rol asociado representado por un enum `E_ROL`.
     * @return La cadena completa resultante de la concatenación del código y el rol.
     */
    public String crearCadenaCompleta(E_CODIGO_SP codigo, E_ROL rol){
        return cadenaFinal(codigo,rol);
    }
}

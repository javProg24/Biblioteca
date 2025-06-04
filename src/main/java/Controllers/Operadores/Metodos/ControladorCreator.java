package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Datos.Interface.Acceso_DB;
import main.java.Controllers.Datos.Interface.ExecuteSP;
import main.java.Controllers.Operadores.Creators.CadenaSPCreator;
import main.java.Controllers.Operadores.Creators.PropiedadesCreator;
import main.java.Controllers.Operadores.Enums.E_CODIGO_SP;
import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorCreator {
    private Map<Integer,Object>lista;
    private final CadenaSPCreator cadenaSP;
    private final Acceso_DB accesoDb;
    private final PropiedadesCreator propiedadesEntidad;
    public ControladorCreator(){
        lista= new HashMap<>();
        cadenaSP=new CadenaSPCreator();
        accesoDb=new ExecuteSP();
        propiedadesEntidad=new PropiedadesCreator();
    }
    public <T extends Entidad> boolean crearEntidad(T entidad, E_ROL rol){
        lista=propiedadesEntidad.crearPropiedadesNoID(entidad);
        String procedimientoAlmacenado=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_INSERTAR,rol);
        return accesoDb.CrearEntidad(procedimientoAlmacenado,lista);
    }
    public <T extends Entidad>List<Map<String,Object>> obtenerEntidad(T entidad, E_ROL rol){
        String procedimientoAlmacenado=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_OBTENER,rol);
        return accesoDb.ObtenerEntidad(procedimientoAlmacenado);
    }
    public <T extends Entidad>List<Map<String,Object>> obtenerEntidadParametro(T entidad, E_ROL rol, E_PARAMETRO parametro,String nombre) {
        lista=propiedadesEntidad.listarPropiedad(entidad,nombre);
        String spNombre = E_CODIGO_SP.SP_OBTENER + parametro.name();
        String procedimientoAlmacenado = spNombre + rol.name();
        return accesoDb.ObtenerPorParametro(procedimientoAlmacenado,lista);
    }
    
}

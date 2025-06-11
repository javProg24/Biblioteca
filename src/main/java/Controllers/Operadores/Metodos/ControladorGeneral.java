package main.java.Controllers.Operadores.Metodos;

import main.java.Controllers.Datos.Interface.Acceso_DB;
import main.java.Controllers.Datos.Interface.ExecuteSP;
import main.java.Controllers.Operadores.Creators.CadenaSPCreator;
import main.java.Controllers.Operadores.Creators.PropiedadesCreator;
import main.java.Controllers.Operadores.Enums.E_CODIGO_SP;
import main.java.Controllers.Operadores.Enums.E_PARAMETRO;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Models.Entidad;

import java.util.List;
import java.util.Map;

public class ControladorGeneral {
    private static final CadenaSPCreator cadenaSP=new CadenaSPCreator();
    private static final Acceso_DB accesoDb=new ExecuteSP();
    private static final PropiedadesCreator propiedadesEntidad = new PropiedadesCreator();
    // crear
    protected static  <T extends Entidad> boolean crearEntidad(T entidad, E_ROL rol){
        //lista=propiedadesEntidad.crearPropiedadesNoID(entidad);
        //lista= PropiedadesCreator.crearPropiedadesNoID(entidad);
        Map<Integer, Object> lista = propiedadesEntidad.crearPropiedadesNoID(entidad);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_INSERTAR,rol);
        return accesoDb.CrearEntidad(procedimiento,lista);
    }
    // obtener
    protected static List<Map<String,Object>> obtenerEntidad(E_ROL rol){
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_OBTENER,rol);
        return accesoDb.ObtenerEntidad(procedimiento);
    }
    // obtener por parametro por 1 parametro - ahora por n cantidad de parametros
    protected static <T extends Entidad>List<Map<String,Object>> obtenerEntidadParametro(T entidad, E_ROL rol, E_PARAMETRO parametro,List<String>atributos) {
        //lista=propiedadesEntidad.listarPropiedad(entidad,nombre);
        //lista= PropiedadesCreator.listarPropiedad(entidad, nombre);
        Map<Integer,Object> lista = propiedadesEntidad.listarPropiedades(entidad, atributos);
        String spNombre = E_CODIGO_SP.SP_OBTENER + parametro.name(); // Nombre del procedimiento almacenado
        String procedimiento = spNombre + rol.name();
        return accesoDb.ObtenerPorParametro(procedimiento,lista);
    }
    // actualizar
    protected static <T extends Entidad> boolean actualizarEntidad(T entidad, E_ROL rol){
        //lista=propiedadesEntidad.crearPropiedades(entidad);
        //lista= PropiedadesCreator.crearPropiedades(entidad);
        Map<Integer,Object> lista = propiedadesEntidad.crearPropiedades(entidad);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_ACTUALIZAR,rol);
        return accesoDb.ActualizarEntidad(procedimiento,lista);
    }
    //eliminar
    protected static <T extends Entidad>boolean eliminarEntidad(T entidad,E_ROL rol,List<String>parametros){
        //lista=propiedadesEntidad.listarPropiedad(entidad,parametro);
        //lista= PropiedadesCreator.listarPropiedad(entidad, parametro);
        Map<Integer,Object> lista = propiedadesEntidad.listarPropiedades(entidad, parametros);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_ELIMINAR,rol);
        return accesoDb.EliminarEntidad(procedimiento,lista);
    }
}
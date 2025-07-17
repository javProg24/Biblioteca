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
    /**
     * Crea una nueva entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad a crear, que contiene los datos necesarios para la operación.
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    protected static  <T extends Entidad> boolean crearEntidad(T entidad, E_ROL rol){
        //lista=propiedadesEntidad.crearPropiedadesNoID(entidad);
        //lista= PropiedadesCreator.crearPropiedadesNoID(entidad);
        Map<Integer, Object> lista = propiedadesEntidad.crearPropiedadesNoID(entidad);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_INSERTAR,rol);
        return accesoDb.CrearEntidad(procedimiento,lista);
    }
    /**
     * Crea una nueva entidad en la base de datos ejecutando un procedimiento almacenado,
     * permitiendo especificar un índice de salida para obtener valores generados por el procedimiento.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad a crear, que contiene los datos necesarios para la operación.
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @param indexOutput El índice del parámetro de salida que se utilizará para capturar valores generados.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    protected static <T extends Entidad>boolean crearEntidad(T entidad,E_ROL rol, int indexOutput){
        Map<Integer, Object> lista = propiedadesEntidad.crearPropiedadesNoID(entidad);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_INSERTAR,rol);
        return accesoDb.CrearEntidad(procedimiento,lista,indexOutput);
    }
    /**
     * Válida una entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad a validar, que contiene los datos necesarios para la operación.
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @return Una lista de mapas donde cada mapa representa una fila del resultado,
     *         con las claves como nombres de columnas y los valores como datos correspondientes.
     */
    protected static <T extends Entidad> List<Map<String,Object>> validarEntidad(T entidad, E_ROL rol){
        Map<Integer, Object> lista = propiedadesEntidad.crearPropiedadesNoID(entidad);
        String procedimiento = cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_OBTENER,rol);
        return accesoDb.ObtenerPorParametro(procedimiento,lista);
    }
    /**
     * Obtiene una lista de entidades desde la base de datos ejecutando un procedimiento almacenado.
     *
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @return Una lista de mapas donde cada mapa representa una fila del resultado,
     *         con las claves como nombres de columnas y los valores como datos correspondientes.
     */
    protected static List<Map<String,Object>> obtenerEntidad(E_ROL rol){
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_OBTENER,rol);
        return accesoDb.ObtenerEntidad(procedimiento);
    }
    // obtener por parametro por 1 parametro - ahora por n cantidad de parametros
    /**
     * Obtiene una lista de entidades desde la base de datos ejecutando un procedimiento almacenado,
     * permitiendo especificar un parámetro adicional y una lista de atributos.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad que contiene los datos necesarios para la operación.
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @param parametro El parámetro adicional representado por un enum `E_PARAMETRO`, que se utiliza para personalizar el procedimiento almacenado.
     * @param atributos Una lista de nombres de atributos que se utilizarán para construir las propiedades de la entidad.
     * @return Una lista de mapas donde cada mapa representa una fila del resultado,
     *         con las claves como nombres de columnas y los valores como datos correspondientes.
     */
    protected static <T extends Entidad>List<Map<String,Object>> obtenerEntidadParametro(T entidad, E_ROL rol, E_PARAMETRO parametro,List<String>atributos) {
        //lista=propiedadesEntidad.listarPropiedad(entidad,nombre);
        //lista= PropiedadesCreator.listarPropiedad(entidad, nombre);
        Map<Integer,Object> lista = propiedadesEntidad.listarPropiedades(entidad, atributos);
        String spNombre = E_CODIGO_SP.SP_OBTENER + parametro.name(); // Nombre del procedimiento almacenado
        String procedimiento = spNombre + rol.name();
        return accesoDb.ObtenerPorParametro(procedimiento,lista);
    }
    /**
     * Actualiza una entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad a actualizar, que contiene los datos necesarios para la operación.
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    protected static <T extends Entidad> boolean actualizarEntidad(T entidad, E_ROL rol){
        //lista=propiedadesEntidad.crearPropiedades(entidad);
        //lista= PropiedadesCreator.crearPropiedades(entidad);
        Map<Integer,Object> lista = propiedadesEntidad.crearPropiedades(entidad);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_ACTUALIZAR,rol);
        return accesoDb.ActualizarEntidad(procedimiento,lista);
    }
    /**
     * Elimina una entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param <T> El tipo de la entidad que extiende de la clase `Entidad`.
     * @param entidad La entidad a eliminar, que contiene los datos necesarios para la operación.
     * @param rol El rol asociado representado por un enum `E_ROL`, que se utiliza para construir el procedimiento almacenado.
     * @param parametros Una lista de nombres de parámetros que se utilizarán para construir las propiedades de la entidad.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    protected static <T extends Entidad>boolean eliminarEntidad(T entidad,E_ROL rol,List<String>parametros){
        //lista=propiedadesEntidad.listarPropiedad(entidad,parametro);
        //lista= PropiedadesCreator.listarPropiedad(entidad, parametro);
        Map<Integer,Object> lista = propiedadesEntidad.listarPropiedades(entidad, parametros);
        String procedimiento=cadenaSP.crearCadenaCompleta(E_CODIGO_SP.SP_ELIMINAR,rol);
        return accesoDb.EliminarEntidad(procedimiento,lista);
    }
}
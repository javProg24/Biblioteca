package main.java.Controllers.Datos.Interface;

import main.java.Controllers.Datos.DbOperaciones.ExecuteQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteSP implements Acceso_DB{
    private final ExecuteQuery obj_bd;
    public ExecuteSP(){
        obj_bd=new ExecuteQuery();
    }
    /**
     * Obtiene una entidad desde la base de datos ejecutando un procedimiento almacenado.
     *
     * @param sp_query El nombre del procedimiento almacenado a ejecutar.
     * @return Una lista de mapas donde cada mapa representa una fila del resultado,
     *         con las claves como nombres de columnas y los valores como datos correspondientes.
     */
    @Override
    public List<Map<String, Object>> ObtenerEntidad(String sp_query) {
        Map<Integer, Object> parametros = new HashMap<>();
        return obj_bd.ExecuteSPQuery(sp_query,parametros);
    }
    /**
     * Obtiene una lista de entidades desde la base de datos ejecutando un procedimiento almacenado
     * con parámetros específicos.
     *
     * @param sp_query El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro y el valor es el dato correspondiente.
     * @return Una lista de mapas donde cada mapa representa una fila del resultado,
     *         con las claves como nombres de columnas y los valores como datos correspondientes.
     */
    @Override
    public List<Map<String, Object>> ObtenerPorParametro(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPQuery(sp_query,parametros);
    }
    /**
     * Crea una nueva entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param sp_query El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro y el valor es el dato correspondiente.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    @Override
    public boolean CrearEntidad(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros);
    }
    /**
     * Crea una nueva entidad en la base de datos ejecutando un procedimiento almacenado,
     * permitiendo especificar un índice de salida para obtener valores generados por el procedimiento.
     *
     * @param sp_query El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro y el valor es el dato correspondiente.
     * @param indexOutput El índice del parámetro de salida que se utilizará para capturar valores generados.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    @Override
    public boolean CrearEntidad(String sp_query, Map<Integer, Object> parametros,int indexOutput){
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros,indexOutput);
    }
    /**
     * Actualiza una entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param sp_query El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro y el valor es el dato correspondiente.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    @Override
    public boolean ActualizarEntidad(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros);
    }
    /**
     * Elimina una entidad en la base de datos ejecutando un procedimiento almacenado.
     *
     * @param sp_query El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro y el valor es el dato correspondiente.
     * @return `true` si la operación se ejecutó correctamente, de lo contrario `false`.
     */
    @Override
    public boolean EliminarEntidad(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros);
    }

}

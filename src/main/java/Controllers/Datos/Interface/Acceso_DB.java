package main.java.Controllers.Datos.Interface;

import java.util.List;
import java.util.Map;
/**
 * Interfaz que define los métodos para interactuar con la base de datos mediante
 * procedimientos almacenados.
 */
public interface Acceso_DB {
    List<Map<String,Object>>ObtenerEntidad(String sp_query);
    List<Map<String,Object>>ObtenerPorParametro(String sp_query, Map<Integer,Object> parametros);
    boolean CrearEntidad(String sp_query, Map<Integer,Object> parametros);
    boolean CrearEntidad(String sp_query, Map<Integer,Object> parametros,int indexOutput);
    boolean ActualizarEntidad(String sp_query, Map<Integer,Object> parametros);
    boolean EliminarEntidad(String sp_query, Map<Integer,Object> parametros);
}

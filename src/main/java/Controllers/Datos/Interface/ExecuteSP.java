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

    @Override
    public List<Map<String, Object>> ObtenerEntidad(String sp_query) {
        Map<Integer, Object> parametros = new HashMap<>();
        return obj_bd.ExecuteSPQuery(sp_query,parametros);
    }

    @Override
    public List<Map<String, Object>> ObtenerPorParametro(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPQuery(sp_query,parametros);
    }

    @Override
    public boolean CrearEntidad(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros);
    }
    public boolean CrearEntidad(String sp_query, Map<Integer, Object> parametros,int indexOutput){
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros,indexOutput);
    }

    @Override
    public boolean ActualizarEntidad(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros);
    }

    @Override
    public boolean EliminarEntidad(String sp_query, Map<Integer, Object> parametros) {
        return obj_bd.ExecuteSPNo_Query(sp_query,parametros);
    }

}

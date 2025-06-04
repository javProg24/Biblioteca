package main.java.Controllers.Datos.DbOperaciones;

import com.sun.jdi.connect.Connector;

import java.sql.*;
import java.util.*;

public class ExecuteQuery {
    private final ConexionDB conexionDB = new ConexionDB();
    // metodo que devuelve una tabla de datos
    // este metodo recibe un string (nombre_sp), Lista de parametros
    //public ExecuteQuery(string nombre_sp, lista de parametros)
    public boolean ExecuteSPNo_Query(String nombre_sp, Map<Integer,Object> parametros){
        try {
            Connection con=conexionDB.AbrirConexion();
            int cantidad=parametros.size();
            String placeHolders=String.join(", ", Collections.nCopies(cantidad,"?"));
            String sql ="{call "+nombre_sp+"("+placeHolders+")}";
            CallableStatement statement = con.prepareCall(sql);
            if (!parametros.isEmpty()){
                for (Map.Entry<Integer, Object> parametro : parametros.entrySet()) {
                    statement.setObject(parametro.getKey(),parametro.getValue());
                }
            }
            int resultado = statement.executeUpdate();
            statement.clearParameters();
            conexionDB.CerrarConexion(con);
            return resultado > 0;
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
            return false;
        }
    }
    public List<Map<String,Object>> ExecuteSPQuery(String nombre_sp, Map<Integer,Object> parametros){
        try {
            Connection con=conexionDB.AbrirConexion();
            int cantidad=parametros.size(); //0
            String placeHolders=String.join(", ", Collections.nCopies(cantidad,"?"));
            String sql ="{call "+nombre_sp+"("+placeHolders+")}";
            CallableStatement statement = con.prepareCall(sql);
            if (!parametros.isEmpty()){
                for (Map.Entry<Integer, Object> parametro : parametros.entrySet()) {
                    statement.setObject(parametro.getKey(),parametro.getValue());
                }
            }
            ResultSet reader = statement.executeQuery();
            statement.clearParameters();
            ResultSetMetaData metaData=reader.getMetaData();
            int columnas=metaData.getColumnCount();
            List<Map<String, Object>> tabla = new ArrayList<>();
            while (reader.next()){
                Map<String,Object>datos=new HashMap<>();
                for (int i=1;i<=columnas;i++){
                    String columnaName=metaData.getColumnLabel(i);
                    Object value=reader.getObject(i);
                    datos.put(columnaName,value);
                }
                tabla.add(datos);
            }
            return tabla;
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
//    private Object tipoParametro(Object parametro){
//        if (parametro instanceof Integer){
//            return parametro;
//        }
//        if( parametro instanceof String){
//            return parametro;
//        }
//        if(parametro instanceof Boolean){
//            return parametro;
//        }
//        throw new IllegalArgumentException("Tipo de parametro no soportado: " + parametro.getClass().getName());
//    }
}

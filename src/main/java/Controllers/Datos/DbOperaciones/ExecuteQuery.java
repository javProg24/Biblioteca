package main.java.Controllers.Datos.DbOperaciones;

import java.sql.*;
import java.util.*;

public class ExecuteQuery {
    private final ConexionDB conexionDB;
    public static int intOut;
    private static final String llamada= "{call %s(%s)}";
    public ExecuteQuery(){
        conexionDB = new ConexionDB();
    }

    public boolean ExecuteSPNo_Query(String nombre_sp, Map<Integer,Object> parametros){
        try {
            Connection con=conexionDB.AbrirConexion();
            int cantidad=parametros.size();
            String placeHolders=String.join(", ", Collections.nCopies(cantidad,"?"));
            String sql = String.format(llamada, nombre_sp, placeHolders);
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
            int cantidad=parametros.size();
            String placeHolders=String.join(", ", Collections.nCopies(cantidad,"?"));
            String sql = String.format(llamada, nombre_sp, placeHolders);
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
            conexionDB.CerrarConexion(con);
            return tabla;
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean ExecuteSPNo_Query(String nombre_sp,Map<Integer,Object>parametros,int indexOutput){
        Connection con = null;
        CallableStatement statement;
        try {
            con = conexionDB.AbrirConexion();
            con = conexionDB.AbrirConexion();
            int cantidad = parametros.size();
            String placeHolders = String.join(", ", Collections.nCopies(cantidad + 1, "?"));
            String sql = String.format(llamada, nombre_sp, placeHolders);
            statement = con.prepareCall(sql);
            for (Map.Entry<Integer, Object> parametro : parametros.entrySet()) {
                statement.setObject(parametro.getKey(), parametro.getValue());
            }
            statement.registerOutParameter(indexOutput,Types.INTEGER);
            int resultado=statement.executeUpdate();
            intOut=statement.getInt(indexOutput);
            statement.clearParameters();
            return resultado>0;
        }catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento con OUTPUT: " + e.getMessage());
            return false;
        } finally {
            conexionDB.CerrarConexion(con);
        }
    }
}

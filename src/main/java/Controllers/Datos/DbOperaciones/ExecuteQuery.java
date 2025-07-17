package main.java.Controllers.Datos.DbOperaciones;

import java.sql.*;
import java.util.*;

public class ExecuteQuery {
    private final ConexionDB conexionDB;
    public static int intOut;
    /**
     * Plantilla para la llamada a un procedimiento almacenado (Stored Procedure).
     *
     * La cadena utiliza un formato donde:
     * - `%s` se reemplaza por el nombre del procedimiento almacenado.
     * - `%s` se reemplaza por los marcadores de posición para los parámetros.
     *
     * Ejemplo de uso:
     * Si el nombre del procedimiento es "miProcedimiento" y tiene dos parámetros,
     * la cadena resultante será: "{call miProcedimiento(?, ?)}".
     */
    private static final String llamada= "{call %s(%s)}";
    public ExecuteQuery(){
        conexionDB = new ConexionDB();
    }

    // metodo que devuelve una tabla de datos
    // este metodo recibe un string (nombre_sp), Lista de parametros
    //public ExecuteQuery(string nombre_sp, lista de parametros)
    /**
     * Ejecuta un procedimiento almacenado (Stored Procedure) que realiza operaciones en la base de datos,
     * como inserción, actualización o eliminación, pero no retorna conjuntos de resultados (tablas).
     *
     * @param nombre_sp El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro (basado en 1) y el valor es el dato a pasar.
     * @return true si la ejecución del procedimiento fue exitosa (resultado mayor a 0), false en caso contrario.
     */
    public boolean ExecuteSPNo_Query(String nombre_sp, Map<Integer,Object> parametros){ // para insertar, actualizar y eliminar
        try {
            Connection con=conexionDB.AbrirConexion(); // Abre una conexión a la base de datos
            int cantidad=parametros.size(); // Determina la cantidad de parámetros y genera los placeholders para la consulta
            String placeHolders=String.join(", ", Collections.nCopies(cantidad,"?"));
            String sql = String.format(llamada, nombre_sp, placeHolders); // Formatea la consulta SQL con el nombre del procedimiento y los placeholders
            CallableStatement statement = con.prepareCall(sql); // Prepara la llamada al procedimiento almacenado
            // Si hay parámetros, los asigna al CallableStatement
            if (!parametros.isEmpty()){ // verifica si hay parametros
                for (Map.Entry<Integer, Object> parametro : parametros.entrySet()) {
                    statement.setObject(parametro.getKey(),parametro.getValue());
                }
            }
            int resultado = statement.executeUpdate(); // Ejecuta el procedimiento almacenado y obtiene el resultado
            statement.clearParameters(); // Limpia los parámetros del CallableStatement
            conexionDB.CerrarConexion(con); // Cierra la conexión a la base de datos
            return resultado > 0; // Devuelve true si el resultado es mayor a 0, indicando éxito
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
            return false;
        }
    }
    /**
     * Ejecuta un procedimiento almacenado (Stored Procedure) que devuelve un conjunto de resultados,
     * como una tabla de datos, y los convierte en una lista de mapas.
     *
     * @param nombre_sp El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro (basado en 1) y el valor es el dato a pasar.
     * @return Una lista de mapas, donde cada mapa representa una fila del conjunto de resultados,
     *         con las claves como nombres de columna y los valores como datos de la fila.
     *         Devuelve null si ocurre un error durante la ejecución.
     */
    public List<Map<String,Object>> ExecuteSPQuery(String nombre_sp, Map<Integer,Object> parametros){ // para obtener datos
        try {
            Connection con=conexionDB.AbrirConexion(); // Abre una conexión a la base de datos
            int cantidad=parametros.size(); // Determina la cantidad de parámetros y genera los placeholders para la consulta
            String placeHolders=String.join(", ", Collections.nCopies(cantidad,"?"));
            String sql = String.format(llamada, nombre_sp, placeHolders); // Formatea la consulta SQL con el nombre del procedimiento y los placeholders
            CallableStatement statement = con.prepareCall(sql); // Prepara la llamada al procedimiento almacenado
            // Si hay parámetros, los asigna al CallableStatement
            if (!parametros.isEmpty()){ // verifica si hay parametros
                for (Map.Entry<Integer, Object> parametro : parametros.entrySet()) {
                    statement.setObject(parametro.getKey(),parametro.getValue());
                }
            }
            ResultSet reader = statement.executeQuery(); // Ejecuta el procedimiento almacenado y obtiene el conjunto de resultados
            statement.clearParameters();
            ResultSetMetaData metaData=reader.getMetaData(); // Obtiene los metadatos del conjunto de resultados
            int columnas=metaData.getColumnCount();
            List<Map<String, Object>> tabla = new ArrayList<>(); // Crea una lista para almacenar las filas de resultados
            while (reader.next()){ // Itera sobre las filas del conjunto de resultados
                Map<String,Object>datos=new HashMap<>();
                for (int i=1;i<=columnas;i++){
                    // Obtiene el nombre de la columna y el valor correspondiente
                    String columnaName=metaData.getColumnLabel(i);
                    Object value=reader.getObject(i);
                    datos.put(columnaName,value);
                }
                tabla.add(datos); // Agrega la fila a la lista
            }
            conexionDB.CerrarConexion(con); // Cierra la conexión a la base de datos
            return tabla; // Devuelve la lista de resultados
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    /**
     * Ejecuta un procedimiento almacenado (Stored Procedure) que realiza operaciones en la base de datos
     * y obtiene un parámetro de salida (OUTPUT) junto con el resultado de la operación.
     *
     * @param nombre_sp El nombre del procedimiento almacenado a ejecutar.
     * @param parametros Un mapa que contiene los parámetros del procedimiento almacenado,
     *                   donde la clave es el índice del parámetro (basado en 1) y el valor es el dato a pasar.
     * @param indexOutput El índice del parámetro de salida (OUTPUT) en el procedimiento almacenado.
     * @return true si la ejecución del procedimiento fue exitosa (resultado mayor a 0), false en caso contrario.
     */
    public boolean ExecuteSPNo_Query(String nombre_sp,Map<Integer,Object>parametros,int indexOutput){
        Connection con = null;
        CallableStatement statement;
        try {
            // Abre una conexión a la base de datos
            con = conexionDB.AbrirConexion();
            con = conexionDB.AbrirConexion();
            // Determina la cantidad de parámetros y genera los placeholders para la consulta
            int cantidad = parametros.size();
            String placeHolders = String.join(", ", Collections.nCopies(cantidad + 1, "?"));
            String sql = String.format(llamada, nombre_sp, placeHolders);
            // Prepara la llamada al procedimiento almacenado
            statement = con.prepareCall(sql);
            // Parametros de entrada
            // Asigna los parámetros de entrada al CallableStatement
            for (Map.Entry<Integer, Object> parametro : parametros.entrySet()) {
                statement.setObject(parametro.getKey(), parametro.getValue());
            }
            // Registra el parámetro de salida (OUTPUT)
            statement.registerOutParameter(indexOutput,Types.INTEGER);
            // Ejecuta el procedimiento almacenado
            int resultado=statement.executeUpdate();
            //opcion 1
            //Libro libro = new Libro();
            //libro.setID(statement.getInt(indexOutput));
            //opcion 2
            // int out=statement.getInt(indexOutput)
            // Obtiene el valor del parámetro de salida y lo asigna a la variable estática intOut
            intOut=statement.getInt(indexOutput);
            //System.out.println(statement.getInt(indexOutput));
            // Limpia los parámetros del CallableStatement
            statement.clearParameters();
            // Devuelve true si el resultado es mayor a 0, indicando éxito
            return resultado>0;
        }catch (SQLException e) {
            // Maneja errores de SQL y los imprime en la consola
            System.err.println("Error al ejecutar el procedimiento con OUTPUT: " + e.getMessage());
            return false;
        } finally {
            // Cierra la conexión a la base de datos
            conexionDB.CerrarConexion(con);
        }
    }
}

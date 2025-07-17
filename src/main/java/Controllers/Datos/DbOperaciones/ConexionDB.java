package main.java.Controllers.Datos.DbOperaciones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    private static final Properties props=new Properties();
    // Bloque estático que se ejecuta una vez cuando la clase es cargada en memoria.
    // Este bloque carga las propiedades de configuración desde el archivo "config.properties".
    static {
        try {
            props.load(ConexionDB.class.getClassLoader().getResourceAsStream("config.properties"));
        }catch (IOException e){
            System.out.println("Error al cargar las propiedades de la conexion: "+e.getMessage());
        }
    }
    /**
     * Abre una conexión a la base de datos utilizando las propiedades configuradas.
     *
     * @return Un objeto `Connection` si la conexión se establece correctamente,
     *         o `null` si ocurre un error durante la conexión.
     */
    protected Connection AbrirConexion(){
        try{
            // Obtiene la URL, el usuario y la contraseña de las propiedades configuradas
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            return DriverManager.getConnection(url, user, password); // Establece y devuelve la conexión a la base de datos
        }catch (SQLException e){
            System.out.println("Error al abrir la conexion: "+e.getMessage()); // Maneja errores al abrir la conexión y los imprime en la consola
            return null;
        }
    }
    /**
     * Cierra una conexión a la base de datos si está abierta.
     *
     * @param conexion La conexión a la base de datos que se desea cerrar.
     *                 Puede ser `null` o ya estar cerrada.
     */
    protected void CerrarConexion(Connection conexion){
        try{
            // Verifica si la conexión no es nula y está abierta antes de cerrarla
            if (conexion!= null && !conexion.isClosed()) {
                conexion.close();
            }
        }catch (SQLException e){
            System.out.println("Error al cerrar la conexion: "+e.getMessage());
        }
    }
}
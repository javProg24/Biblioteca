package main.java.Controllers.Datos.DbOperaciones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    private static Connection conexion=null;
    private static final Properties props=new Properties();
    static {
        try {
            props.load(ConexionDB.class.getClassLoader().getResourceAsStream("config.properties"));
            System.out.println("Propiedades de la conexion cargadas exitosamente");
        }catch (IOException e){
            System.out.println("Error al cargar las propiedades de la conexion: "+e.getMessage());
        }
    }
    public Connection AbrirConexion(){
        try{
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            conexion= DriverManager.getConnection(url, user, password);
            if (conexion!=null)
                System.out.println("Conexion exitosa");
        }catch (SQLException e){
            System.out.println("Error al abrir la conexion: "+e.getMessage());
        }
        return conexion;
    }
    public Connection CerrarConexion(){
        try{
            if (conexion!= null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada exitosamente");
            }
        }catch (SQLException e){
            System.out.println("Error al cerrar la conexion: "+e.getMessage());
        }
        return conexion;
    }
}
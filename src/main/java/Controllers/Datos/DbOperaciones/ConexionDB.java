package main.java.Controllers.Datos.DbOperaciones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    private static final Properties props=new Properties();
    static {
        try {
            props.load(ConexionDB.class.getClassLoader().getResourceAsStream("config.properties"));
        }catch (IOException e){
            System.out.println("Error al cargar las propiedades de la conexion: "+e.getMessage());
        }
    }
    protected Connection AbrirConexion(){
        try{
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            return DriverManager.getConnection(url, user, password);
        }catch (SQLException e){
            System.out.println("Error al abrir la conexion: "+e.getMessage());
            return null;
        }
    }
    protected void CerrarConexion(Connection conexion){
        try{
            if (conexion!= null && !conexion.isClosed()) {
                conexion.close();
            }
        }catch (SQLException e){
            System.out.println("Error al cerrar la conexion: "+e.getMessage());
        }
    }
}
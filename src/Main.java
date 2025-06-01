import Controllers.Datos.DbOperaciones.ConexionDB;

public class Main {
    public static void main(String[] args) {
        ConexionDB conn= new ConexionDB();
        if (conn.AbrirConexion() != null) {
            System.out.println("La conexión a la base de datos se ha establecido correctamente.");
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }

    }
}
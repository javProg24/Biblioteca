package main.Resources.Shared;

public class RutaIcono {
    public static String ruta(String icono){
        return rutaIcono(icono);
    }
    private static String rutaIcono(String nombreIcono) {
        return "src/main/resources/icons/" + nombreIcono + ".png";
    }
}

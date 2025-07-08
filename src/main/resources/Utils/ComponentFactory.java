package main.resources.Utils;

import javax.swing.*;
import java.awt.*;

public class ComponentFactory {
    // Crear un campos de texto con un tamaño de fuente específico
    public static JTextField crearCampoTexto(){
        JTextField campo= new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        return campo;
    }
    // Crea una etiqueta con un texto y un tamaño de fuente específico
    public static JLabel crearEtiqueta(String texto){
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        return etiqueta;
    }
    // Crear una ruta de icono para los botones
    public static String ruta(String icono){
        return rutaIcono(icono);
    }
    private static String rutaIcono(String nombreIcono) {
        return "src/main/resources/icons/"+nombreIcono+".png";
    }
    // Crear colores personalizados para la aplicación
    public static final Color COLOR_FONDO = new Color(240, 248, 255);
    public static final Color COLOR_MENU = new Color(33, 150, 243);
    public static final Color COLOR_PRINCIPAL = new Color(70, 130, 180);
    public static final Color COLOR_SECUNDARIO = new Color(135, 206, 235);
    public static final Color COLOR_PELIGRO = new Color(220, 53, 69);
    public static final Color COLOR_EXITO = new Color(40, 167, 69);
}

package main.resources.Shared.Table;

import javax.swing.*;
import java.awt.*;

public class ActionButton extends JButton {
    /**
     * Constructor de la clase ActionButton.
     * Configura un botón con un ícono escalado y varias propiedades visuales.
     *
     * @param rutaIcono La ruta del archivo del ícono que se utilizará en el botón.
     */
    public ActionButton(String rutaIcono) {
        setIconEscalado(rutaIcono); // Establece el ícono escalado del botón.
        setFocusPainted(false); // Desactiva el enfoque pintado.
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambia el cursor al estilo de mano.
        setContentAreaFilled(true); // Habilita el relleno del área de contenido.
        setBorderPainted(false); // Desactiva el borde pintado.
        setOpaque(false); // Hace que el botón sea transparente.
        // Efecto simple de rollover para ver feedback
        setRolloverEnabled(true); // Habilita el efecto de rollover.
    }

    private void setIconEscalado(String rutaIcono) {
        try {
            ImageIcon icono = new ImageIcon(rutaIcono);
            Image image = icono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + rutaIcono);
        }
    }
}

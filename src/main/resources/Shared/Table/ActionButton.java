package main.resources.Shared.Table;

import javax.swing.*;
import java.awt.*;

public class ActionButton extends JButton {
    public ActionButton(String rutaIcono) {
        setIconEscalado(rutaIcono);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Usa FlatLaf normal: no sobrescribas paintComponent
        setContentAreaFilled(true);
        setBorderPainted(false);
        setOpaque(false);

        // Efecto simple de rollover para ver feedback
        setRolloverEnabled(true);

        // Opcional: evento para saber cuándo se hace clic
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

package main.resources.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ComponentFactory {
    // Crear campos de texto con un tamaño de fuente específico
    public static JTextField crearCampoTexto(){
        JTextField campo= new JTextField();
        Dimension campoDimension = new Dimension(200, 30);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setPreferredSize(campoDimension);
        return campo;
    }
    // Crea una etiqueta con un texto y un tamaño de fuente específico
    public static JLabel crearEtiqueta(String texto){
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return etiqueta;
    }
    public static String metodoTitulo(String titulo){
        return titulo.replaceAll("\\s+","");
    }
    public static JButton crearBoton(String texto, String ruta, boolean agregarEfectoHover) {
        ImageIcon icono = new ImageIcon(ruta);
        Image image = icono.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        icono = new ImageIcon(image);
        JButton boton = new JButton(texto, icono);
        boton.setMaximumSize(new Dimension(200, 40));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(ComponentFactory.COLOR_MENU);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(15);

        if (agregarEfectoHover) {
            boton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    boton.setBackground(new Color(21, 101, 192));
                }

                public void mouseExited(MouseEvent e) {
                    boton.setBackground(ComponentFactory.COLOR_MENU);
                }
            });
        }

        return boton;
    }


    public static void desactivarBotonVisualmente(JButton boton) {
        // Eliminar todos los ActionListener
        for (ActionListener al : boton.getActionListeners()) {
            boton.removeActionListener(al);
        }

        // Eliminar todos los MouseListener (incluido el que cambia el color)
        for (MouseListener ml : boton.getMouseListeners()) {
            boton.removeMouseListener(ml);
        }

        // Dejar cursor normal
        boton.setCursor(Cursor.getDefaultCursor());

        // Añadir MouseListener que bloquee eventos para evitar que vuelva a cambiar color o hacer algo
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                e.consume();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                e.consume();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public static JLabel crearTarjeta(String titulo, int cantidad) {
        JLabel tarjeta = new JLabel("<html><div style='text-align:center;'>"
                + "<h2 style='margin: 0;'>" + cantidad + "</h2>"
                + "<p style='margin: 0; font-size: 14px;'>" + titulo + "</p>"
                + "</div></html>");

        tarjeta.setOpaque(true);
        tarjeta.setBackground(new Color(245, 245, 245));
        tarjeta.setForeground(Color.DARK_GRAY);
        tarjeta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        tarjeta.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.setVerticalAlignment(SwingConstants.CENTER);
        tarjeta.setPreferredSize(new Dimension(160, 80));

        return tarjeta;
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

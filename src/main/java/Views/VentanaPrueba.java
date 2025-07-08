package main.java.Views;


import main.java.Views.Libro.PanelLibro;
import main.java.Views.Usuario.PanelUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class VentanaPrueba {
    private JFrame frame;
    private JPanel contentPanel; // Panel donde se carga el panel activo (FabricantePanel o PersonaPanel)
    private final Map<String, JPanel> panelsMap = new HashMap<>();
    public void createAndShowGui() {
        frame = new JFrame("Gestión de Entidades");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Crear menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuVer = new JMenu("Ver");

        JMenuItem itemUsuarios = new JMenuItem("Usuarios");
        JMenuItem itemPersonas = new JMenuItem("Libros");

        menuVer.add(itemUsuarios);
        menuVer.add(itemPersonas);
        menuBar.add(menuVer);
        frame.setJMenuBar(menuBar);

        // Panel principal donde cargar dinámicamente los paneles de tablas
        contentPanel = new JPanel(new BorderLayout());
        frame.add(contentPanel, BorderLayout.CENTER);

        // Crear paneles para cada entidad y guardarlos
        PanelUsuario panelUsuario = new PanelUsuario();
        PanelLibro personaPanel = new PanelLibro();

        panelsMap.put("Usuario", panelUsuario);
        panelsMap.put("Libro", personaPanel);

        // Listener para menú que cambia el panel activo
        itemUsuarios.addActionListener(e -> switchPanel("Usuario"));
        itemPersonas.addActionListener(e -> switchPanel("Libro"));

        // Mostrar inicialmente el panel de Fabricantes
        switchPanel("Usuario");

        frame.setVisible(true);
    }

    private void switchPanel(String panelName) {
        contentPanel.removeAll();
        JPanel panel = panelsMap.get(panelName);
        if (panel != null) {
            contentPanel.add(panel, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrueba window = new VentanaPrueba();
            window.createAndShowGui();
        });
    }
}

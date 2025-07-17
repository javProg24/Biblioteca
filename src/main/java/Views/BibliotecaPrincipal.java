package main.java.Views;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Views.Libro.PanelEjemplar;
import main.java.Views.Login.LoginPrincipal;
import main.resources.Utils.ComponentFactory;
import main.java.Views.Libro.PanelLibro;
import main.java.Views.Prestamo.PanelPrestamo;
import main.java.Views.Usuario.PanelUsuario;

import javax.swing.*;
import java.awt.*;

public class BibliotecaPrincipal extends JFrame {
    private final CardLayout cardLayout;
    private JPanel panelPrincipal;
    private final JPanel menuLateral;
    private final JPanel headerPanel;
    private final JPanel botonesPanel;
    private final JPanel logoutPanel;
    private final JPanel panelDashboard;
    private String nombreUsuario; // Campo para almacenar el nombre del usuario

    public BibliotecaPrincipal(){
        this("Admin"); // Constructor por defecto
    }

    public BibliotecaPrincipal(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        setTitle("Sistema de Biblioteca");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(ComponentFactory.COLOR_FONDO);
        setLayout(new BorderLayout());
        menuLateral = new JPanel();
        headerPanel= new JPanel();
        botonesPanel = new JPanel();
        logoutPanel = new JPanel();
        cardLayout = new CardLayout();
        panelDashboard = new JPanel();
        initComponents();
    }
    private void initComponents(){
        initMenuLateral();
        initPanelPrincipal();
        add(menuLateral, BorderLayout.WEST);
        add(panelPrincipal, BorderLayout.CENTER);
    }
    private void initMenuLateral(){
        //Menu lateral
        menuLateral.setBackground(ComponentFactory.COLOR_MENU);
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setPreferredSize(new Dimension(220, getHeight()));
        menuLateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        initHeaderPanel();
        initBotonesPanel();
        initLogoutPanel();
        menuLateral.add(headerPanel);
        menuLateral.add(botonesPanel);
        menuLateral.add(Box.createVerticalGlue()); // Espacio flexible
        menuLateral.add(logoutPanel);
    }
    private void initHeaderPanel(){
        //Panel para el título y usuario
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(ComponentFactory.COLOR_MENU);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        //Título
        JLabel lbl_titulo = new JLabel("Biblioteca");
        lbl_titulo.setFont(new Font("Arial", Font.BOLD, 28));
        lbl_titulo.setForeground(Color.WHITE);
        lbl_titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Información de usuario
        JLabel lblUsuario = new JLabel("<html><center><b>Usuario:</b> " + nombreUsuario);
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lbl_titulo);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(lblUsuario);
    }
    private void initBotonesPanel(){
        // Panel para los botones del menú
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(ComponentFactory.COLOR_MENU);
        botonesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Botones menú con íconos
        JButton btnDashboard = ComponentFactory.crearBoton("Dashboard", ComponentFactory.ruta("menu-dashboard"));
        JButton btnLibros = ComponentFactory.crearBoton("Libros", ComponentFactory.ruta("menu-books"));
        JButton btnUsuarios = ComponentFactory.crearBoton("Usuarios", ComponentFactory.ruta("menu-users"));
        JButton btnCopias = ComponentFactory.crearBoton("Copias",ComponentFactory.ruta("ejemplo"));
        JButton btnPrestamos = ComponentFactory.crearBoton("Préstamos", ComponentFactory.ruta("menu-loans"));
        // Agregar botones con espaciado
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnDashboard);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnLibros);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnPrestamos);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnCopias);

        // Agregar listeners a los botones
        btnDashboard.addActionListener(e -> cardLayout.show(panelPrincipal, "Dashboard"));
        btnLibros.addActionListener(e -> cardLayout.show(panelPrincipal, "Libros"));
        btnUsuarios.addActionListener(e -> cardLayout.show(panelPrincipal, "Usuarios"));
        btnPrestamos.addActionListener(e -> cardLayout.show(panelPrincipal, "Prestamos"));
        btnCopias.addActionListener(e->cardLayout.show(panelPrincipal,"Copias"));
    }
    private void initLogoutPanel(){
        // Botón cerrar sesión en panel separado
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.X_AXIS));
        logoutPanel.setBackground(ComponentFactory.COLOR_MENU);
        logoutPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnCerrarSesion = ComponentFactory.crearBoton("Cerrar Sesión", ComponentFactory.ruta("menu-logout"));
        btnCerrarSesion.setBackground(new Color(231, 10, 10)); // Rojo para el botón de cerrar sesión
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 16));
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.addActionListener(e -> {
            SwingUtilities.invokeLater(()-> new LoginPrincipal().setVisible(true));
            this.dispose();
        });
        logoutPanel.add(btnCerrarSesion);
    }
    private void initPanelPrincipal(){
        // PANEL PRINCIPAL con CardLayout
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(ComponentFactory.COLOR_FONDO);
        // Crear instancias de los formularios
        panelDashboard.setBackground(ComponentFactory.COLOR_FONDO);
        panelDashboard.add(new JLabel("Dashboard - En construccion"));
        panelPrincipal.add(panelDashboard, "Dashboard");
        //Panel para los botones del menú
        PanelUsuario panelUsuarios = new PanelUsuario();
        panelPrincipal.add(panelUsuarios, "Usuarios");
        PanelLibro panelLibros = new PanelLibro();
        panelPrincipal.add(panelLibros, "Libros");
        PanelPrestamo panelPrestamos = new PanelPrestamo();
        panelPrincipal.add(panelPrestamos, "Prestamos");
        PanelEjemplar panelEjemplar = new PanelEjemplar();
        panelPrincipal.add(panelEjemplar,"Copias");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new BibliotecaPrincipal().setVisible(true));
    }
}
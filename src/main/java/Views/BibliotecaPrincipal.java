package main.java.Views;

import main.Resources.Shared.ColorComponent;
import main.Resources.Shared.RutaIcono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BibliotecaPrincipal extends JFrame {
    private final CardLayout cardLayout;
    private JPanel panelPrincipal;
    private final JPanel menuLateral;
    private final JPanel headerPanel;
    private final JPanel botonesPanel;
    private final JPanel logoutPanel;
    private final JPanel panelDashboard;

    public BibliotecaPrincipal(){
        setTitle("Sistema de Biblioteca");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(ColorComponent.COLOR_FONDO);
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
        menuLateral.setBackground(ColorComponent.COLOR_MENU);
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
        headerPanel.setBackground(ColorComponent.COLOR_MENU);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        //Título
        JLabel lbl_titulo = new JLabel("Biblioteca");
        lbl_titulo.setFont(new Font("Arial", Font.BOLD, 28));
        lbl_titulo.setForeground(Color.WHITE);
        lbl_titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Información de usuario
        JLabel lblUsuario = new JLabel("<html><center><b>Usuario:</b> Admin<br><b>Rol:</b> Bibliotecario</center></html>");
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
        botonesPanel.setBackground(ColorComponent.COLOR_MENU);
        botonesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Botones menú con íconos
        JButton btnDashboard = crearBotonMenu("Dashboard", RutaIcono.ruta("menu-dashboard"));
        JButton btnLibros = crearBotonMenu("Libros", RutaIcono.ruta("menu-books"));
        JButton btnUsuarios = crearBotonMenu("Usuarios", RutaIcono.ruta("menu-users"));
        JButton btnPrestamos = crearBotonMenu("Préstamos", RutaIcono.ruta("menu-loans"));
        // Agregar botones con espaciado
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnDashboard);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnLibros);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(btnPrestamos);

        // Agregar listeners a los botones
        btnDashboard.addActionListener(e -> cardLayout.show(panelPrincipal, "Dashboard"));
        btnLibros.addActionListener(e -> cardLayout.show(panelPrincipal, "Libros"));
        btnUsuarios.addActionListener(e -> cardLayout.show(panelPrincipal, "Usuarios"));
        btnPrestamos.addActionListener(e -> cardLayout.show(panelPrincipal, "Prestamos"));
    }
    private void initLogoutPanel(){
        // Botón cerrar sesión en panel separado
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.X_AXIS));
        logoutPanel.setBackground(ColorComponent.COLOR_MENU);
        logoutPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton btnCerrarSesion = crearBotonMenu("Cerrar Sesión", RutaIcono.ruta("menu-logout"));
        btnCerrarSesion.setBackground(new Color(211, 47, 47)); // Rojo para el botón de cerrar sesión
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 16));
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.addActionListener(e -> System.exit(0));
        logoutPanel.add(btnCerrarSesion);
    }
    private void initPanelPrincipal(){
        // PANEL PRINCIPAL con CardLayout
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(ColorComponent.COLOR_FONDO);
        // Crear instancias de los formularios
        panelDashboard.setBackground(ColorComponent.COLOR_FONDO);
        panelDashboard.add(new JLabel("Dashboard - En construccion"));
        panelPrincipal.add(panelDashboard, "Dashboard");
    }
    private JButton crearBotonMenu(String texto, String ruta){
        ImageIcon icono = new ImageIcon(ruta);
        Image imagen=icono.getImage().getScaledInstance(28,28,Image.SCALE_SMOOTH);
        icono = new ImageIcon(imagen);
        JButton boton = new JButton(texto, icono);
        boton.setMaximumSize(new Dimension(200, 40));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(ColorComponent.COLOR_MENU);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(15);
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                boton.setBackground(new Color(21, 101, 192));
            }
            public void mouseExited(MouseEvent e){
                boton.setBackground(ColorComponent.COLOR_MENU);
            }
        });
        return boton;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new BibliotecaPrincipal().setVisible(true));
    }
}

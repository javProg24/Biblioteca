package main.java.Views;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Metodos.ControladorBiblioteca;
import main.java.Models.Bibliotecario;
import main.java.Views.Principal.BibliotecaPrincipal;
import main.resources.Utils.ComponentFactory;
import main.resources.Utils.ImagePanel;
import main.resources.Utils.LabelField;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LoginPrincipal extends JFrame {
    private final JPanel panelLogin;
    private JPanel panelPortada;
    private JPanel camposPanel;
    private JPanel botonesPanel;
    private JTextField campoUsuario;
    private JTextField campoContrasena;
    public LoginPrincipal(){
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(ComponentFactory.COLOR_FONDO);
        setLayout(new BorderLayout());
        panelLogin = new JPanel();
        panelPortada =new JPanel();
        initComponents();
    }

    private void initComponents() {
        initPanelLogin();
        initPanelPortada();
        add(panelLogin,BorderLayout.WEST);
        add(panelPortada,BorderLayout.CENTER);
    }
    private void panelCampos() {
        camposPanel = new JPanel();
        camposPanel.setLayout(new BoxLayout(camposPanel, BoxLayout.Y_AXIS));
        camposPanel.setOpaque(false);

        LabelField usuarioField = new LabelField("Usuario");
        LabelField contrasenaField = new LabelField("Contraseña");

        campoUsuario = usuarioField.getTextField();
        campoContrasena = contrasenaField.getTextField();

        usuarioField.setAlignmentX(Component.CENTER_ALIGNMENT);
        contrasenaField.setAlignmentX(Component.CENTER_ALIGNMENT);

        camposPanel.add(usuarioField);
        camposPanel.add(Box.createVerticalStrut(20));
        camposPanel.add(contrasenaField);

        camposPanel.setMaximumSize(new Dimension(300, camposPanel.getPreferredSize().height));
    }

    private void panelBotones(){
        botonesPanel = new JPanel((new FlowLayout(FlowLayout.CENTER, 0, 0)));
        botonesPanel.setOpaque(false);
//        botonesPanel.setBackground(ComponentFactory.COLOR_EXITO);
        JButton btnIniciar = ComponentFactory.crearBoton("Iniciar Sesion",ComponentFactory.ruta("user"));
        botonesPanel.setMaximumSize(new Dimension(200, botonesPanel.getPreferredSize().height));
        botonesPanel.add(btnIniciar);
        btnIniciar.addActionListener(e->{
            Bibliotecario bibliotecario = crearBibliotecario();
            List<Map<String,Object>> dato=ControladorBiblioteca.validarBibliotecario(bibliotecario);
            boolean esValido=false;
            String nombreUsuario = campoUsuario.getText(); // Usar el nombre ingresado

            if(dato!=null&&!dato.isEmpty()){
                Object valor = dato.get(0).get("EsValido");
                if(valor instanceof Boolean){
                    esValido=(Boolean)valor;
                }

                // Obtener el nombre del usuario desde la respuesta de la validación
                if(esValido) {
                    Object usuario = dato.get(0).get("Usuario");
                    if(usuario != null) {
                        nombreUsuario = usuario.toString();
                    }
                    // Si no se encuentra en la respuesta, mantener el nombre
                }
            }

            final String nombreUsuarioFinal = nombreUsuario; // Variable final para usar en el lambda

            if (esValido) {
                SwingUtilities.invokeLater(()->new BibliotecaPrincipal(nombreUsuarioFinal).setVisible(true));
                this.dispose();
            } else {
                System.out.println("❌ Usuario o contraseña incorrectos");
            }
        });
    }
    private void initPanelPortada() {
        Image img = new ImageIcon("src/main/resources/Images/login-background.png").getImage();
        panelPortada = new ImagePanel(img);
    }

    private void initPanelLogin() {
        panelLogin.setLayout(new GridBagLayout());
        panelLogin.setBackground(ComponentFactory.COLOR_FONDO);
        panelLogin.setPreferredSize(new Dimension(350, getHeight()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ComponentFactory.COLOR_FONDO);

        JLabel lbl_titulo = new JLabel("Biblioteca");
        lbl_titulo.setFont(new Font("Arial", Font.BOLD, 28));
        lbl_titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lbl_titulo);
        contentPanel.add(Box.createVerticalStrut(20));

        panelCampos();
        camposPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        camposPanel.setMaximumSize(new Dimension(250, camposPanel.getPreferredSize().height));
        contentPanel.add(camposPanel);

        contentPanel.add(Box.createVerticalStrut(20));

        panelBotones();
        botonesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonesPanel.setMaximumSize(new Dimension(200, botonesPanel.getPreferredSize().height));
        contentPanel.add(botonesPanel);

        panelLogin.add(contentPanel, gbc);
    }
    private Bibliotecario crearBibliotecario(){
        Bibliotecario bibliotecario = new Bibliotecario();
        bibliotecario.setUsuario(campoUsuario.getText());
        bibliotecario.setContrasena(campoContrasena.getText());
        return bibliotecario;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new LoginPrincipal().setVisible(true));
    }
}

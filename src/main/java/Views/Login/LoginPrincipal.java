package main.java.Views.Login;

import com.formdev.flatlaf.FlatLightLaf;
import main.resources.Utils.ComponentFactory;
import main.resources.Utils.FloatingLabelField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginPrincipal extends JFrame {
    private JPanel panelLogin,
                        panelPortada,
                        camposPanel,
                        botonesPanel;
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

        FloatingLabelField usuarioField = new FloatingLabelField("Usuario");
        FloatingLabelField contrasenaField = new FloatingLabelField("Contraseña");

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
    }
    private void initPanelPortada() {
        panelPortada.setBackground(ComponentFactory.COLOR_MENU);
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new LoginPrincipal().setVisible(true));
    }
}

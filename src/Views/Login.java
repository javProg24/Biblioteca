package Views;

import com.formdev.flatlaf.FlatLightLaf;
import Controllers.Operadores.Metodos.ControladorBiblioteca;
import Models.Bibliotecario;
import resources.Utils.ComponentFactory;
import resources.Utils.ImagePanel;
import resources.Utils.LabelField;
import Views.Principal.BibliotecaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Login extends JFrame {
    private final JPanel panelLogin;
    private JPanel panelPortada;
    //private JPanel camposPanel;
    //private JPanel botonesPanel;
    private JTextField campoUsuario;
    private JTextField campoContrasena;
    public Login(){
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
    private JPanel panelCampos() {
        JPanel camposPanel = new JPanel();
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
        return camposPanel;
    }

    private JPanel panelBotones(){
        JPanel botonesPanel = new JPanel((new FlowLayout(FlowLayout.CENTER, 0, 0)));
        botonesPanel.setOpaque(false);
        JButton btnIniciar = ComponentFactory.crearBoton("Iniciar Sesion",ComponentFactory.ruta("user"),true);
        botonesPanel.setMaximumSize(new Dimension(200, botonesPanel.getPreferredSize().height));
        botonesPanel.add(btnIniciar);
        btnIniciar.addActionListener(e->{
            Bibliotecario bibliotecario = crearBibliotecario();
            if (bibliotecario == null) {
                // Los mensajes de error ya se muestran desde crearBibliotecario()
                return;
            }
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
                JOptionPane.showMessageDialog(this, "Usuario o contraseña inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return botonesPanel;
    }
    private void initPanelPortada() {
        Image img = new ImageIcon("src/resources/Images/login-background.png").getImage();
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

        JPanel camposPanel = panelCampos();
        camposPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(camposPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel botonesPanel = panelBotones();
        botonesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(botonesPanel);
        contentPanel.add(Box.createVerticalGlue());

        panelLogin.add(contentPanel, gbc);
    }
    private Bibliotecario crearBibliotecario() {
        String usuarioTexto = campoUsuario.getText().trim();
        String contrasenaTexto = campoContrasena.getText().trim();

        // Validar campos vacíos
        if (usuarioTexto.isEmpty() || contrasenaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un usuario y una contraseña.", "Campos obligatorios", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Crear el objeto solo si los datos están completos
        Bibliotecario bibliotecario = new Bibliotecario();
        bibliotecario.setUsuario(usuarioTexto);
        bibliotecario.setContrasena(contrasenaTexto);

        return bibliotecario;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}

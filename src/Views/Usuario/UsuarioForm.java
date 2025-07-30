package Views.Usuario;

import com.formdev.flatlaf.FlatLightLaf;
import Controllers.Operadores.Metodos.ControladorUsuario;
import Models.Usuario;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import resources.Shared.Notification.NotificationComponent;
import resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UsuarioForm extends JDialog {
    private JTextField txtNombre,
            txtApellido,
            txtDireccion,
            txtTelefono,
            txtFecha;
    private JButton btnCalendario;
    private boolean isEdit;
    private int idUsuario;
    private final SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    private JPopupMenu popupCalendar;
    private Runnable onUserSaved;

    public UsuarioForm(Frame parent,Runnable onUserSaved){
        super(parent, "Formulario de Usuario", true);
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.onUserSaved = onUserSaved;
        isEdit=false;
        idUsuario=0;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 500));
    }
    public UsuarioForm(Frame parent, int id, boolean isEdit,Runnable onUserSaved){
        super(parent,"Formulario de Usuario",true);
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.onUserSaved = onUserSaved;
        this.isEdit=isEdit;
        this.idUsuario=id;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 500));
        if(this.isEdit) editarUsuario();
    }
    private void editarUsuario(){
        Usuario usuario=new Usuario();
        usuario.setID(idUsuario);
        List<Map<String,Object>> datosUsuario= ControladorUsuario.obtenerUsuarioID(usuario);
        try {
            if(!datosUsuario.isEmpty()){
                Map<String, Object> usuarioData = datosUsuario.get(0);
                txtNombre.setText(String.valueOf(usuarioData.get("Nombre")));
                txtApellido.setText(String.valueOf(usuarioData.get("Apellido")));
                txtDireccion.setText(String.valueOf(usuarioData.get("Direccion")));
                txtTelefono.setText(String.valueOf(usuarioData.get("Telefono")));
                String fechaCampo = String.valueOf(usuarioData.get("Fecha_Nacimiento"));

                // 1. Parsear desde DB
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimiento = inputFormat.parse(fechaCampo);

                // 2. Mostrar en el formato deseado
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                txtFecha.setText(outputFormat.format(fechaNacimiento));
            }
            else {
                System.out.println("No hay datos");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        mainPanel.add(camposUsuario(),BorderLayout.CENTER);
        mainPanel.add(panelBotones(),BorderLayout.SOUTH);
        add(mainPanel,BorderLayout.CENTER);
    }
    private JPanel camposUsuario(){
        JPanel panelCampos=new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        gbc.anchor=GridBagConstraints.WEST;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        //Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNombre= ComponentFactory.crearEtiqueta("Nombre");
        panelCampos.add(lblNombre,gbc);
        gbc.gridx = 1;
        txtNombre=ComponentFactory.crearCampoTexto();
        panelCampos.add(txtNombre,gbc);
        //Apellido
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblApellido=ComponentFactory.crearEtiqueta("Apellido");
        panelCampos.add(lblApellido,gbc);
        gbc.gridx = 1;
        txtApellido=ComponentFactory.crearCampoTexto();
        panelCampos.add(txtApellido,gbc);
        //Direccion
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblDireccion=ComponentFactory.crearEtiqueta("Direccion");
        panelCampos.add(lblDireccion,gbc);
        gbc.gridx = 1;
        txtDireccion=ComponentFactory.crearCampoTexto();
        panelCampos.add(txtDireccion,gbc);
        //Telefono
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblTelefono=ComponentFactory.crearEtiqueta("Telefono");
        panelCampos.add(lblTelefono,gbc);
        gbc.gridx = 1;
        txtTelefono=ComponentFactory.crearCampoTexto();
        panelCampos.add(txtTelefono,gbc);
        //Fecha de Nacimiento
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblFecha=ComponentFactory.crearEtiqueta("Fecha de Nacimiento");
        panelCampos.add(lblFecha,gbc);
        gbc.gridx = 1;
        JPanel fechaPanel = new JPanel(new BorderLayout());
        txtFecha=ComponentFactory.crearCampoTexto();
        txtFecha.setEnabled(false);
        fechaPanel.add(txtFecha, BorderLayout.CENTER);
        btnCalendario = new JButton();
        btnCalendario.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        btnCalendario.setPreferredSize(new Dimension(40,30));
        fechaPanel.add(btnCalendario,BorderLayout.EAST);
        panelCampos.add(fechaPanel,gbc);
        JDatePanelImpl datePanel = createDatePanel();
        popupCalendar=new JPopupMenu();
        popupCalendar.setLayout(new BorderLayout());
        popupCalendar.add(datePanel,BorderLayout.CENTER);
        btnCalendario.addActionListener(e->toggleCalendar());
        return panelCampos;
    }
    private JPanel panelBotones(){
        JPanel panelBotones=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        //"Agregar",ComponentFactory.ruta("action-add")
        JButton btnGuardar = ComponentFactory.crearBoton(
                isEdit ? "Editar" : "Agregar",
                isEdit ? ComponentFactory.ruta("editar") : ComponentFactory.ruta("action-add"),true
        );
        JButton btnCancelar = ComponentFactory.crearBoton("Cancelar", ComponentFactory.ruta("action-cancel"),true);
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        btnGuardar.addActionListener(e->guardarUsuario());
        return panelBotones;
    }
    private void guardarUsuario(){
        Usuario usuario=crearUsuario();
        if(usuario==null){
            return;
        }
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        NotificationComponent panelComponent;
        boolean valido=isEdit? ControladorUsuario.actualizarUsuario(usuario):ControladorUsuario.crearUsuario(usuario);
        if (valido){
            //JOptionPane.showMessageDialog(this,"Registro"+(isEdit?" actualizado":" guardado"));
            panelComponent = new NotificationComponent(
                    frame,
                    isEdit?NotificationComponent.Type.INFORMACION :NotificationComponent.Type.EXITO,
                    NotificationComponent.Location.TOP_RIGHT,
                    "Registro"+(isEdit?" actualizado":" guardado")
            );
            panelComponent.showNotification();
            limpiarCampos();
            if (onUserSaved != null) {
                onUserSaved.run();  // <-- Ejecuta acción en el PanelUsuario
            }
        }
        else {
            panelComponent=new NotificationComponent(
                    frame,NotificationComponent.Type.ADVERTENCIA,NotificationComponent.Location.TOP_RIGHT,"Registro no guardado"
            );
            panelComponent.showNotification();
        }
        isEdit=false;
    }
    private JDatePanelImpl createDatePanel() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl dp = new JDatePanelImpl(model, p);
        dp.addActionListener(e -> {
            Date selectedDate = (Date) dp.getModel().getValue();
            if (selectedDate != null) {
                txtFecha.setText(dateFormat.format(selectedDate));
                popupCalendar.setVisible(false);
            }
        });
        return dp;
    }
    private void toggleCalendar() {
        if (popupCalendar.isVisible()) {
            popupCalendar.setVisible(false);
        } else {
            popupCalendar.show(btnCalendario, 0, btnCalendario.getHeight());
        }
    }
    private void limpiarCampos(){
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtFecha.setText("");
    }
    // crear usuario
    private Usuario crearUsuario() {
        // Obtener texto desde los campos
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefonoTexto = txtTelefono.getText().trim();
        String fechaTexto = txtFecha.getText().trim();

        // Validar campos vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || telefonoTexto.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Validar teléfono numérico y de 10 dígitos
        int telefono;
        if (!telefonoTexto.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener exactamente 10 dígitos numéricos.", "Teléfono inválido", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            telefono = Integer.parseInt(telefonoTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener solo números válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Validar fecha
        Date fechaNacimiento;
        try {
            fechaNacimiento = dateFormat.parse(fechaTexto);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Debe ser dd-MM-yyyy.", "Error de fecha", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Crear el objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setID(isEdit ? idUsuario : 0);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDirreccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setFecha_Nacimiento(fechaNacimiento);

        return usuario;
    }



}

package main.java.Views.Prestamo;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Metodos.ControladorEjemplar;
import main.java.Controllers.Operadores.Metodos.ControladorPrestamo;
import main.java.Controllers.Operadores.Metodos.ControladorUsuario;
import main.java.Models.Ejemplar;
import main.java.Models.Prestamo;
import main.resources.Shared.Notification.NotificationComponent;
import main.resources.Utils.ComponentFactory;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PrestamoForm extends JDialog {
    private JTextField txtFechaPrestamo,
            txtFechaDevolucion;
    private JComboBox<String> comboEstado_Libro,
                            comboEstado_Prestamo;
    private JButton btnFechaDevolucion,
            btnFechaPrestamo;
    private final SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    private Runnable onPrestamoSaved;
    private boolean isEdit;
    private int ID_Ejemplar;
    private int ID_Prestamo;
    private static boolean estadoEjemplar;
    private static boolean estadoPrestamo;
    private JComboBox<String> comboUsuarios;
    public PrestamoForm (Frame parent, int ID_Prestamo, int ID_Ejemplar,boolean isEdit,Runnable onPrestamoSaved){
        super(parent,"Formulario de Prestamo",true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.onPrestamoSaved = onPrestamoSaved;
        this.isEdit=isEdit;
        this.ID_Prestamo=ID_Prestamo;
        this.ID_Ejemplar=ID_Ejemplar;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack(); // Primero empaca el contenido
        setMinimumSize(new Dimension(450, 400));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = getSize();
        int x = (screenSize.width - dialogSize.width) / 2;
        int y = (screenSize.height - dialogSize.height) / 2;
        setLocation(x, y);
        //obtener el ID del usuario cuando seleccione la fila en la tabla de prestamo
        // con el ID del usuario se seleccione automáticamente en el comboUsuarios
        if(isEdit){
            obtenerNombresUsuarios();
            editarPrestamo();
        }
    }
    // Crear prestamo
    public PrestamoForm(Frame parent, int ID_Ejemplar, Runnable onPrestamoSaved){
        super(parent,"Formulario de Prestamo",true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.onPrestamoSaved = onPrestamoSaved;
        this.isEdit=false;
        this.ID_Ejemplar = ID_Ejemplar;
        //Centradoooo
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack(); // Primero empaca el contenido
        setMinimumSize(new Dimension(450, 400));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = getSize();
        int x = (screenSize.width - dialogSize.width) / 2;
        int y = (screenSize.height - dialogSize.height) / 2;
        setLocation(x, y);
        obtenerNombresUsuarios();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        mainPanel.add(camposPrestamo(),BorderLayout.CENTER);
        mainPanel.add(panelBotones(),BorderLayout.SOUTH);
        add(mainPanel,BorderLayout.CENTER);
    }
    private void editarPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setID(ID_Prestamo);
        Ejemplar ejemplar = new Ejemplar();
        prestamo.setID(ID_Ejemplar);
        List<Map<String,Object>> datosPrestamo=ControladorPrestamo.obtenerPrestamoID(prestamo);
        List<Map<String,Object>> datosEjemplar=ControladorEjemplar.obtenerEjemplarID(ejemplar);
        try {
            if (!datosPrestamo.isEmpty()){
                Map<String,Object> prestamoData=datosPrestamo.get(0);
                Map<String,Object> ejemplarData=datosEjemplar.get(0);
                String campoPrestamo=String.valueOf(prestamoData.get("Fecha_Prestamo"));
                String campoDevolucion=String.valueOf(prestamoData.get("Fecha_Devolucion"));
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaPrestamo = inputFormat.parse(campoPrestamo);
                Date fechaDevolucion = inputFormat.parse(campoDevolucion);
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                txtFechaPrestamo.setText(outputFormat.format(fechaPrestamo));
                txtFechaDevolucion.setText(outputFormat.format(fechaDevolucion));
                boolean estadoPrestamo = Boolean.parseBoolean(String.valueOf(prestamoData.get("Estado")));
                boolean estadoEjemplar= Boolean.parseBoolean(String.valueOf(ejemplarData.get("Estado")));
//                checkEstado.setSelected(estado);
                comboEstado_Libro.setSelectedItem(estadoEjemplar ? "No disponible" : "Disponible");
                comboEstado_Prestamo.setSelectedItem(estadoPrestamo?"Prestado":"Devuelto");
                int idUsuario = (int) prestamoData.get("ID_Usuario");
                for (Map.Entry<String, Integer> entry : mapaNombreId.entrySet()) {
                    if (entry.getValue() == idUsuario) {
                        comboUsuarios.setSelectedItem(entry.getKey());
                        break;
                    }
                }
            }
            else {
                System.out.println("No hay datos");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private JPanel camposPrestamo() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Fecha de Préstamo
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblFechaPrestamo = ComponentFactory.crearEtiqueta("Fecha de Préstamo");
        panelCampos.add(lblFechaPrestamo, gbc);
        gbc.gridx = 1;
        JPanel fechaPanelPrestamo = new JPanel(new BorderLayout());
        txtFechaPrestamo = ComponentFactory.crearCampoTexto();
        txtFechaPrestamo.setEnabled(false);
        fechaPanelPrestamo.add(txtFechaPrestamo, BorderLayout.CENTER);
        btnFechaPrestamo = new JButton();
        btnFechaPrestamo.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        btnFechaPrestamo.setPreferredSize(new Dimension(40, 30));
        fechaPanelPrestamo.add(btnFechaPrestamo, BorderLayout.EAST);
        panelCampos.add(fechaPanelPrestamo, gbc);
        JPopupMenu popupPrestamo = new JPopupMenu();
        popupPrestamo.setLayout(new BorderLayout());
        JDatePanelImpl datePanelPrestamo = createDatePanel(txtFechaPrestamo, popupPrestamo);
        popupPrestamo.add(datePanelPrestamo, BorderLayout.CENTER);
        btnFechaPrestamo.addActionListener(e -> toggleCalendar(popupPrestamo, btnFechaPrestamo));
        // Fecha de Devolución
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblFechaDevolucion = ComponentFactory.crearEtiqueta("Fecha de Devolución");
        panelCampos.add(lblFechaDevolucion, gbc);
        gbc.gridx = 1;
        JPanel fechaPanelDevolucion = new JPanel(new BorderLayout());
        txtFechaDevolucion = ComponentFactory.crearCampoTexto();
        txtFechaDevolucion.setEnabled(false);
        fechaPanelDevolucion.add(txtFechaDevolucion, BorderLayout.CENTER);
        btnFechaDevolucion = new JButton();
        btnFechaDevolucion.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        btnFechaDevolucion.setPreferredSize(new Dimension(40, 30));
        fechaPanelDevolucion.add(btnFechaDevolucion, BorderLayout.EAST);
        panelCampos.add(fechaPanelDevolucion, gbc);
        JPopupMenu popupDevolucion = new JPopupMenu();
        popupDevolucion.setLayout(new BorderLayout());
        JDatePanelImpl datePanelDevolucion = createDatePanel(txtFechaDevolucion, popupDevolucion);
        popupDevolucion.add(datePanelDevolucion, BorderLayout.CENTER);
        btnFechaDevolucion.addActionListener(e -> toggleCalendar(popupDevolucion, btnFechaDevolucion));
        // Estado del ejemplar
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblEstado = ComponentFactory.crearEtiqueta("Estado del Ejemplar: ");
        panelCampos.add(lblEstado,gbc);
        gbc.gridx = 1;
        comboEstado_Libro = new JComboBox<>();
        comboEstado_Libro.removeAllItems();
        comboEstado_Libro.addItem("--Seleccione--");
        //comboEstado_Libro.setSelectedIndex(0);
        comboEstado_Libro.addItem("Disponible");     // false
        comboEstado_Libro.addItem("No disponible");  // true
        comboEstado_Libro.addActionListener(e -> {
            String seleccionado = (String) comboEstado_Libro.getSelectedItem();
            estadoEjemplar = "No disponible".equals(seleccionado);
        });
        panelCampos.add(comboEstado_Libro,gbc);
        // Combo Estado del Prestamo (Prestado - Devuelto)
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblEstadoPrestamo = ComponentFactory.crearEtiqueta("Estado del Prestamo: ");
        panelCampos.add(lblEstadoPrestamo,gbc);
        gbc.gridx = 1;
        comboEstado_Prestamo=new JComboBox<>();
        comboEstado_Prestamo.removeAllItems();
        comboEstado_Prestamo.addItem("--Seleccione--");
//        comboEstado_Prestamo.setSelectedIndex(0);
        comboEstado_Prestamo.addItem("Prestado");
        comboEstado_Prestamo.addItem("Devuelto");
        comboEstado_Prestamo.addActionListener(e -> {
            String seleccionado = (String) comboEstado_Prestamo.getSelectedItem();
            estadoPrestamo = "Prestado".equals(seleccionado);
        });
//        String selecEstPres=(String) comboEstado_Prestamo.getSelectedItem();
//        estadoPrestamo="Reservado".equals(selecEstPres);
        panelCampos.add(comboEstado_Prestamo,gbc);
        //Combo de Usuarios
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblUsuarios = ComponentFactory.crearEtiqueta("Usuarios: ");
        panelCampos.add(lblUsuarios,gbc);
        gbc.gridx = 1;
        comboUsuarios=new JComboBox<>();
        comboUsuarios.addActionListener(e-> filtrarUsuario());
        panelCampos.add(comboUsuarios,gbc);
        return panelCampos;
    }
    private int idUsuario;
    private void filtrarUsuario() {
        String seleccionado = (String) comboUsuarios.getSelectedItem();
        if (seleccionado == null || seleccionado.equals("-- Selecciona un Usuario --")) {
            return;
        }
        if (mapaNombreId.containsKey(seleccionado)) {
            idUsuario = mapaNombreId.get(seleccionado);
            System.out.println("ID del usuario seleccionado: " + idUsuario);
        } else {
            System.out.println("Seleccione un usuario válido.");
        }
    }

    private JDatePanelImpl createDatePanel(JTextField campoFecha, JPopupMenu popup) {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl dp = new JDatePanelImpl(model, p);
        dp.addActionListener(e -> {
            Date selectedDate = (Date) dp.getModel().getValue();
            if (selectedDate != null) {
                campoFecha.setText(dateFormat.format(selectedDate));
                popup.setVisible(false);
            }
        });
        return dp;
    }

    private void toggleCalendar(JPopupMenu popup, JButton btn) {
        if (popup.isVisible()) {
            popup.setVisible(false);
        } else {
            popup.show(btn, 0, btn.getHeight());
        }
    }
    private JPanel panelBotones() {
        JPanel panelBotones=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        //"Agregar",ComponentFactory.ruta("action-add")
        JButton btnGuardar = ComponentFactory.crearBoton(
                isEdit ? "Editar" : "Agregar",
                isEdit ? ComponentFactory.ruta("editar") : ComponentFactory.ruta("action-add")
        );
        JButton btnCancelar = ComponentFactory.crearBoton("Cancelar", ComponentFactory.ruta("action-cancel"));
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        btnGuardar.addActionListener(e->guardarPrestamo());
        return panelBotones;
    }

    private void guardarPrestamo() {
        Prestamo prestamo = crearPrestamo();
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        NotificationComponent panelComponent;
        boolean valido= isEdit? ControladorPrestamo.actualizarPrestamo(prestamo):ControladorPrestamo.crearPrestamo(prestamo);
        if (valido){
            Ejemplar ejemplar = new Ejemplar();
            ejemplar.setID(ID_Ejemplar);
            ejemplar.setEstado(estadoEjemplar);
            boolean editado = ControladorEjemplar.actualizarEstadoEjemplar(ejemplar);
            if(editado){
                System.out.println("Editado");
            }
            else {
                System.out.println("No editado");
            }
            panelComponent=new NotificationComponent(
                    frame,
                    isEdit?NotificationComponent.Type.INFORMACION :NotificationComponent.Type.EXITO,
                    NotificationComponent.Location.TOP_RIGHT,
                    "Registro"+(isEdit?" actualizado":" guardado")
            );
            panelComponent.showNotification();
            limpiarCampos();
            if (onPrestamoSaved != null) {
                onPrestamoSaved.run();  // <-- Ejecuta acción en el PanelUsuario
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

    private void limpiarCampos() {
        txtFechaPrestamo.setText("");
        txtFechaDevolucion.setText("");
        comboUsuarios.setSelectedIndex(0);
        comboEstado_Libro.setSelectedIndex(0);
        comboEstado_Prestamo.setSelectedIndex(0);
    }

    private Prestamo crearPrestamo(){
        Prestamo prestamo = new Prestamo();
        prestamo.setID(isEdit?ID_Prestamo:0);
        String campoPrestamo=txtFechaPrestamo.getText().trim();
        String campoDevolucion=txtFechaDevolucion.getText().trim();
        if (campoPrestamo.isEmpty()) {
            if (campoDevolucion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de Prestamo y fecha de Devolucion", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        try {
            Date fechaPrestamo=dateFormat.parse(campoPrestamo);
            Date fechaDevolucion=dateFormat.parse(campoDevolucion);
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Debe ser dd-MM-yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        prestamo.setEstado(estadoPrestamo);
        prestamo.setID_Usuario(idUsuario);
        prestamo.setID_Ejemplar(ID_Ejemplar);
        return prestamo;
    }
    private Map<String, Integer> mapaNombreId = new HashMap<>();
    private void obtenerNombresUsuarios(){
        List<Map<String, Object>> datosUsuarios = ControladorUsuario.obtenerUsuarios();
        if (datosUsuarios == null || datosUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron usuarios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        comboUsuarios.removeAllItems();
        comboUsuarios.addItem("-- Selecciona un Usuario --");
        for(Map<String,Object>fila:datosUsuarios){
            String nombresCompletos = fila.get("Nombre") +" "+fila.get("Apellido");
            int id= (int) fila.get("ID");
            comboUsuarios.addItem(nombresCompletos);
            mapaNombreId.put(nombresCompletos, id);
        }
        //System.out.println(id);
        comboUsuarios.setSelectedIndex(0);
    }
}

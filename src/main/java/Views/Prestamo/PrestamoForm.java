package main.java.Views.Prestamo;

import com.formdev.flatlaf.FlatLightLaf;
import main.resources.Utils.ComponentFactory;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PrestamoForm extends JDialog {
    private JTextField txtFechaPrestamo,
            txtFechaDevolucion;
    private JCheckBox estado;
    private JButton btnFechaDevolucion,
            btnFechaPrestamo;
    private JPopupMenu popupCalendar;
    private final SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    private Runnable onUserSaved;
    private boolean isEdit;
    private int idUsuario;
    public PrestamoForm(Frame parent,Runnable onUserSaved){
        super(parent,"Formulario de Prestamo",true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.onUserSaved = onUserSaved;
        this.isEdit=false;
        this.idUsuario=0;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack(); // Primero empaca el contenido
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 400));
    }
    public PrestamoForm(Frame parent,int id,boolean isEdit,Runnable onUserSaved){
        super(parent,"Formulario de Prestamo",true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.onUserSaved = onUserSaved;
        this.isEdit=isEdit;
        this.idUsuario=id;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack(); // Primero empaca el contenido
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 500));
    }
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        mainPanel.add(camposPrestamo(),BorderLayout.CENTER);
        mainPanel.add(panelBotones(),BorderLayout.SOUTH);
        add(mainPanel,BorderLayout.CENTER);
    }

    private JPanel camposPrestamo() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.anchor = GridBagConstraints.WEST;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Fecha de Préstamo
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        JLabel lblFechaPrestamo = ComponentFactory.crearEtiqueta("Fecha de Préstamo");
//        panelCampos.add(lblFechaPrestamo, gbc);
//
//        gbc.gridx = 1;
//        JPanel fechaPanelPrestamo = new JPanel(new BorderLayout());
//        txtFechaPrestamo = ComponentFactory.crearCampoTexto();
//        txtFechaPrestamo.setEnabled(false);
//        fechaPanelPrestamo.add(txtFechaPrestamo, BorderLayout.CENTER);
//
//        btnFechaPrestamo = new JButton();
//        btnFechaPrestamo.setIcon(UIManager.getIcon("FileView.directoryIcon"));
//        btnFechaPrestamo.setPreferredSize(new Dimension(40, 30));
//        fechaPanelPrestamo.add(btnFechaPrestamo, BorderLayout.EAST);
//
//        panelCampos.add(fechaPanelPrestamo, gbc);
//
//        JPopupMenu popupPrestamo = new JPopupMenu();
//        popupPrestamo.setLayout(new BorderLayout());
//
//        JDatePanelImpl datePanelPrestamo = createDatePanel(txtFechaPrestamo, popupPrestamo);
//        popupPrestamo.add(datePanelPrestamo, BorderLayout.CENTER);
//
//        btnFechaPrestamo.addActionListener(e -> toggleCalendar(popupPrestamo, btnFechaPrestamo));
//
//
//        // Fecha de Devolución
//        gbc.gridx = 0;
//        gbc.gridy++;
//        JLabel lblFechaDevolucion = ComponentFactory.crearEtiqueta("Fecha de Devolución");
//        panelCampos.add(lblFechaDevolucion, gbc);
//
//        gbc.gridx = 1;
//        JPanel fechaPanelDevolucion = new JPanel(new BorderLayout());
//        txtFechaDevolucion = ComponentFactory.crearCampoTexto();
//        txtFechaDevolucion.setEnabled(false);
//        fechaPanelDevolucion.add(txtFechaDevolucion, BorderLayout.CENTER);
//
//        btnFechaDevolucion = new JButton();
//        btnFechaDevolucion.setIcon(UIManager.getIcon("FileView.directoryIcon"));
//        btnFechaDevolucion.setPreferredSize(new Dimension(40, 30));
//        fechaPanelDevolucion.add(btnFechaDevolucion, BorderLayout.EAST);
//
//        panelCampos.add(fechaPanelDevolucion, gbc);
//
//        JPopupMenu popupDevolucion = new JPopupMenu();
//        popupDevolucion.setLayout(new BorderLayout());
//
//        JDatePanelImpl datePanelDevolucion = createDatePanel(txtFechaDevolucion, popupDevolucion);
//        popupDevolucion.add(datePanelDevolucion, BorderLayout.CENTER);
//
//        btnFechaDevolucion.addActionListener(e -> toggleCalendar(popupDevolucion, btnFechaDevolucion));

        return panelCampos;
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
    }

}

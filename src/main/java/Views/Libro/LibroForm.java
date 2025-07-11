/*
package main.java.Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.Libro;
import main.resources.Shared.Notification.NotificationComponent;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LibroForm extends JDialog {

    private JTextField txtTitulo, txtAutor, txtAnio, txtISBN;
    private JComboBox<String> comboCategoria;
    private JButton btnGuardar, btnCancelar;

    private boolean isEdit;
    private int idLibro;
    private Runnable onLibroSaved;

    public LibroForm(Frame parent, Runnable onLibroSaved) {
        this(parent, 0, false, onLibroSaved);
    }

    public LibroForm(Frame parent, int id, boolean isEdit, Runnable onLibroSaved) {
        super(parent, isEdit ? "Editar Libro" : "Agregar Libro", true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.isEdit = isEdit;
        this.idLibro = id;
        this.onLibroSaved = onLibroSaved;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 400));

        if (isEdit) {
            cargarDatosLibro();
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.add(camposLibro(), BorderLayout.CENTER);
        mainPanel.add(panelBotones(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel camposLibro() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(ComponentFactory.crearEtiqueta("Título"), gbc);
        gbc.gridx = 1;
        txtTitulo = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtTitulo, gbc);

        // Autor
        gbc.gridx = 0;
        gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Autor"), gbc);
        gbc.gridx = 1;
        txtAutor = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtAutor, gbc);

        // Año de Publicación
        gbc.gridx = 0;
        gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Año de Publicación"), gbc);
        gbc.gridx = 1;
        txtAnio = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtAnio, gbc);

        // Categoría
        gbc.gridx = 0;
        gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Categoría"), gbc);
        gbc.gridx = 1;
        comboCategoria = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        panelCampos.add(comboCategoria, gbc);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("ISBN"), gbc);
        gbc.gridx = 1;
        txtISBN = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtISBN, gbc);

        return panelCampos;
    }

    private JPanel panelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        btnGuardar = ComponentFactory.crearBoton(
                isEdit ? "Editar" : "Agregar",
                isEdit ? ComponentFactory.ruta("editar") : ComponentFactory.ruta("action-add")
        );
        btnCancelar = ComponentFactory.crearBoton("Cancelar", ComponentFactory.ruta("action-cancel"));

        btnGuardar.addActionListener(e -> guardarLibro());
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        return panel;
    }

    private void guardarLibro() {
        try {
            Libro libro = new Libro();
            libro.setID(isEdit ? idLibro : 0);
            libro.setTitulo(txtTitulo.getText());
            libro.setAutor(txtAutor.getText());
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            libro.setCategoria(comboCategoria.getSelectedItem().toString());
            libro.setISBN(Integer.parseInt(txtISBN.getText()));

            //libro.setISBN(txtISBN.getText());

            boolean valido = isEdit
                    ? ControladorLibro.actualizarLibro(libro)
                    : ControladorLibro.crearLibro(libro);

            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            if (valido) {
                NotificationComponent panelComponent = new NotificationComponent(
                        frame,
                        isEdit ? NotificationComponent.Type.INFORMACION : NotificationComponent.Type.EXITO,
                        NotificationComponent.Location.TOP_RIGHT,
                        "Libro " + (isEdit ? "actualizado" : "registrado")
                );
                panelComponent.showNotification();
                if (onLibroSaved != null) onLibroSaved.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el libro.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año inválido. Debe ser un número.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
    }

    private void cargarDatosLibro() {
        Libro libro = new Libro();
        libro.setID(idLibro);
        List<Map<String, Object>> datosLibro = ControladorLibro.obtenerLibroID(libro);
        if (!datosLibro.isEmpty()) {
            Map<String, Object> row = datosLibro.get(0);
            txtTitulo.setText((String) row.get("Titulo"));
            txtAutor.setText((String) row.get("Autor"));
            txtAnio.setText(String.valueOf(row.get("AnioPublicacion")));
            comboCategoria.setSelectedItem(String.valueOf(row.get("Categoria")));
            txtISBN.setText((String) row.get("ISBN"));
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el libro.");
            dispose();
        }
    }
}*/
package main.java.Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.Libro;
import main.resources.Shared.Notification.NotificationComponent;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LibroForm extends JDialog {

    private JTextField txtISBN, txtTitulo, txtAnio, txtAutor;
    private JComboBox<String> comboCategoria;
    private JButton btnGuardar, btnCancelar;

    private boolean isEdit;
    private int idLibro;
    private Runnable onLibroSaved;

    public LibroForm(Frame parent, Runnable onLibroSaved) {
        this(parent, 0, false, onLibroSaved);
    }

    public LibroForm(Frame parent, int id, boolean isEdit, Runnable onLibroSaved) {
        super(parent, isEdit ? "Editar Libro" : "Agregar Libro", true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.isEdit = isEdit;
        this.idLibro = id;
        this.onLibroSaved = onLibroSaved;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 400));

        if (isEdit) {
            cargarDatosLibro();
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.add(camposLibro(), BorderLayout.CENTER);
        mainPanel.add(panelBotones(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel camposLibro() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(ComponentFactory.crearEtiqueta("ISBN"), gbc);
        gbc.gridx = 1;
        txtISBN = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtISBN, gbc);

        // Título
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Título"), gbc);
        gbc.gridx = 1;
        txtTitulo = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtTitulo, gbc);

        // Año de Publicación
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Año de Publicación"), gbc);
        gbc.gridx = 1;
        txtAnio = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtAnio, gbc);

        // Autor
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Autor"), gbc);
        gbc.gridx = 1;
        txtAutor = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtAutor, gbc);

        // Categoría
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Categoría"), gbc);
        gbc.gridx = 1;
        comboCategoria = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        panelCampos.add(comboCategoria, gbc);

        return panelCampos;
    }

    private JPanel panelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        btnGuardar = ComponentFactory.crearBoton(
                isEdit ? "Editar" : "Agregar",
                isEdit ? ComponentFactory.ruta("editar") : ComponentFactory.ruta("action-add")
        );
        btnCancelar = ComponentFactory.crearBoton("Cancelar", ComponentFactory.ruta("action-cancel"));

        btnGuardar.addActionListener(e -> guardarLibro());
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        return panel;
    }

    private void guardarLibro() {
        try {
            Libro libro = new Libro();
            libro.setID(isEdit ? idLibro : 0);
            libro.setISBN(Integer.parseInt(txtISBN.getText()));
            libro.setTitulo(txtTitulo.getText());
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            libro.setAutor(txtAutor.getText());
            libro.setCategoria(comboCategoria.getSelectedItem().toString());

            boolean valido = isEdit
                    ? ControladorLibro.actualizarLibro(libro)
                    : ControladorLibro.crearLibro(libro);

            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            if (valido) {
                NotificationComponent panelComponent = new NotificationComponent(
                        frame,
                        isEdit ? NotificationComponent.Type.INFORMACION : NotificationComponent.Type.EXITO,
                        NotificationComponent.Location.TOP_RIGHT,
                        "Libro " + (isEdit ? "actualizado" : "registrado")
                );
                panelComponent.showNotification();
                if (onLibroSaved != null) onLibroSaved.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el libro.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año o ISBN inválido. Deben ser números.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
    }

    private void cargarDatosLibro() {
        Libro libro = new Libro();
        libro.setID(idLibro);
        List<Map<String, Object>> datosLibro = ControladorLibro.obtenerLibroID(libro);
        if (!datosLibro.isEmpty()) {
            Map<String, Object> row = datosLibro.get(0);
            txtISBN.setText(String.valueOf(row.get("ISBN")));
            txtTitulo.setText((String) row.get("Titulo"));
            txtAnio.setText(String.valueOf(row.get("Anio_Publicacion"))); // usa el nombre correcto de la columna
            txtAutor.setText((String) row.get("Autor"));
            comboCategoria.setSelectedItem((String) row.get("Categoria"));
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el libro.");
            dispose();
        }
    }
}
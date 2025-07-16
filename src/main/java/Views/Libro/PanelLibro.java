/*package main.java.Views.Libro;

import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.Libro;
import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelLibro extends JPanel {
    private JTextField txtTitulo;

    public PanelLibro() {
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Gestión de Libros"));
        initComponents();
    }

    private void initComponents() {
        JPanel panelBotones = panelBotones();
        JPanel panelTabla = panelTabla();

        add(panelBotones, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
    }

    private JPanel panelBotones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelIzquierdo.setBackground(ComponentFactory.COLOR_FONDO);

        JLabel lblTitulo = ComponentFactory.crearEtiqueta("Título:");
        txtTitulo = ComponentFactory.crearCampoTexto();
        txtTitulo.setColumns(20);

        panelIzquierdo.add(lblTitulo);
        panelIzquierdo.add(txtTitulo);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setBackground(ComponentFactory.COLOR_FONDO);

        JButton btnConsultar = ComponentFactory.crearBoton("Buscar", ComponentFactory.ruta("action-search"));
        JButton btnAgregar = ComponentFactory.crearBoton("Agregar", ComponentFactory.ruta("action-add"));

        panelIzquierdo.add(btnConsultar);
        panelDerecho.add(btnAgregar);

        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.gridx = 0;
        gbcIzq.gridy = 0;
        gbcIzq.weightx = 1.0;
        gbcIzq.anchor = GridBagConstraints.WEST;
        gbcIzq.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints gbcDer = new GridBagConstraints();
        gbcDer.gridx = 1;
        gbcDer.gridy = 0;
        gbcDer.weightx = 1.0;
        gbcDer.anchor = GridBagConstraints.EAST;
        gbcDer.insets = new Insets(10, 10, 10, 10);

        panel.add(panelIzquierdo, gbcIzq);
        panel.add(panelDerecho, gbcDer);

        return panel;
    }

    private JPanel panelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        TableComponent<Libro> model = getLibroTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(model);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);

        List<Map<String, Object>> datosLibros = ControladorLibro.obtenerLibros(); //probando
        List<Libro> libros = datosLibros.stream().map(row -> Libro.builder()
                .ID((Integer) row.get("ID"))
                .Titulo((String) row.get("Titulo"))
                .Autor((String) row.get("Autor"))
                .AnioPublicacion((Integer) row.get("AnioPublicacion"))
                .Categoria(((String) row.get("Categoria")).charAt(0))
                .ISBN((String) row.get("ISBN"))
                .build()).toList();

        TableActionEvent actionEvent = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Editar libro en fila: " + row);
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Eliminar libro en fila: " + row);
            }
        };

        int colAcciones = tabla.getColumnCount() - 1;
        tabla.getColumnModel().getColumn(colAcciones)
                .setCellRenderer(new TableActionCellRenderer(actionEvent));
        tabla.getColumnModel().getColumn(colAcciones)
                .setCellEditor(new TableActionCellEditor(actionEvent));

        tabla.setRowHeight(40);
        model.addRows(libros);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static TableComponent<Libro> getLibroTableComponent() {
        List<Column<Libro>> columns = List.of(
                new Column<>("ID", Libro::getID, (l, v) -> l.setID((Integer) v)),
                new Column<>("Título", Libro::getTitulo, (l, v) -> l.setTitulo((String) v)),
                new Column<>("Autor", Libro::getAutor, (l, v) -> l.setAutor((String) v)),
                new Column<>("Año", Libro::getAnioPublicacion, (l, v) -> l.setAnioPublicacion((Integer) v)),
                new Column<>("Categoría",
                        libro -> String.valueOf(libro.getCategoria()),
                        (l, v) -> l.setCategoria(((String) v).charAt(0))),
                new Column<>("ISBN", Libro::getISBN, (l, v) -> l.setISBN((String) v)),
                new Column<>("Acciones", l -> null, (l, v) -> {})
        );
        return new TableComponent<>(columns);
    }
}*/
package main.java.Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.Libro;
import main.resources.Shared.Dialog.DialogComponent;
import main.resources.Shared.Notification.NotificationComponent;
import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelLibro extends JPanel {

    private JTextField txtTitulo;
    private JButton btnConsultar, btnAgregar, btnEjemplares;
    private TableComponent<Libro> modelLibro;

    public PanelLibro() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Gestión de Libros"));
        initComponents();
    }

    private void initComponents() {
        add(panelBotones(), BorderLayout.NORTH);
        add(panelTabla(), BorderLayout.CENTER);
    }

    private JPanel panelBotones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelIzquierdo.setBackground(ComponentFactory.COLOR_FONDO);

        JLabel lblTitulo = ComponentFactory.crearEtiqueta("Título:");
        txtTitulo = ComponentFactory.crearCampoTexto();
        txtTitulo.setColumns(20);
        panelIzquierdo.add(lblTitulo);
        panelIzquierdo.add(txtTitulo);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setBackground(ComponentFactory.COLOR_FONDO);

        btnConsultar = ComponentFactory.crearBoton("Buscar", ComponentFactory.ruta("action-search"));
        btnAgregar = ComponentFactory.crearBoton("Agregar", ComponentFactory.ruta("action-add"));
        btnEjemplares = ComponentFactory.crearBoton("Ejemplares", ComponentFactory.ruta("archivo")); // ícono arbitrario

        panelIzquierdo.add(btnConsultar);
        panelDerecho.add(btnAgregar);
        panelDerecho.add(btnEjemplares);

        btnConsultar.addActionListener(e -> cargarDatosLibros()); // O filtrado en futuro
        btnAgregar.addActionListener(e -> {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelLibro.this);
            LibroForm dialog = new LibroForm(parentFrame, this::cargarDatosLibros);
            dialog.setVisible(true);
        });

        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.gridx = 0;
        gbcIzq.gridy = 0;
        gbcIzq.weightx = 1.0;
        gbcIzq.anchor = GridBagConstraints.WEST;
        gbcIzq.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints gbcDer = new GridBagConstraints();
        gbcDer.gridx = 1;
        gbcDer.gridy = 0;
        gbcDer.weightx = 1.0;
        gbcDer.anchor = GridBagConstraints.EAST;
        gbcDer.insets = new Insets(10, 10, 10, 10);

        panel.add(panelIzquierdo, gbcIzq);
        panel.add(panelDerecho, gbcDer);

        return panel;
    }
    public void cargarDatosLibros() {
        List<Map<String, Object>> datosLibros = ControladorLibro.obtenerLibros();
        List<Libro> libros = datosLibros.stream().map(row -> {
            Integer id = (Integer) row.get("ID");
            String titulo = (String) row.get("Titulo");
            String autor = (String) row.get("Autor");
            Integer anio = (Integer) row.get("Anio_Publicacion");
            String categoria = (String) row.get("Categoria");

            Number isbnNumber = (Number) row.get("ISBN");
            int isbn = (isbnNumber != null) ? isbnNumber.intValue() : 0;

            return Libro.builder()
                    .ID(id != null ? id : 0)
                    .Titulo(titulo != null ? titulo : "")
                    .Autor(autor != null ? autor : "")
                    .Anio_Publicacion(anio != null ? anio : 0)
                    .Categoria(categoria != null ? categoria : "")
                    .ISBN(isbn)
                    .build();
        }).toList();

        modelLibro.clearRows();
        modelLibro.fireTableDataChanged();
        modelLibro.addRows(libros);
    }
    /*
    public void cargarDatosLibros() {
        List<Map<String, Object>> datosLibros = ControladorLibro.obtenerLibros();
        List<Libro> libros = datosLibros.stream().map(row -> Libro.builder()
                .ID((Integer) row.get("ID"))
                .Titulo((String) row.get("Titulo"))
                .Autor((String) row.get("Autor"))
                .AnioPublicacion((Integer) row.get("AnioPublicacion"))
                .Categoria((String) row.get("Categoria"))
                //.Categoria(((String) row.get("Categoria")).charAt(0))
                .ISBN(((Number) row.get("ISBN")).intValue())
                //.ISBN((String) row.get("ISBN"))
                .build()
        ).toList();

        modelLibro.clearRows();
        modelLibro.fireTableDataChanged();
        modelLibro.addRows(libros);
    }*/

    private JPanel panelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        modelLibro = getLibroTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(modelLibro);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);

        cargarDatosLibros();

        TableActionEvent actionEvent = new TableActionEvent() {
            private int id = 0;

            @Override
            public void onEdit(int row) {
                Libro libroSeleccionado = modelLibro.getRow(row);
                this.id = libroSeleccionado.getID();
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelLibro.this);
                LibroForm dialog = new LibroForm(parentFrame, id, true, () -> cargarDatosLibros());
                dialog.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                Libro libroSeleccionado = modelLibro.getRow(row);
                this.id = libroSeleccionado.getID();
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelLibro.this);
                DialogComponent ventanaEliminar = new DialogComponent(
                        parentFrame,
                        E_ROL._LIBRO, () -> {
                    Libro libro = new Libro();
                    libro.setID(id);
                    boolean eliminado = ControladorLibro.eliminarLibro(libro);
                    NotificationComponent panelComponent;
                    if (eliminado) {
                        panelComponent = new NotificationComponent(
                                parentFrame, NotificationComponent.Type.EXITO,
                                NotificationComponent.Location.TOP_RIGHT,
                                "Libro eliminado");
                        cargarDatosLibros();
                    } else {
                        panelComponent = new NotificationComponent(
                                parentFrame, NotificationComponent.Type.ADVERTENCIA,
                                NotificationComponent.Location.TOP_RIGHT,
                                "Ocurrió un error");
                    }
                    panelComponent.showNotification();
                });
                ventanaEliminar.setVisible(true);
            }
        };

        int colAcciones = tabla.getColumnCount() - 1;
        tabla.getColumnModel().getColumn(colAcciones).setCellRenderer(new TableActionCellRenderer(actionEvent));
        tabla.getColumnModel().getColumn(colAcciones).setCellEditor(new TableActionCellEditor(actionEvent));

        tabla.setRowHeight(40);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static TableComponent<Libro> getLibroTableComponent() {
        List<Column<Libro>> columns = List.of(
                new Column<>("ID", Libro::getID, (l, v) -> l.setID((Integer) v)),
                new Column<>("ISBN", Libro::getISBN, (l, v) -> l.setISBN((Integer) v)),
                new Column<>("Título", Libro::getTitulo, (l, v) -> l.setTitulo((String) v)),
                new Column<>("Año", Libro::getAnio_Publicacion, (l, v) -> l.setAnio_Publicacion((Integer) v)),
                new Column<>("Autor", Libro::getAutor, (l, v) -> l.setAutor((String) v)),
                new Column<>("Categoría", Libro::getCategoria, (l, v) -> l.setCategoria((String) v)),
                new Column<>("Acciones", l -> null, (l, v) -> {})
        );
        return new TableComponent<>(columns);
    }
}
package Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import Controllers.Operadores.Enums.E_ROL;
import Controllers.Operadores.Metodos.ControladorLibro;
import Models.Libro;
import resources.Shared.Dialog.DialogComponent;
import resources.Shared.Notification.NotificationComponent;
import resources.Shared.Table.*;
import resources.Utils.Column;
import resources.Utils.ComponentFactory;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class PanelLibro extends JPanel {

    private TableComponent<Libro> modelLibro;
    private JTextField txtTitulo;

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
        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                cargarDatosLibros();
                filtrarLibro();
            }
            @Override public void ancestorRemoved(AncestorEvent event) {}
            @Override public void ancestorMoved(AncestorEvent event) {}
        });
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
        //txtTitulo.addActionListener(e ->filtrarLibro() );
        txtTitulo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarLibro();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarLibro();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarLibro();

            }
        });
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setBackground(ComponentFactory.COLOR_FONDO);

        JButton btnConsultar = ComponentFactory.crearBoton("Buscar", ComponentFactory.ruta("action-search"),false);
        JButton btnAgregar = ComponentFactory.crearBoton("Agregar", ComponentFactory.ruta("action-add"),true);
//        JButton btnEjemplares = ComponentFactory.crearBoton("Ejemplares", ComponentFactory.ruta("archivo")); // ícono arbitrario

        panelIzquierdo.add(btnConsultar);
        panelDerecho.add(btnAgregar);
//        panelDerecho.add(btnEjemplares);
        btnConsultar.setCursor(Cursor.getDefaultCursor()); // Cursor normal
        for (ActionListener al : btnConsultar.getActionListeners()) {
            btnConsultar.removeActionListener(al); // Eliminar cualquier acción previa
        }
        btnConsultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                e.consume(); // Bloquea evento
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                e.consume(); // Bloquea evento
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume(); // Bloquea evento
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnConsultar.setCursor(Cursor.getDefaultCursor()); // Cursor normal
            }
        });
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
    private void filtrarLibro() {
        String libroSeleccionado = txtTitulo.getText().trim();
        List<Map<String, Object>> datosLibro = ControladorLibro.obtenerLibros();

        List<Libro> libros = datosLibro.stream()
                .filter(row -> {
                    String nombreLibro = (String) row.get("Titulo");

                    // Si el campo está vacío, mostrar todo
                    if (libroSeleccionado.isEmpty()) return true;

                    // Filtrar por coincidencias parciales (tipo buscador moderno)
                    return nombreLibro != null &&
                            nombreLibro.toLowerCase().contains(libroSeleccionado.toLowerCase());
                })
                .map(row -> {
                    Libro libro = new Libro();
                    libro.setID((Integer) row.get("ID"));
                    libro.setISBN((Integer) row.get("ISBN"));
                    libro.setTitulo((String) row.get("Titulo"));
                    libro.setAnio_Publicacion((Integer) row.get("Anio_Publicacion"));
                    libro.setAutor((String) row.get("Autor"));
                    libro.setCategoria((String) row.get("Categoria"));
                    return libro;
                })
                .toList();

        modelLibro.clearRows();
        modelLibro.addRows(libros);
    }


//    private void filtrarLibro() {
//        String libroSeleccionado = txtTitulo.getText();
//        if(libroSeleccionado.isEmpty()){
//            cargarDatosLibros();
//            return;
//        }
//        Libro libro = new Libro();
//        libro.setTitulo(libroSeleccionado);
//        List<Map<String, Object>> datosLibro = ControladorLibro.obtenerLibroTitulo(libro);
//        List<Libro>libroFiltrado=datosLibro.stream()
//                        .map(row->{
//                            Integer id = (Integer) row.get("ID");
//                            String titulo = (String) row.get("Titulo");
//                            String autor = (String) row.get("Autor");
//                            Integer anio = (Integer) row.get("Anio_Publicacion");
//                            String categoria = (String) row.get("Categoria");
//
//                            Number isbnNumber = (Number) row.get("ISBN");
//                            int isbn = (isbnNumber != null) ? isbnNumber.intValue() : 0;
//
//                            return Libro.builder()
//                                    .ID(id != null ? id : 0)
//                                    .Titulo(titulo != null ? titulo : "")
//                                    .Autor(autor != null ? autor : "")
//                                    .Anio_Publicacion(anio != null ? anio : 0)
//                                    .Categoria(categoria != null ? categoria : "")
//                                    .ISBN(isbn)
//                                    .build();
//                        }).toList();
//        modelLibro.clearRows();
//        modelLibro.addRows(libroFiltrado);
//    }

    public void cargarDatosLibros() {
        filtrarLibro();
//        List<Map<String, Object>> datosLibros = ControladorLibro.obtenerLibros();
//        List<Libro> libros = datosLibros.stream().map(row -> {
//            Integer id = (Integer) row.get("ID");
//            String titulo = (String) row.get("Titulo");
//            String autor = (String) row.get("Autor");
//            Integer anio = (Integer) row.get("Anio_Publicacion");
//            String categoria = (String) row.get("Categoria");
//
//            Number isbnNumber = (Number) row.get("ISBN");
//            int isbn = (isbnNumber != null) ? isbnNumber.intValue() : 0;
//
//            return Libro.builder()
//                    .ID(id != null ? id : 0)
//                    .Titulo(titulo != null ? titulo : "")
//                    .Autor(autor != null ? autor : "")
//                    .Anio_Publicacion(anio != null ? anio : 0)
//                    .Categoria(categoria != null ? categoria : "")
//                    .ISBN(isbn)
//                    .build();
//        }).toList();
//
//        modelLibro.clearRows();
//        //modelLibro.fireTableDataChanged();
//        modelLibro.addRows(libros);
    }

    private JPanel panelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        modelLibro = getLibroTableComponent();
        cargarDatosLibros();
        JTable tabla = TableFactory.crearTablaEstilo(modelLibro);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);

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
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }
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
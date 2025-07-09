package main.java.Views.Libro;

import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;
import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelLibro extends JPanel {
    private JTextField txtISBN;
    private ControladorLibro controladorLibro;
    
    public PanelLibro() {
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Libros"));
        controladorLibro = new ControladorLibro();
        initComponents();
    }
    
    private void initComponents(){
        //2 JPanel, Panel de botones y panel de tabla
        JPanel panelBotones = panelBotones();
        JPanel panelTabla = panelTabla();

        add(panelBotones, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
    }
    
    private JPanel panelBotones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        // ---- Subpanel izquierdo: etiqueta + campo ----
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelIzquierdo.setBackground(ComponentFactory.COLOR_FONDO);

        JLabel lblISBN = ComponentFactory.crearEtiqueta("ISBN:");
        txtISBN = ComponentFactory.crearCampoTexto();
        txtISBN.setColumns(15);

        panelIzquierdo.add(lblISBN);
        panelIzquierdo.add(txtISBN);

        // ---- Subpanel derecho: botones ----
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setBackground(ComponentFactory.COLOR_FONDO);

        JButton btnConsultar = ComponentFactory.crearBoton("Buscar", ComponentFactory.ruta("action-search"));
        JButton btnAgregar = ComponentFactory.crearBoton("Agregar", ComponentFactory.ruta("action-add"));

        panelIzquierdo.add(btnConsultar);
        panelDerecho.add(btnAgregar);

        // ---- GridBagConstraints para subpanel izquierdo ----
        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.gridx = 0;
        gbcIzq.gridy = 0;
        gbcIzq.weightx = 1.0;      // Para ocupar espacio disponible
        gbcIzq.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        gbcIzq.insets = new Insets(10, 10, 10, 10); // Márgenes

        // ---- GridBagConstraints para subpanel derecho ----
        GridBagConstraints gbcDer = new GridBagConstraints();
        gbcDer.gridx = 1;
        gbcDer.gridy = 0;
        gbcDer.weightx = 1.0;      // Para ocupar espacio disponible
        gbcDer.anchor = GridBagConstraints.EAST; // Alinear a la derecha
        gbcDer.insets = new Insets(10, 10, 10, 10); // Márgenes

        // ---- Agregar subpaneles ----
        panel.add(panelIzquierdo, gbcIzq);
        panel.add(panelDerecho, gbcDer);

        return panel;
    }
    
    private JPanel panelTabla(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        TableComponent<Libro> model = getLibroTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(model);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        List<Map<String,Object>> datosLibros = controladorLibro.obtenerLibros();
        List<Libro> libros = datosLibros.stream().map(
                row -> {
                    Libro libro = new Libro();
                    libro.setID((Integer) row.get("ID"));
                    libro.setTitulo((String) row.get("Titulo"));
                    libro.setAutor((String) row.get("Autor"));
                    libro.setAnioPublicacion((Integer) row.get("Anio_Publicacion"));
                    libro.setCategoria(((String) row.get("Categoria")).charAt(0));
                    libro.setISBN((String) row.get("ISBN"));
                    return libro;
                }
        ).toList();
        TableActionEvent actionEvent = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Editar fila: " + row);
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Eliminar fila: " + row);
            }
        };
        int colAcciones = tabla.getColumnCount()-1;
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
                new Column<>("ID",
                        Libro::getID,
                        (l, v) -> l.setID((Integer) v)
                ),
                new Column<>("ISBN",
                        Libro::getISBN,
                        (l, v) -> l.setISBN((String) v)
                ),
                new Column<>("Título",
                        Libro::getTitulo,
                        (l, v) -> l.setTitulo((String) v)
                ),
                new Column<>("Autor",
                        Libro::getAutor,
                        (l, v) -> l.setAutor((String) v)
                ),
                new Column<>("Año Publicación",
                        Libro::getAnioPublicacion,
                        (l, v) -> l.setAnioPublicacion((Integer) v)
                ),
                new Column<>("Categoría",
                        libro -> String.valueOf(libro.getCategoria()),
                        (l, v) -> l.setCategoria(((String) v).charAt(0))
                ),
                new Column<>("Acciones", l -> null, (l, v) -> {})
        );
        return new TableComponent<>(columns);
    }
}

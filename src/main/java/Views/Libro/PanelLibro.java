package main.java.Views.Libro;

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

        List<Map<String, Object>> datosLibros = ControladorLibro.obtenerLibros();
        List<Libro> libros = datosLibros.stream().map(row -> {
            return Libro.builder()
                    .ID((Integer) row.get("ID"))
                    .Titulo((String) row.get("Titulo"))
                    .Autor((String) row.get("Autor"))
                    .AnioPublicacion((Integer) row.get("AnioPublicacion"))
                    .Categoria(((String) row.get("Categoria")).charAt(0))
                    .ISBN((String) row.get("ISBN"))
                    .build();
        }).toList();

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
}

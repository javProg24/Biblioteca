package main.java.Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Metodos.ControladorEjemplar;
import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.EjemplarDTO;
import main.java.Views.Prestamo.PrestamoForm;
import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelEjemplar extends JPanel {
    private TableComponent<EjemplarDTO> modelEjemplar;
    JComboBox<String> comboTitulos;
    JComboBox<String> comboEstado;

    public PanelEjemplar(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Gestion"));
        initComponents();

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                obtenerTitulosLibros();
                filtrarEjemplares();
            }
            @Override public void ancestorRemoved(AncestorEvent event) {}
            @Override public void ancestorMoved(AncestorEvent event) {}
        });
    }

    private void initComponents() {
        comboTitulos = new JComboBox<>();
        comboTitulos.setBounds(50, 30, 180, 25);
        comboEstado = new JComboBox<>(new String[]{"-- Estado --", "Disponible", "No disponible"});
        comboEstado.setBounds(250, 30, 180, 25);
        comboTitulos.addActionListener(e -> filtrarEjemplares());
        comboEstado.addActionListener(e -> filtrarEjemplares());
        add(panelSuperior(),BorderLayout.NORTH);
        add(panelTabla(),BorderLayout.CENTER);
//        obtenerTitulosLibros();
    }

    private JPanel panelSuperior() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelIzquierdo.setBackground(ComponentFactory.COLOR_FONDO);
        JLabel lblTitulo = ComponentFactory.crearEtiqueta("Titulo: ");
        panelIzquierdo.add(lblTitulo);
        panelIzquierdo.add(comboTitulos);
        JLabel lblEstado = ComponentFactory.crearEtiqueta("Estado: ");
        panelIzquierdo.add(lblEstado);
        panelIzquierdo.add(comboEstado);
        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.gridx = 0;
        gbcIzq.gridy = 0;
        gbcIzq.weightx = 1.0;
        gbcIzq.anchor = GridBagConstraints.EAST;
        gbcIzq.insets = new Insets(10, 10, 10, 10);
        panel.add(panelIzquierdo,gbcIzq);
        return panel;
    }

    private JPanel panelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        modelEjemplar=getEjemplarTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(modelEjemplar);

        tabla.setRowSelectionAllowed(false);
        tabla.setColumnSelectionAllowed(false);
        tabla.setCellSelectionEnabled(false);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        cargarDatosEjemplares();
        TableActionEvent actionEvent = new TableActionEvent() {
            private int ID_Ejemplar =0;
            @Override
            public void onEdit(int row) {
                // hola
                EjemplarDTO ejemplarSeleccionado=modelEjemplar.getRow(row);
                this.ID_Ejemplar =ejemplarSeleccionado.getID();
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelEjemplar.this);
                PrestamoForm dialog = new PrestamoForm(parentFrame, ID_Ejemplar, () -> cargarDatosEjemplares());
                dialog.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }
            }
        };
        int colAcciones=tabla.getColumnCount()-1;
        tabla.getColumnModel().getColumn(colAcciones).setCellRenderer(new TableActionCellRenderer(actionEvent));
        tabla.getColumnModel().getColumn(colAcciones).setCellEditor(new TableActionCellEditor(actionEvent,3));
        tabla.setRowHeight(40);
        //modelUsuario.addRows(usuarios);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    private void obtenerTitulosLibros(){
        List<Map<String, Object>> datosTitulo = ControladorLibro.obtenerLibros();

        comboTitulos.removeAllItems(); // Limpia si ya tenía datos
        comboTitulos.addItem("-- Selecciona un libro --"); // Opción por defecto

        for (Map<String, Object> fila : datosTitulo) {
            String titulo = (String) fila.get("Titulo"); // Asegúrate que la clave es exacta
            if (titulo != null) {
                comboTitulos.addItem(titulo); // Añade solo el título al combo
            }
        }
        comboTitulos.setSelectedIndex(0); // No seleccionado
    }
    private void filtrarEjemplares() {
        String libroSeleccionado = (String) comboTitulos.getSelectedItem();
        String estadoSeleccionado = (String) comboEstado.getSelectedItem();
        List<Map<String,Object>> datosEjemplares = ControladorEjemplar.obtenerEjemplares();
        List<EjemplarDTO> ejemplares = datosEjemplares.stream()
                .filter(row -> {
                    boolean libroOk = comboTitulos.getSelectedIndex() == 0
                            || (libroSeleccionado != null && libroSeleccionado.equals(row.get("Nombre_Libro")));
                    boolean estadoOk = comboEstado.getSelectedIndex() == 0
                            || ("Disponible".equals(estadoSeleccionado) && !(boolean)row.get("Estado"))
                            || ("No disponible".equals(estadoSeleccionado) && (boolean)row.get("Estado"));
                    return libroOk && estadoOk;
                })
                .map(row -> {
                    EjemplarDTO ejemplar = new EjemplarDTO();
                    ejemplar.setID((Integer) row.get("ID"));
                    ejemplar.setCodigo_Interno((String) row.get("Codigo_Interno"));
                    ejemplar.setNombreLibro((String) row.get("Nombre_Libro"));
                    ejemplar.setEstado((boolean) row.get("Estado"));
                    return ejemplar;
                })
                .toList();
        modelEjemplar.clearRows();
        modelEjemplar.fireTableDataChanged();
        modelEjemplar.addRows(ejemplares);
    }
    private void cargarDatosEjemplares(){
        filtrarEjemplares();
    }



    private static TableComponent<EjemplarDTO> getEjemplarTableComponent() {
        List<Column<EjemplarDTO>> columns = List.of(
                new Column<>(
                        "ID",
                        EjemplarDTO::getID,
                        (e,v)->e.setID_Libro((Integer)v)
                ),
                new Column<>(
                        "Código Interno",
                        EjemplarDTO::getCodigo_Interno,
                        (e,v)->e.setCodigo_Interno((String) v)
                ),
                new Column<>(
                        "Título Libro",
                        EjemplarDTO::getNombreLibro,
                        (e,v)->e.setNombreLibro((String) v)
                ),
                new Column<>(
                        "Estado",
                        e -> e.getEstado() ? "No disponible" : "Disponible",
                        (e,v) -> e.setEstado("No disponible".equals(v))
                ),
                new Column<>(
                        "Acciones",
                        e->null,
                        (e,v)->{}
                )
        );
        return new TableComponent<>(columns);
    }
    private class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {
        private final JTable table;
        private final TableActionEvent actionEvent;
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        private final JButton btnEditar = new JButton("Editar");
        private int currentRow = -1;

        public ActionCellEditor(JTable table, TableActionEvent actionEvent) {
            this.table = table;
            this.actionEvent = actionEvent;
            btnEditar.addActionListener(e -> {
                fireEditingStopped();
                actionEvent.onEdit(currentRow);
            });
            panel.add(btnEditar);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            String estado = (String) table.getValueAt(row, 3);
            boolean disponible = !"No disponible".equals(estado);
            btnEditar.setEnabled(disponible);
            btnEditar.setToolTipText(disponible ? null : "Ejemplar no disponible");
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

}

package main.java.Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Metodos.ControladorEjemplar;
import main.java.Controllers.Operadores.Metodos.ControladorLibro;
import main.java.Models.Ejemplar;
import main.java.Models.EjemplarDTO;
import main.java.Models.Libro;
import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelEjemplar extends JPanel {
    private TableComponent<EjemplarDTO> modelEjemplar;
    JComboBox<String> comboBox;
    public PanelEjemplar(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        comboBox = new JComboBox<>();
        comboBox.setBounds(50, 30, 180, 25);
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Gestion Prestamos"));
        initComponents();
        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                obtenerTitulosLibros(); // ← Cargar títulos al mostrarse
                cargarDatosEjemplares();
            }
            @Override public void ancestorRemoved(AncestorEvent event) {}
            @Override public void ancestorMoved(AncestorEvent event) {}
        });
    }

    private void initComponents() {
        add(panelSuperior(),BorderLayout.NORTH);
        add(panelTabla(),BorderLayout.CENTER);
//        obtenerTitulosLibros();
    }

    private JPanel panelSuperior() {
        JPanel panel = new JPanel();
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelIzquierdo.setBackground(ComponentFactory.COLOR_FONDO);
        JLabel lblTitulo = ComponentFactory.crearEtiqueta("Titulo: ");

//        obtenerTitulosLibros();
        panelIzquierdo.add(lblTitulo);
        panelIzquierdo.add(comboBox);

        GridBagConstraints gbcIzq = new GridBagConstraints();
        gbcIzq.gridx = 0;
        gbcIzq.gridy = 0;
        gbcIzq.weightx = 1.0;      // Para ocupar espacio disponible
        gbcIzq.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
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
            @Override
            public void onEdit(int row) {

            }

            @Override
            public void onDelete(int row) {

            }
        };
        int colAcciones=tabla.getColumnCount()-1;
        tabla.getColumnModel().getColumn(colAcciones).setCellRenderer(new TableActionCellRenderer(actionEvent));
        tabla.getColumnModel().getColumn(colAcciones).setCellEditor(new TableActionCellEditor(actionEvent));
        tabla.setRowHeight(40);
        //modelUsuario.addRows(usuarios);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    private void obtenerTitulosLibros(){
        List<Map<String, Object>> datosTitulo = ControladorLibro.obtenerLibros();

        comboBox.removeAllItems(); // Limpia si ya tenía datos

        for (Map<String, Object> fila : datosTitulo) {
            String titulo = (String) fila.get("Titulo"); // Asegúrate que la clave es exacta
            if (titulo != null) {
                comboBox.addItem(titulo); // Añade solo el título al combo
            }
        }
    }
    private void cargarDatosEjemplares(){
        List<Map<String,Object>> datosEjemplares= ControladorEjemplar.obtenerEjemplares();
        List<EjemplarDTO> ejemplares = datosEjemplares.stream().map(
                row->{
                    EjemplarDTO ejemplar = new EjemplarDTO();
                    ejemplar.setID((Integer)row.get("ID"));
                    ejemplar.setCodigo_Interno((String) row.get("Codigo_Interno"));
                    ejemplar.setNombreLibro((String) row.get("Nombre_Libro"));
//                    ejemplar.setID_Libro((Integer)row.get("ID_Libro"));
                    ejemplar.setEstado((boolean) row.get("Estado"));
                    return ejemplar;
                }
        ).toList();
        modelEjemplar.clearRows();
        modelEjemplar.fireTableDataChanged();
        modelEjemplar.addRows(ejemplares);
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
//                new Column<>(
//                        "ID Libro",
//                        EjemplarDTO::getID_Libro,
//                        (e,v)->e.setID_Libro((Integer)v)
//                ),
                new Column<>(
                        "Estado",
                        EjemplarDTO::getEstado,
                        (e,v)->e.setEstado((boolean) v)
                ),
                new Column<>(
                        "Acciones",
                        e->null,
                        (e,v)->{}
                )
        );
        return new TableComponent<>(columns);
    }
}

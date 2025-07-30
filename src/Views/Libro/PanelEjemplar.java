package Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import Controllers.Operadores.Enums.E_ROL;
import Controllers.Operadores.Metodos.ControladorEjemplar;
import Controllers.Operadores.Metodos.ControladorLibro;
import Models.Ejemplar;
import Models.EjemplarDTO;
import resources.Shared.Dialog.DialogComponent;
import resources.Shared.Notification.NotificationComponent;
import resources.Shared.Table.*;
import resources.Utils.Column;
import resources.Utils.ComponentFactory;
import Views.Prestamo.PrestamoForm;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
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
//                modelEjemplar=getEjemplarTableComponent();
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
                EjemplarDTO ejemplarSeleccionado=modelEjemplar.getRow(row);
                ID_Ejemplar=ejemplarSeleccionado.getID();
                Frame parent=(Frame) SwingUtilities.getWindowAncestor(PanelEjemplar.this);
                DialogComponent ventana=new DialogComponent(
                        parent,
                        E_ROL._EJEMPLAR,
                        ()->{
                            Ejemplar prestamo=new Ejemplar();
                            prestamo.setID(ID_Ejemplar);
                            boolean eliminado= ControladorEjemplar.eliminarEjemplar(prestamo);
                            NotificationComponent notification;
                            if(eliminado){
                                notification=new NotificationComponent(parent,NotificationComponent.Type.EXITO,NotificationComponent.Location.TOP_RIGHT,"Ejemplar eliminado");
                                cargarDatosEjemplares();
                            }else {
                                notification=new NotificationComponent(parent,NotificationComponent.Type.EXITO,NotificationComponent.Location.TOP_RIGHT,"Ejemplar eliminado");
                            }
                            notification.showNotification();
                        }
                );
                ventana.setVisible(true);
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

        comboTitulos.removeAllItems();
        comboTitulos.addItem("-- Selecciona un libro --");

        for (Map<String, Object> fila : datosTitulo) {
            String titulo = (String) fila.get("Titulo");
            if (titulo != null) {
                comboTitulos.addItem(titulo);
            }
        }
        comboTitulos.setSelectedIndex(0);
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
}

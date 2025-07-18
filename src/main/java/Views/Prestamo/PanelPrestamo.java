package main.java.Views.Prestamo;

import main.java.Controllers.Operadores.Enums.E_ROL;
import main.java.Controllers.Operadores.Metodos.ControladorPrestamo;
import main.java.Models.Prestamo;
import main.java.Models.PrestamoDTO;
import main.resources.Shared.Dialog.DialogComponent;
import main.resources.Shared.Notification.NotificationComponent;
import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PanelPrestamo extends JPanel {
    private JTextField txtID;
    private TableComponent<PrestamoDTO> tablaPrestamo;

    public PanelPrestamo() {
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Préstamos"));
        initComponents();

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                cargarPrestamos();
            }
            @Override public void ancestorRemoved(AncestorEvent event) {}
            @Override public void ancestorMoved(AncestorEvent event) {}
        });
    }

    private void initComponents(){
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

        JLabel lblID = ComponentFactory.crearEtiqueta("Usuario:");
        txtID = ComponentFactory.crearCampoTexto();
        txtID.setColumns(15);

        panelIzquierdo.add(lblID);
        panelIzquierdo.add(txtID);

        txtID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarUsuario();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarUsuario();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarUsuario();
            }
        });

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

    private JPanel panelTabla(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        tablaPrestamo = getPrestamoTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(tablaPrestamo);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);

        TableActionEvent actionEvent = new TableActionEvent() {
            private int ID_Prestamo = 0;
            @Override
            public void onEdit(int row) {
                PrestamoDTO prestamoSeleccionado= tablaPrestamo.getRow(row);
                this.ID_Prestamo =prestamoSeleccionado.getID();
                int ejemplar = prestamoSeleccionado.getID_Ejemplar();
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(PanelPrestamo.this);
                PrestamoForm dialog = new PrestamoForm(
                        parent,
                        ID_Prestamo,
                        ejemplar,true,
                        ()->cargarPrestamos()
                );
                dialog.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }
                PrestamoDTO prestamoSeleccionado= tablaPrestamo.getRow(row);
                this.ID_Prestamo =prestamoSeleccionado.getID();
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(PanelPrestamo.this);
                DialogComponent ventana=new DialogComponent(
                        parent,
                        E_ROL._PRESTAMO,
                        ()->{
                            Prestamo prestamo=new Prestamo();
                            prestamo.setID(ID_Prestamo);
                            boolean eliminado=ControladorPrestamo.eliminarPrestamo(prestamo);
                            NotificationComponent notification;
                            if(eliminado){
                                notification=new NotificationComponent(parent,NotificationComponent.Type.EXITO,NotificationComponent.Location.TOP_RIGHT,"Prestamo eliminado");
                                cargarPrestamos();
                            }else {
                                notification=new NotificationComponent(parent,NotificationComponent.Type.EXITO,NotificationComponent.Location.TOP_RIGHT,"Prestamo eliminado");
                            }
                            notification.showNotification();
                        }
                );
                ventana.setVisible(true);
//                System.out.println("Eliminar fila: " + row);
            }
        };

        int colAcciones = tabla.getColumnCount() - 1;
        tabla.getColumnModel().getColumn(colAcciones)
                .setCellRenderer(new TableActionCellRenderer(actionEvent));
        tabla.getColumnModel().getColumn(colAcciones)
                .setCellEditor(new TableActionCellEditor(actionEvent));
        tabla.setRowHeight(40);

        cargarPrestamos(); // Carga inicial

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static TableComponent<PrestamoDTO> getPrestamoTableComponent() {
        List<Column<PrestamoDTO>> columns = List.of(
                new Column<>("ID", PrestamoDTO::getID, (p, v) -> p.setID((Integer) v)),
                new Column<>("Usuario", PrestamoDTO::getUsuario, (p, v) -> p.setUsuario((String) v)),
                new Column<>("Libro", PrestamoDTO::getLibro, (p, v) -> p.setLibro((String) v)),
                new Column<>("Codigo", PrestamoDTO::getCodigo_Ejemplar, (p, v) -> p.setCodigo_Ejemplar((String) v)),
                new Column<>("ID Ejemplar",PrestamoDTO::getID_Ejemplar,(p,v)->p.setID_Ejemplar((Integer)v)),
                new Column<>("Fecha Préstamo",
                        prestamo -> {
                            Date fecha = prestamo.getFechaPrestamo();
                            return fecha == null ? "" : new SimpleDateFormat("dd-MM-yyyy").format(fecha);
                        },
                        (p, v) -> {
                            try {
                                if (v == null || ((String) v).isEmpty()) {
                                    p.setFechaPrestamo(null);
                                } else {
                                    Date fecha = new SimpleDateFormat("dd-MM-yyyy").parse((String) v);
                                    p.setFechaPrestamo(fecha);
                                }
                            } catch (Exception e) {
                                System.err.println("Error parseando fecha de préstamo: " + e.getMessage());
                            }
                        }
                ),
                new Column<>("Fecha Devolución",
                        prestamo -> {
                            Date fecha = prestamo.getFechaDevolucion();
                            return fecha == null ? "" : new SimpleDateFormat("dd-MM-yyyy").format(fecha);
                        },
                        (p, v) -> {
                            try {
                                if (v == null || ((String) v).isEmpty()) {
                                    p.setFechaDevolucion(null);
                                } else {
                                    Date fecha = new SimpleDateFormat("dd-MM-yyyy").parse((String) v);
                                    p.setFechaDevolucion(fecha);
                                }
                            } catch (Exception e) {
                                System.err.println("Error parseando fecha de devolución: " + e.getMessage());
                            }
                        }
                ),
                new Column<>("Estado",
                        prestamo -> prestamo.isEstado() ? "Prestado" : "Devuelto",
                        (p, v) -> p.setEstado("Prestado".equals(v))
                ),
                new Column<>("Acciones", p -> null, (p, v) -> {})
        );
        return new TableComponent<>(columns);
    }

    private void cargarPrestamos() {
        filtrarUsuario();
//        List<Map<String,Object>> datosPrestamos = ControladorPrestamo.obtenerPrestamos();
//        List<PrestamoDTO> prestamos = datosPrestamos.stream().map(
//                row -> {
//                    PrestamoDTO prestamoDTO = new PrestamoDTO();
//                    prestamoDTO.setID((Integer) row.get("ID"));
//                    prestamoDTO.setUsuario((String) row.get("Usuario"));
//                    prestamoDTO.setLibro((String) row.get("Libro"));
//                    prestamoDTO.setCodigo_Ejemplar((String) row.get("Codigo_Ejemplar"));
//                    prestamoDTO.setFechaPrestamo((Date) row.get("Fecha_Prestamo"));
//                    prestamoDTO.setFechaDevolucion((Date) row.get("Fecha_Devolucion"));
//                    prestamoDTO.setEstado((boolean) row.get("Estado"));
//                    return prestamoDTO;
//                }
//        ).toList();
//
//        tablaComponent.clearRows();
//        tablaComponent.addRows(prestamos);
    }
    private void filtrarUsuario(){
        String usuarioSeleccionado=txtID.getText();
        List<Map<String,Object>>datosPrestamo=ControladorPrestamo.obtenerPrestamos();
        List<PrestamoDTO>prestamos=datosPrestamo.stream()
                .filter(row->{
                    String nombreUsuario=(String) row.get("Usuario");
                    if (usuarioSeleccionado.isEmpty())return true;
                    return nombreUsuario!=null&&
                            nombreUsuario.toLowerCase().contains(usuarioSeleccionado.toLowerCase());
                }).map(row->{
                    PrestamoDTO p = new PrestamoDTO();
                    p.setID((Integer)row.get("ID"));
                    p.setUsuario((String) row.get("Usuario"));
                    p.setLibro((String) row.get("Libro"));
                    p.setID_Ejemplar((Integer)row.get("ID_Ejemplar"));
                    p.setCodigo_Ejemplar((String) row.get("Codigo_Ejemplar"));
                    p.setFechaPrestamo((Date) row.get("Fecha_Prestamo"));
                    p.setFechaDevolucion((Date) row.get("Fecha_Devolucion"));
                    p.setEstado((boolean) row.get("Estado"));
                    return p;
                }).toList();
        tablaPrestamo.clearRows();
        tablaPrestamo.addRows(prestamos);
    }
}

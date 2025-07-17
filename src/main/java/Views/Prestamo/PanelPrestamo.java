package main.java.Views.Prestamo;

import main.java.Models.EjemplarDTO;
import main.java.Models.PrestamoDTO;
import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;
import main.java.Controllers.Operadores.Metodos.ControladorPrestamo;
import main.java.Models.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PanelPrestamo extends JPanel {
    private JTextField txtID;
    
    public PanelPrestamo() {
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Préstamos"));
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

        JLabel lblID = ComponentFactory.crearEtiqueta("ID Préstamo:");
        txtID = ComponentFactory.crearCampoTexto();
        txtID.setColumns(15);

        panelIzquierdo.add(lblID);
        panelIzquierdo.add(txtID);

        // ---- Subpanel derecho: botones ----
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setBackground(ComponentFactory.COLOR_FONDO);

        JButton btnConsultar = ComponentFactory.crearBoton("Buscar", ComponentFactory.ruta("action-search"));
        JButton btnAgregar = ComponentFactory.crearBoton("Agregar", ComponentFactory.ruta("action-add"));

        panelIzquierdo.add(btnConsultar);
        panelDerecho.add(btnAgregar);
// hola
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
        TableComponent<PrestamoDTO> model = getPrestamoTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(model);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        List<Map<String,Object>> datosPrestamos = ControladorPrestamo.obtenerPrestamos();
        List<PrestamoDTO> prestamos = datosPrestamos.stream().map(
                row->{
                    PrestamoDTO prestamoDTO = new PrestamoDTO();
                    prestamoDTO.setID((Integer)row.get("ID"));
                    prestamoDTO.setUsuario((String) row.get("Usuario"));
                    prestamoDTO.setLibro((String) row.get("Libro"));
                    prestamoDTO.setCodigo_Ejemplar((String) row.get("Codigo_Ejemplar"));
                    prestamoDTO.setFechaPrestamo((Date) row.get("Fecha_Pretamo"));
                    prestamoDTO.setFechaDevolucion((Date) row.get("Fecha_Devolucion"));
                    prestamoDTO.setEstado((boolean) row.get("Estado"));
                    return prestamoDTO;
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
        model.addRows(prestamos);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static TableComponent<PrestamoDTO> getPrestamoTableComponent() {
        List<Column<PrestamoDTO>> columns = List.of(
                new Column<>(
                        "ID",
                        PrestamoDTO::getID,
                        (p,v)->p.setID((Integer)v)
                ),
                new Column<>(
                        "Usuario",
                        PrestamoDTO::getUsuario,
                        (p,v)->p.setUsuario((String) v)
                ),
                new Column<>(
                        "Libro",
                        PrestamoDTO::getLibro,
                        (p,v)->p.setLibro((String) v)
                ),
                new Column<>(
                        "Codigo",
                        PrestamoDTO::getCodigo_Ejemplar,
                        (p,v)->p.setCodigo_Ejemplar((String) v)
                ),
                new Column<>("Fecha Préstamo",
                        prestamo -> {
                            Date fecha = prestamo.getFechaPrestamo();
                            if (fecha == null) return "";
                            return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
                        },
                        (p, v) -> {
                            try {
                                if (v == null || ((String)v).isEmpty()) {
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
                            if (fecha == null) return "";
                            return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
                        },
                        (p, v) -> {
                            try {
                                if (v == null || ((String)v).isEmpty()) {
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
}

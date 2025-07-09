package main.java.Views.Prestamo;

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
        TableComponent<Prestamo> model = getPrestamoTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(model);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        List<Map<String,Object>> datosPrestamos = ControladorPrestamo.obtenerPrestamos();
        List<Prestamo> prestamos = datosPrestamos.stream().map(
                row -> {
                    Prestamo prestamo = new Prestamo();
                    prestamo.setID((Integer) row.get("ID"));
                    prestamo.setFechaPrestamo((Date) row.get("Fecha_Prestamo"));
                    prestamo.setFechaDevolucion((Date) row.get("Fecha_Devolucion"));
                    prestamo.setEstado((Boolean) row.get("Estado"));
                    prestamo.setID_Bibliotecario((Integer) row.get("ID_Bibliotecario"));
                    prestamo.setID_Usuario((Integer) row.get("ID_Usuario"));
                    prestamo.setID_Ejemplar((Integer) row.get("ID_Ejemplar"));
                    return prestamo;
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

    private static TableComponent<Prestamo> getPrestamoTableComponent() {
        List<Column<Prestamo>> columns = List.of(
                new Column<>("ID",
                        Prestamo::getID,
                        (p, v) -> p.setID((Integer) v)
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
                        prestamo -> prestamo.isEstado() ? "Activo" : "Devuelto",
                        (p, v) -> p.setEstado("Activo".equals(v))
                ),
                new Column<>("ID Bibliotecario",
                        Prestamo::getID_Bibliotecario,
                        (p, v) -> p.setID_Bibliotecario((Integer) v)
                ),
                new Column<>("ID Usuario",
                        Prestamo::getID_Usuario,
                        (p, v) -> p.setID_Usuario((Integer) v)
                ),
                new Column<>("ID Ejemplar",
                        Prestamo::getID_Ejemplar,
                        (p, v) -> p.setID_Ejemplar((Integer) v)
                ),
                new Column<>("Acciones", p -> null, (p, v) -> {})
        );
        return new TableComponent<>(columns);
    }
}

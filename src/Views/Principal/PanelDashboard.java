package Views.Principal;

import Controllers.Operadores.Metodos.ControladorEjemplar;
import Controllers.Operadores.Metodos.ControladorPrestamo;
import Controllers.Operadores.Metodos.ControladorUsuario;
import Models.PrestamoDTO;
import resources.Shared.Table.TableComponent;
import resources.Shared.Table.TableFactory;
import resources.Utils.Column;
import resources.Utils.ComponentFactory;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PanelDashboard extends JPanel {
    private TableComponent<PrestamoDTO> tablaComponent;
    private JPanel panelResumen; // 🔹 Se agrega la variable

    public PanelDashboard() {
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);

        panelResumen = panelResumen();                 // 🟦 Se inicializa
        add(panelResumen, BorderLayout.NORTH);         // 🔹 Se agrega el inicial
        add(panelTabla(), BorderLayout.CENTER);        // 🟦 Tabla existente

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                actualizarResumen();                   // 🔄 Al entrar al panel
                cargarPrestamosPrestados();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {}

            @Override
            public void ancestorMoved(AncestorEvent event) {}
        });
    }

    private JPanel panelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        tablaComponent = getTablaDashboard();
        JTable tabla = TableFactory.crearTablaEstilo(tablaComponent);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);

        tabla.setRowHeight(40);
        cargarPrestamosPrestados(); // Solo préstamos activos

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel panelResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        long cantidadPrestados = ControladorPrestamo.obtenerPrestamos().stream()
                .filter(p -> Boolean.TRUE.equals(p.get("Estado")))
                .count();

        JLabel tarjetaUsuarios = ComponentFactory.crearTarjeta("Usuarios", ControladorUsuario.obtenerUsuarios().size());
        JLabel tarjetaLibros = ComponentFactory.crearTarjeta("Libros", ControladorEjemplar.obtenerEjemplares().size());
        JLabel tarjetaPrestamos = ComponentFactory.crearTarjeta("Préstamos Activos", (int) cantidadPrestados);

        panel.add(tarjetaUsuarios);
        panel.add(tarjetaLibros);
        panel.add(tarjetaPrestamos);

        return panel;
    }

    // 🔄 Este método actualiza el panelResumen dinámicamente
    private void actualizarResumen() {
        remove(panelResumen);               // Quita el viejo
        panelResumen = panelResumen();      // Crea uno nuevo actualizado
        add(panelResumen, BorderLayout.NORTH);
        revalidate();                       // Reorganiza layout
        repaint();                          // Redibuja
    }

    private void cargarPrestamosPrestados() {
        List<Map<String, Object>> datos = ControladorPrestamo.obtenerPrestamos();
        List<PrestamoDTO> prestamos = datos.stream()
                .filter(row -> (boolean) row.get("Estado"))
                .map(row -> {
                    PrestamoDTO p = new PrestamoDTO();
                    p.setID((Integer) row.get("ID"));
                    p.setUsuario((String) row.get("Usuario"));
                    p.setLibro((String) row.get("Libro"));
                    p.setCodigo_Ejemplar((String) row.get("Codigo_Ejemplar"));
                    p.setFechaPrestamo((Date) row.get("Fecha_Prestamo"));
                    p.setFechaDevolucion((Date) row.get("Fecha_Devolucion"));
                    p.setEstado((boolean) row.get("Estado"));
                    return p;
                }).toList();

        tablaComponent.clearRows();
        tablaComponent.addRows(prestamos);
    }

    private static TableComponent<PrestamoDTO> getTablaDashboard() {
        List<Column<PrestamoDTO>> columnas = List.of(
                new Column<>("ID", PrestamoDTO::getID, (p, v) -> p.setID((Integer) v)),
                new Column<>("Usuario", PrestamoDTO::getUsuario, (p, v) -> p.setUsuario((String) v)),
                new Column<>("Libro", PrestamoDTO::getLibro, (p, v) -> p.setLibro((String) v)),
                new Column<>("Código", PrestamoDTO::getCodigo_Ejemplar, (p, v) -> p.setCodigo_Ejemplar((String) v)),
                new Column<>("Fecha Préstamo",
                        p -> {
                            Date fecha = p.getFechaPrestamo();
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
                                System.err.println("Error en fecha préstamo: " + e.getMessage());
                            }
                        }
                ),
                new Column<>("Fecha Devolución",
                        p -> {
                            Date fecha = p.getFechaDevolucion();
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
                                System.err.println("Error en fecha devolución: " + e.getMessage());
                            }
                        }
                ),
                new Column<>("Estado",
                        p -> p.isEstado() ? "Prestado" : "Devuelto",
                        (p, v) -> p.setEstado("Prestado".equals(v))
                )
        );

        return new TableComponent<>(columnas);
    }
}

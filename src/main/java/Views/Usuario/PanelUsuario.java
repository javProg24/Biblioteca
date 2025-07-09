package main.java.Views.Usuario;

import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;
import main.java.Controllers.Operadores.Metodos.ControladorUsuario;
import main.java.Models.Usuario;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PanelUsuario extends JPanel {
    private JTextField txtCedula;
    public PanelUsuario() {
        setLayout(new BorderLayout());
        setBackground(ComponentFactory.COLOR_FONDO);
        setBorder(BorderFactory.createTitledBorder("Usuarios"));
        initComponents();

    }
    private void initComponents(){
        //2 JPanel, Panel de botones y panel de tabla
        JPanel panelBotones = panelBotones();
        JPanel panelTabla = panelTabla();

        add(panelBotones, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);

    }
    private JPanel panelBotones(){
        JPanel panel = new JPanel();
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        panel.add(new JButton("Nuevo"));
        panel.add(new JButton("Eliminar"));
        return panel;
    }
    private JPanel panelTabla(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        TableComponent<Usuario> model = getUsuarioTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(model);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        List<Map<String,Object>> datosUsuarios= ControladorUsuario.obtenerUsuarios();
        List<Usuario>usuarios=datosUsuarios.stream().map(
                row->{
                    Usuario usuario = new Usuario();
                    usuario.setID((Integer) row.get("ID"));
                    usuario.setNombre((String) row.get("Nombre"));
                    usuario.setApellido((String) row.get("Apellido"));
                    usuario.setDirreccion((String) row.get("Direccion"));
                    usuario.setTelefono((Integer) row.get("Telefono"));
//                    Object fechaObj = row.get("Fecha_Nacimiento");
//                    Date fecha = null;
//                    if (fechaObj instanceof Date) {
//                        fecha = (Date) fechaObj;
//                    } else {
//                        System.err.println("Tipo desconocido para Fecha_Nacimiento: " + fechaObj.getClass());
//                    }
                    usuario.setFecha_Nacimiento((Date) row.get("Fecha_Nacimiento"));
                    return usuario;
                }
        ).toList();
        TableActionEvent actionEvent=new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Editar fila: " + row);
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Eliminar fila: " + row);
            }
        };
        int colAcciones=tabla.getColumnCount()-1;
        tabla.getColumnModel().getColumn(colAcciones)
                .setCellRenderer(new TableActionCellRenderer(actionEvent));

        tabla.getColumnModel().getColumn(colAcciones)
                .setCellEditor(new TableActionCellEditor(actionEvent));
        tabla.setRowHeight(40);
        model.addRows(usuarios);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static TableComponent<Usuario> getUsuarioTableComponent() {
        List<Column<Usuario>>columns= List.of(
                new Column<>("ID",
                        Usuario::getID,
                        (u, v) -> u.setID((Integer) v)
                ),
                new Column<>("Nombre",
                        Usuario::getNombre,
                        (u, v) -> u.setNombre((String) v)
                ),
                new Column<>("Apellido",
                        Usuario::getApellido,
                        (u, v) -> u.setApellido((String) v)
                ),
                new Column<>("Direccion",
                        Usuario::getDirreccion,
                        (u, v) -> u.setDirreccion((String) v)
                ),
                new Column<>("Telefono",
                        Usuario::getTelefono,
                        (u, v) -> u.setTelefono((Integer) v)
                ),
                new Column<>("Fecha de Nacimiento",
                        usuario -> {
                            Date fecha = usuario.getFecha_Nacimiento();
                            if (fecha == null) return "";
                            return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
                        },
                        (u, v) -> {
                            try {
                                if (v == null || ((String)v).isEmpty()) {
                                    u.setFecha_Nacimiento(null);
                                } else {
                                    Date fecha = new SimpleDateFormat("dd-MM-yyyy").parse((String) v);
                                    u.setFecha_Nacimiento(fecha);
                                }
                            } catch (Exception e) {
                                System.err.println("Error parseando fecha en columna: " + e.getMessage());
                            }
                        }
                )
        );
        return new TableComponent<>(columns);
    }
//    public Date parseFecha(String fechaStr) {
//        if (fechaStr == null || fechaStr.isEmpty()) return null;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//            return sdf.parse(fechaStr);
//        } catch (ParseException e) {
//            System.err.println("Error parseando fecha: " + e.getMessage());
//            return null;
//        }
//    }
}

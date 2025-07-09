package main.java.Views.Usuario;

import main.resources.Shared.Table.*;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;
import main.java.Controllers.Operadores.Metodos.ControladorUsuario;
import main.java.Models.Usuario;

import javax.swing.*;
import java.awt.*;
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
    private JPanel panelBotones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);

        // ---- Subpanel izquierdo: etiqueta + campo ----
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelIzquierdo.setBackground(ComponentFactory.COLOR_FONDO);

        JLabel lblCedula = ComponentFactory.crearEtiqueta("Cédula:");
        txtCedula = ComponentFactory.crearCampoTexto();
        txtCedula.setColumns(15);

        panelIzquierdo.add(lblCedula);
        panelIzquierdo.add(txtCedula);

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
                ),
                new Column<>("Acciones",u->null,(u,v)->{})
        );
        return new TableComponent<>(columns);
    }
}

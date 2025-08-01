package Views.Usuario;

import com.formdev.flatlaf.FlatLightLaf;
import Controllers.Operadores.Enums.E_ROL;
import Controllers.Operadores.Metodos.ControladorUsuario;
import Models.Usuario;
import resources.Shared.Dialog.DialogComponent;
import resources.Shared.Notification.NotificationComponent;
import resources.Shared.Table.*;
import resources.Utils.Column;
import resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PanelUsuario extends JPanel {
    private JTextField txtNombre;
    private TableComponent<Usuario> modelUsuario;
    public PanelUsuario() {
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
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

        JLabel lblCedula = ComponentFactory.crearEtiqueta("Nombre:");
        txtNombre = ComponentFactory.crearCampoTexto();
        txtNombre.setColumns(15);

        panelIzquierdo.add(lblCedula);
        panelIzquierdo.add(txtNombre);

        // ---- Subpanel derecho: botones ----
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setBackground(ComponentFactory.COLOR_FONDO);

        JButton btnConsultar = ComponentFactory.crearBoton("Buscar", ComponentFactory.ruta("action-search"),true);
        JButton btnAgregar = ComponentFactory.crearBoton("Agregar", ComponentFactory.ruta("action-add"),true);
        panelIzquierdo.add(btnConsultar);
        panelDerecho.add(btnAgregar);
        btnConsultar.addActionListener(e -> consultarUsuario());
        btnAgregar.addActionListener(e -> {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelUsuario.this);
            UsuarioForm dialog = new UsuarioForm(parentFrame, this::cargarDatosUsuarios);
            dialog.setVisible(true);
        });
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
    private void cargarDatosUsuarios(){
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
        modelUsuario.clearRows();
        modelUsuario.fireTableDataChanged();
        modelUsuario.addRows(usuarios);
    }
    private void consultarUsuario(){
        String nombre=txtNombre.getText().trim();
        if(nombre.isEmpty()){
            cargarDatosUsuarios();
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        List<Map<String,Object>> datosUsuarios=ControladorUsuario.obtenerUsuarioNombre(usuario);
        List<Usuario> usuariosFiltrados = datosUsuarios.stream()
                .map(row -> {
                    Usuario u = new Usuario();
                    u.setID((Integer) row.get("ID"));
                    u.setNombre((String) row.get("Nombre"));
                    u.setApellido((String) row.get("Apellido"));
                    u.setDirreccion((String) row.get("Direccion"));
                    u.setTelefono((Integer) row.get("Telefono"));
                    u.setFecha_Nacimiento((Date) row.get("Fecha_Nacimiento"));
                    return u;
                })
                .filter(u -> u.getNombre() != null &&u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

        modelUsuario.clearRows();
        modelUsuario.fireTableDataChanged();
        modelUsuario.addRows(usuariosFiltrados);
    }
    private JPanel panelTabla(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ComponentFactory.COLOR_FONDO);
        //modelo de la tabla
        modelUsuario = getUsuarioTableComponent();
        JTable tabla = TableFactory.crearTablaEstilo(modelUsuario);
        tabla.setRowSelectionAllowed(false);
        tabla.setColumnSelectionAllowed(false);
        tabla.setCellSelectionEnabled(false);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        cargarDatosUsuarios();
        TableActionEvent actionEvent=new TableActionEvent() {
            private int id=0;
            @Override
            public void onEdit(int row) {
                Usuario usuarioSeleccionado=modelUsuario.getRow(row);
                this.id=usuarioSeleccionado.getID();
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelUsuario.this);
                UsuarioForm dialog = new UsuarioForm(parentFrame, id, true, ()-> cargarDatosUsuarios());
                dialog.setVisible(true);
               //System.out.println("Editar fila: " + row+id);
            }
            @Override
            public void onDelete(int row) {
                if (tabla.isEditing()) {
                    tabla.getCellEditor().stopCellEditing();
                }
                Usuario usuarioSeleccionado=modelUsuario.getRow(row);
                this.id=usuarioSeleccionado.getID();
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(PanelUsuario.this);
                DialogComponent ventanaEliminar = new DialogComponent(
                        parentFrame,
                        E_ROL._USUARIO,
                        () -> {
                            Usuario usuario = new Usuario();
                            usuario.setID(id);
                            boolean eliminado = ControladorUsuario.eliminarUsuario(usuario);
                            NotificationComponent panelComponent;
                            if (eliminado) {
                                panelComponent = new NotificationComponent(parentFrame, NotificationComponent.Type.EXITO, NotificationComponent.Location.TOP_RIGHT, "Usuario eliminado");
                                //modelUsuario.removeRow(row);
                                //modelUsuario.fireTableRowsDeleted(row, row);
                                cargarDatosUsuarios();
                            } else {
                                panelComponent = new NotificationComponent(parentFrame, NotificationComponent.Type.ADVERTENCIA, NotificationComponent.Location.TOP_RIGHT, "Ocurrio un error");
                            }
                            panelComponent.showNotification();
                        });
                ventanaEliminar.setVisible(true);
                //acion de eliminar
                //System.out.println("Eliminar fila: " + row+id);
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

    private static TableComponent<Usuario> getUsuarioTableComponent() {
        List<Column<Usuario>>columns= List.of(
                new Column<>(
                        "ID",
                        Usuario::getID,
                        (u, v) -> u.setID((Integer) v)
                ),
                new Column<>("Nombre", Usuario::getNombre, (u, v) -> u.setNombre((String) v)),
                new Column<>("Apellido", Usuario::getApellido, (u, v) -> u.setApellido((String) v)),
                new Column<>("Direccion", Usuario::getDirreccion, (u, v) -> u.setDirreccion((String) v)),
                new Column<>("Telefono", Usuario::getTelefono, (u, v) -> u.setTelefono((Integer) v)),
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
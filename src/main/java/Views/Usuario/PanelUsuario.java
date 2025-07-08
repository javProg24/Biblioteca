package main.java.Views.Usuario;

import main.resources.Shared.Table.TableComponent;
import main.resources.Shared.Table.TableFactory;
import main.resources.Utils.Column;
import main.resources.Utils.ComponentFactory;
import main.java.Controllers.Operadores.Metodos.ControladorUsuario;
import main.java.Models.Usuario;

import javax.swing.*;
import java.awt.*;
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
        List<Column<Usuario>>columns= List.of(
                new Column<>("ID", Usuario::getID, (u, v) -> u.setID((Integer) v)),
                new Column<>("Nombre", Usuario::getNombre, (u, v) -> u.setNombre((String) v)),
                new Column<>("Apellido", Usuario::getApellido, (u, v) -> u.setApellido((String) v)),
                new Column<>("Telefono", Usuario::getTelefono, (u, v) -> u.setTelefono((Integer) v))
        );
        TableComponent<Usuario>model=new TableComponent<>(columns);
        JTable tabla = TableFactory.crearTablaEstilo(model);
        JScrollPane scrollPane = TableFactory.wrapWithRoundedBorder(tabla);
        List<Map<String,Object>> datosUsuarios= ControladorUsuario.obtenerUsuarios();
        List<Usuario>usuarios=datosUsuarios.stream().map(
                row->{
                    Usuario usuario = new Usuario();
                    usuario.setID((Integer) row.get("ID"));
                    usuario.setNombre((String) row.get("Nombre"));
                    usuario.setApellido((String) row.get("Apellido"));
                    usuario.setTelefono((Integer) row.get("Telefono"));
                    return usuario;
                }
        ).toList();
        model.addRows(usuarios);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}

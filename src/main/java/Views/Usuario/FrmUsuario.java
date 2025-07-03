package main.java.Views.Usuario;

import main.Resources.Shared.TableComponent;
import main.Resources.Utils.Column;
import main.java.Controllers.Operadores.Metodos.ControladorUsuario;
import main.java.Models.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class FrmUsuario extends JPanel {
    public FrmUsuario() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Usuarios"));
        List<Column<Usuario>>columns= List.of(
                new Column<>("ID", Usuario::getID, (u, v) -> u.setID((Integer) v)),
                new Column<>("Nombre", Usuario::getNombre, (u, v) -> u.setNombre((String) v)),
                new Column<>("Apellido", Usuario::getApellido, (u, v) -> u.setApellido((String) v)),
                new Column<>("Telefono", Usuario::getTelefono, (u, v) -> u.setTelefono((Integer) v))
        );
        TableComponent<Usuario>model=new TableComponent<>(columns);
        List<Map<String,Object>> usuariosBD= ControladorUsuario.obtenerUsuarios();
        List<Usuario>usuarios=usuariosBD.stream().map(
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
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}

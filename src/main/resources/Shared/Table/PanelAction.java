package main.resources.Shared.Table;

import com.formdev.flatlaf.FlatLightLaf;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelAction extends JPanel {
    private ActionButton cmdDelete;
    private ActionButton cmdEdit;

    public PanelAction() {
        cmdEdit = new ActionButton(ComponentFactory.ruta("editar"));
        cmdDelete = new ActionButton(ComponentFactory.ruta("eliminar"));

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        Dimension btnSize = new Dimension(37, 31);
        cmdEdit.setPreferredSize(btnSize);
        cmdDelete.setPreferredSize(btnSize);

        add(cmdEdit);
        add(cmdDelete);
    }

    public void initEvent(TableActionEvent event, int row) {
        cmdEdit.addActionListener((ActionEvent e) -> event.onEdit(row));
        cmdDelete.addActionListener((ActionEvent e) -> event.onDelete(row));
    }
}

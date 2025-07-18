package main.resources.Shared.Table;

import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Supplier;

public class PanelAction extends JPanel {
    private ActionButton cmdDelete;
    private ActionButton cmdEdit;

    public PanelAction() {
        cmdEdit = new ActionButton(ComponentFactory.ruta("editar"));
        cmdDelete = new ActionButton(ComponentFactory.ruta("eliminar"));

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        Dimension btnSize = new Dimension(37, 31);
        cmdEdit.setPreferredSize(btnSize);
        cmdDelete.setPreferredSize(btnSize);
        setBackground(Color.WHITE);
        add(cmdEdit);
        add(cmdDelete);
    }

    public void initEvent(TableActionEvent event, Supplier<Integer> rowSupplier) {
        for (ActionListener al : cmdEdit.getActionListeners()) {
            cmdEdit.removeActionListener(al);
        }
        for (ActionListener al : cmdDelete.getActionListeners()) {
            cmdDelete.removeActionListener(al);
        }
        cmdEdit.addActionListener(e -> event.onEdit(rowSupplier.get()));
        cmdDelete.addActionListener(e -> event.onDelete(rowSupplier.get()));
    }
}

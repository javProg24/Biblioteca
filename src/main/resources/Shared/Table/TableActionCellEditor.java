package main.resources.Shared.Table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class TableActionCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final TableActionEvent event;
    private PanelAction panel;

    public TableActionCellEditor(TableActionEvent event) {
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel = new PanelAction();
        panel.initEvent(event, row);
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}

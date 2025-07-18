package main.resources.Shared.Table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class TableActionCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final TableActionEvent event;
    private int estadoColumnIndex;
    public TableActionCellEditor(TableActionEvent event, int estadoColumnIndex) {
        this.event = event;
        this.estadoColumnIndex = estadoColumnIndex;
    }
    public TableActionCellEditor(TableActionEvent event) {
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelAction panel = new PanelAction();

        Object estadoValue = table.getValueAt(row, estadoColumnIndex);
        boolean habilitado = !("No disponible".equals(estadoValue));

        panel.setEnabled(habilitado);
        panel.initEvent(habilitado ? event : null, table::getEditingRow);
        panel.setToolTipText(habilitado ? null : "Ejemplar no disponible");

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}


package main.resources.Shared.Table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class TableActionCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final TableActionEvent event;
    private int estadoColumnIndex;
    private String condicion;
    public TableActionCellEditor(TableActionEvent event, int estadoColumnIndex,String condicion) {
        this.event = event;
        this.estadoColumnIndex = estadoColumnIndex;
        this.condicion=condicion;
    }
    public TableActionCellEditor(TableActionEvent event) {
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelAction panel = new PanelAction();

        Object estadoValue = table.getValueAt(row, estadoColumnIndex);
        boolean habilitado = !(condicion.equals(estadoValue));

        panel.setEnabled(habilitado);
        panel.initEvent(habilitado ? event : null, table::getEditingRow);
        panel.setToolTipText(habilitado ? null : "Recurso no disponible");

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}


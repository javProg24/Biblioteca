package main.resources.Shared.Table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class TableActionCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final TableActionEvent event;

    public TableActionCellEditor(TableActionEvent event) {
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelAction panel = new PanelAction();

        // Obtenemos el estado desde la tabla (ajusta el índice si cambia la columna)
        Object estadoValue = table.getValueAt(row, 3);
        boolean habilitado = !("No disponible".equals(estadoValue));

        // Desactivar los botones si el estado no lo permite
        panel.setEnabled(habilitado);

        // Si está habilitado, se asigna el evento normalmente. Si no, no hace nada.
        panel.initEvent(habilitado ? event : null, table::getEditingRow);

        // Opcional: Tooltip para explicar por qué está deshabilitado
        if (!habilitado) {
            panel.setToolTipText("Ejemplar no disponible");
        } else {
            panel.setToolTipText(null);
        }

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}


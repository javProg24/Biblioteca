package resources.Shared.Table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableActionCellRenderer extends JPanel implements TableCellRenderer {

    public TableActionCellRenderer(TableActionEvent event) {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //panel.setBackground(table.getBackground());
        return new PanelAction();
    }
}

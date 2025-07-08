package main.resources.Shared.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableFactory {
    private TableFactory() {}
    public static<T>JTable crearTablaEstilo(TableComponent<T> model){
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return model.isCellEditable(row, column);
            }
        };

        // 🟦 Estilo del encabezado
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0x00, 0x33, 0x66)); // azul marino
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 🧾 Estilo de las filas
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // 🌈 Renderer personalizado para estilo de celdas
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(0xCC, 0xDD, 0xFF));
                } else {
                    c.setBackground(Color.WHITE);
                }
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                return c;
            }
        });

        return table;
    }
    public static JScrollPane wrapWithRoundedBorder(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel roundedPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 40; // Esquinas redondeadas más marcadas
                int strokeWidth = 2;

                // Fondo blanco
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                // Borde azul marino
                g2.setColor(new Color(0x00, 0x33, 0x66));
                g2.setStroke(new BasicStroke(strokeWidth));
                g2.drawRoundRect(
                        strokeWidth / 2,
                        strokeWidth / 2,
                        getWidth() - strokeWidth,
                        getHeight() - strokeWidth,
                        arc, arc
                );
            }
        };

        roundedPanel.setOpaque(false);
        roundedPanel.setBorder(BorderFactory.createEmptyBorder()); // sin borde extra
        roundedPanel.add(scrollPane, BorderLayout.CENTER);

        // Envolver en otro JScrollPane para mantener compatibilidad, si necesitas scrolleo extra
        JScrollPane wrapper = new JScrollPane(roundedPanel);
        wrapper.setBorder(BorderFactory.createEmptyBorder()); // opcional
        wrapper.setOpaque(false);
        wrapper.getViewport().setOpaque(false);

        return wrapper;
    }

}

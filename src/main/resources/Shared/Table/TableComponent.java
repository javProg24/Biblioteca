package main.resources.Shared.Table;

import main.resources.Utils.Column;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class TableComponent<T> implements TableModel {
    private final List<Column<T>> columns;
    private final List<T> data = new ArrayList<>();
    private final EventListenerList listenerList = new EventListenerList();
    public TableComponent(List<Column<T>> columns) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("Columns cannot be null or empty");
        }
        this.columns = columns;
    }
    public void addRows(List<T> datosEntidad) {
        if (!datosEntidad.isEmpty()) {
            int first = this.data.size();
            this.data.addAll(datosEntidad);
            fireTableRowsInserted(first, this.data.size() - 1);
        }
    }
    public void addRow(T row) {
        int index = data.size();
        data.add(row);
        fireTableRowsInserted(index, index);
    }
    @Override
    public int getRowCount() {
        return data.size();
    }
    @Override
    public int getColumnCount() {
        return columns.size();
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).label();
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (!data.isEmpty()) {
            Object value = columns.get(columnIndex).getter().apply(data.get(0));
            if (value != null) {
                return value.getClass();
            }
        }
        return Object.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == getColumnCount() - 1;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T row = data.get(rowIndex);
        return columns.get(columnIndex).getter().apply(row);
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        T row = data.get(rowIndex);
        columns.get(columnIndex).setter().accept(row, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    @Override
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
    }
    @Override
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }
    private void fireTableRowsInserted(int firstRow, int lastRow) {
        TableModelEvent e = new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        fireTableChanged(e);
    }
    private void fireTableRowsDeleted(int firstRow, int lastRow) {
        TableModelEvent e = new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        fireTableChanged(e);
    }
    private void fireTableCellUpdated(int row, int column) {
        TableModelEvent e = new TableModelEvent(this, row, row, column);
        fireTableChanged(e);
    }

    private void fireTableChanged(TableModelEvent e) {
        for (TableModelListener l : listenerList.getListeners(TableModelListener.class)) {
            l.tableChanged(e);
        }
    }
}
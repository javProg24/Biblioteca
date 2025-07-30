package resources.Shared.Table;

public interface TableActionEvent {
    void onEdit(int row);
    void onDelete(int row);
}

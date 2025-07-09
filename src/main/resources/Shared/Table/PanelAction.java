package main.resources.Shared.Table;

import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelAction extends JPanel {
    private ActionButton cmdDelete;
    private ActionButton cmdEdit;
    public PanelAction(){
        cmdEdit=new ActionButton();
        cmdDelete=new ActionButton();
        setLayout(new FlowLayout(FlowLayout.LEFT,18,5));
        setIconEscalado(cmdEdit, ComponentFactory.ruta("editar"));
        setIconEscalado(cmdDelete,ComponentFactory.ruta("eliminar"));
        Dimension btnSize=new Dimension(37,31);
        cmdDelete.setPreferredSize(btnSize);
        cmdEdit.setPreferredSize(btnSize);
        add(cmdEdit);
        add(cmdDelete);
    }
    public void initEvent(TableActionEvent event, int row){
        cmdEdit.addActionListener((ActionEvent e)->event.onEdit(row) );
        cmdDelete.addActionListener((ActionEvent e)->event.onDelete(row) );
    }

    private void setIconEscalado(AbstractButton boton, String rutaIcono) {
        try {
            ImageIcon icono=new ImageIcon(rutaIcono);
            Image image = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(image));
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + rutaIcono);
        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Panel de Acciones");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 100);
//        PanelAction panel = new PanelAction();
//
//        // Simulación de evento
//        panel.initEvent(new TableActionEvent() {
//            @Override
//            public void onEdit(int row) {
//                System.out.println("Editar fila: " + row);
//            }
//
//            @Override
//            public void onDelete(int row) {
//                System.out.println("Eliminar fila: " + row);
//            }
//
//            @Override
//            public void onView(int row) {
//                System.out.println("Ver fila: " + row);
//            }
//        }, 1);
//
//        frame.add(panel);
//        frame.setVisible(true);
//    }
}

package main.resources.Shared.Table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class ActionButton extends JButton {
    private boolean isSelected;
    public ActionButton(){
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3,3,3,3));
        setFocusPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                isSelected=true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isSelected){
                    isSelected=false;
                }
            }
        });
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        Graphics2D g2=(Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int x= (width - size) / 2;
        int y= (height - size) / 2;
        if(isSelected){
            g2.setColor(new Color(158,158,188)); // Color azul para el botón seleccionado
        } else {
            g2.setColor(new Color(199,199,219)); // Color gris claro para el botón no seleccionado
        }
        g2.fill(new Ellipse2D.Double(x, y, size, size));
        g2.dispose();
        super.paintComponent(g);
    }

}

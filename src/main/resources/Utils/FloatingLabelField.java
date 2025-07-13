package main.resources.Utils;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class FloatingLabelField extends JPanel {
    private final JTextField textField;

    public FloatingLabelField(String labelText) {
        setLayout(null);
        setPreferredSize(new Dimension(250, 50));
        setBackground(ComponentFactory.COLOR_FONDO);
        // si tienes ComponentFactory.COLOR_FONDO, úsalo aquí
        // setBackground(ComponentFactory.COLOR_FONDO);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBounds(5, 0, 200, 20);

        textField = new JTextField();
        textField.setBounds(0, 20, 250, 30);

        add(label);
        add(textField);
    }

}
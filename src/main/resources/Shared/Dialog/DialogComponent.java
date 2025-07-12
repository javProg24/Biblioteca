package main.resources.Shared.Dialog;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.Controllers.Operadores.Enums.E_ROL;
import main.resources.Shared.Notification.NotificationComponent;
import main.resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;

public class DialogComponent extends JDialog {
    private JLabel etiqueta;
    private String entidad;
    private JButton btnAceptar,
            btnCancelar;
    private Runnable onAccept;
    public DialogComponent(Frame parent,  E_ROL rol,Runnable onAccept){
        super(parent,"",true);
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.entidad=rol.getDisplayName();
        this.onAccept = onAccept;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
        setMinimumSize(new Dimension(450, 150));
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        mainPanel.add(panelMensaje(),BorderLayout.CENTER);
        mainPanel.add(panelBotones(),BorderLayout.SOUTH);
        add(mainPanel,BorderLayout.CENTER);
    }
    private JPanel panelMensaje() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta principal: Eliminar (entidad)
        gbc.gridx = 0;
        gbc.gridy = 0;
        etiqueta = ComponentFactory.crearEtiqueta("Eliminar " + entidad);

        // Aumentar tamaño de fuente (sin perder estilo)
        etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD, etiqueta.getFont().getSize() + 2));

        panel.add(etiqueta, gbc);

        // Mensaje de confirmación
        gbc.gridy++;
        JLabel labelMensaje = new JLabel("¿Desea eliminar a este " + entidad + "?");

        // Aumentar tamaño de fuente (sin perder estilo)
        labelMensaje.setFont(labelMensaje.getFont().deriveFont(Font.PLAIN, labelMensaje.getFont().getSize() + 1));

        panel.add(labelMensaje, gbc);

        return panel;
    }
    private JPanel panelBotones(){
        JPanel panelBotones=new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnAceptar=ComponentFactory.crearBoton("Aceptar",ComponentFactory.ruta("success"));
        btnCancelar=ComponentFactory.crearBoton("Cancelar",ComponentFactory.ruta("action-cancel"));
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        btnAceptar.addActionListener(e->{
            if (onAccept!=null){
                onAccept.run();
            }
            dispose();
        });
        btnCancelar.addActionListener(e -> dispose());
        return panelBotones;
    }
}

package resources.Utils;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(Image image) {
        this.backgroundImage = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen estirada para llenar el panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

package resources.Shared.Notification;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class GraphicsUtilities {
    private GraphicsUtilities() {
    }

    public static void getPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
        if (w == 0 || h == 0) {
            return;
        }

        if (pixels == null) {
            pixels = new int[w * h];
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length"
                    + " >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_INT_RGB) {
            Raster raster = img.getRaster();
            raster.getDataElements(x, y, w, h, pixels);
            return;
        }
        // Unmanages the image
        img.getRGB(x, y, w, h, pixels, 0, w);
    }

    public static void setPixels(BufferedImage img, int x, int y, int w, int h, int[] pixels) {
        if (pixels == null || w == 0 || h == 0) {
            return;
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length"
                    + " >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_INT_RGB) {
            WritableRaster raster = img.getRaster();
            raster.setDataElements(x, y, w, h, pixels);
        } else {
            // Unmanages the image
            img.setRGB(x, y, w, h, pixels, 0, w);
        }
    }
}

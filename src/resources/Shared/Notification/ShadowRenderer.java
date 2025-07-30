package resources.Shared.Notification;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShadowRenderer {
    private int size;
    private float opacity;
    private Color color;
    public ShadowRenderer() {
        this(5, 0.5f, Color.BLACK);
        size=5;
        opacity=0.5f;
        color=Color.BLACK;
    }
    public ShadowRenderer(final int size, final float opacity, final Color color) {
        this.size = size;
        this.opacity = opacity;
        this.color = color;
    }

    public BufferedImage createShadow(final BufferedImage image) {
        int shadowSize = size * 2;
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();
        int dstWidth = srcWidth + shadowSize;
        int dstHeight = srcHeight + shadowSize;
        int left = size;
        int right = shadowSize - left;
        int yStop = dstHeight - right;
        int shadowRgb = color.getRGB() & 0x00FFFFFF;
        int[] aHistory = new int[shadowSize];
        int historyIdx;
        int aSum;
        BufferedImage dst = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
        int[] dstBuffer = new int[dstWidth * dstHeight];
        int[] srcBuffer = new int[srcWidth * srcHeight];
        GraphicsUtilities.getPixels(image, 0, 0, srcWidth, srcHeight, srcBuffer);
        int lastPixelOffset = right * dstWidth;
        float hSumDivider = 1.0f / shadowSize;
        float vSumDivider = opacity / shadowSize;
        int[] hSumLookup = new int[256 * shadowSize];
        for (int i = 0; i < hSumLookup.length; i++) {
            hSumLookup[i] = (int) (i * hSumDivider);
        }
        int[] vSumLookup = new int[256 * shadowSize];
        for (int i = 0; i < vSumLookup.length; i++) {
            vSumLookup[i] = (int) (i * vSumDivider);
        }
        int srcOffset;
        for (int srcY = 0, dstOffset = left * dstWidth; srcY < srcHeight; srcY++) {
            for (historyIdx = 0; historyIdx < shadowSize;) {
                aHistory[historyIdx++] = 0;
            }
            aSum = 0;
            historyIdx = 0;
            srcOffset = srcY * srcWidth;
            for (int srcX = 0; srcX < srcWidth; srcX++) {
                int a = hSumLookup[aSum];
                dstBuffer[dstOffset++] = a << 24;   // store the alpha value only
                aSum -= aHistory[historyIdx]; // substract the oldest pixel from the sum
                a = srcBuffer[srcOffset + srcX] >>> 24;
                aHistory[historyIdx] = a;   // ... and store its value into history
                aSum += a;                  // ... and add its value to the sum
                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
            for (int i = 0; i < shadowSize; i++) {
                int a = hSumLookup[aSum];
                dstBuffer[dstOffset++] = a << 24;

                // substract the oldest pixel from the sum ... and nothing new to add!
                aSum -= aHistory[historyIdx];

                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
        }
        for (int x = 0, bufferOffset = 0; x < dstWidth; x++, bufferOffset = x) {
            aSum = 0;
            for (historyIdx = 0; historyIdx < left;) {
                aHistory[historyIdx++] = 0;
            }
            for (int y = 0; y < right; y++, bufferOffset += dstWidth) {
                int a = dstBuffer[bufferOffset] >>> 24;         // extract alpha
                aHistory[historyIdx++] = a;                     // store into history
                aSum += a;                                      // and add to sum
            }
            bufferOffset = x;
            historyIdx = 0;
            for (int y = 0; y < yStop; y++, bufferOffset += dstWidth) {
                int a = vSumLookup[aSum];
                dstBuffer[bufferOffset] = a << 24 | shadowRgb;  // store alpha value + shadow color
                aSum -= aHistory[historyIdx];   // substract the oldest pixel from the sum
                a = dstBuffer[bufferOffset + lastPixelOffset] >>> 24;   // extract the new pixel ...
                aHistory[historyIdx] = a;                               // ... and store its value into history
                aSum += a;                                              // ... and add its value to the sum
                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
            for (int y = yStop; y < dstHeight; y++, bufferOffset += dstWidth) {
                int a = vSumLookup[aSum];
                dstBuffer[bufferOffset] = a << 24 | shadowRgb;

                aSum -= aHistory[historyIdx];   // substract the oldest pixel from the sum

                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
        }
        GraphicsUtilities.setPixels(dst, 0, 0, dstWidth, dstHeight, dstBuffer);
        return dst;
    }
}

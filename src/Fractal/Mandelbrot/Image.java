// Fractal/Mandelbrot/Image.java
package Fractal.Mandelbrot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import util.Complex;

public class Image extends BufferedImage {

    private Graphics2D graphics2d;
    private int SCREEN_SIZE;
    private int MAX_ITER;

    public Image(int SCREEN_SIZE, int MAX_ITER) {
        super(SCREEN_SIZE, SCREEN_SIZE, BufferedImage.TYPE_INT_RGB);
        this.SCREEN_SIZE = SCREEN_SIZE;
        this.MAX_ITER = MAX_ITER;
    }

    public void setGraphics2d(Graphics2D graphics2d) {

        this.graphics2d = graphics2d;
    }
    
    // Draw Mandelbrot set
    public void drawSet(double MID_X, double MID_Y, double RANGE, float brightness, String mathod, Rectangle clip) {

        int resolution = 32;
        while (resolution >= 1) {
            int s2 = SCREEN_SIZE / 2;
            for (int y = 0; y < SCREEN_SIZE; y += resolution) {
                for (int x = 0; x < SCREEN_SIZE; x += resolution) {
                    if (clip == null || clip.contains(x, y)) {
                        int n = 0;
                        Complex z = new Complex(0.0, 0.0);
                        Complex c = new Complex((RANGE * (x - s2)) / s2 + MID_X,
                                (RANGE * (y - s2)) / s2 + MID_Y);
                        while (n++ < MAX_ITER && z.modulus() < 4.0) {
                            z = z.multiply(z).add(c);
                        }
                        if (n >= MAX_ITER)
                            graphics2d.setColor(Color.BLACK);
                        else {
                            float hue = (float) (n % 64) / 64.0f;
                            float saturation;

                            // Apply selected function for color variation in functionChooserBox
                            switch (mathod) {
                                case "sin":
                                    saturation = (float) (0.6 + 0.4 * Math.sin((double) n / 40.0));
                                    break;

                                case "asin":
                                    saturation = (float) (0.6 + 0.4 * Math.asin((double) n / 40.0));
                                    break;

                                case "acos":
                                    saturation = (float) (0.6 + 0.4 * Math.acos((double) n / 40.0));
                                    break;

                                case "tan":
                                    saturation = (float) (0.6 + 0.4 * Math.tan((double) n / 40.0));
                                    break;

                                case "atan":
                                    saturation = (float) (0.6 + 0.4 * Math.atan((double) n / 40.0));
                                    break;

                                case "log":
                                    saturation = (float) (0.6 + 0.4 * Math.log((double) n / 40.0));
                                    break;

                                case "log10":
                                    saturation = (float) (0.6 + 0.4 * Math.log10((double) n / 40.0));
                                    break;

                                case "ceil":
                                    saturation = (float) (0.6 + 0.4 * Math.ceil((double) n / 40.0));
                                    break;

                                case "floor":
                                    saturation = (float) (0.6 + 0.4 * Math.floor((double) n / 40.0));
                                    break;

                                case "signum":
                                    saturation = (float) (0.6 + 0.4 * Math.signum((double) n / 40.0));
                                    break;

                                default:
                                    saturation = (float) (0.6 + 0.4 * Math.cos((double) n / 40.0));
                                    break;
                            }

                            // Set color based on hue, saturation, and brightness
                            graphics2d.setColor(Color.getHSBColor(hue, saturation, brightness));
                        }
                        // fill 1 chunk of RESxRES
                        graphics2d.fillRect(x, SCREEN_SIZE - y - resolution, resolution, resolution);
                    }
                }
            }
            resolution /= 2;
        }
    }

}

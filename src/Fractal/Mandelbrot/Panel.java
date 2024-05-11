// Fractal/Mandelbrot/Panel.java
package Fractal.Mandelbrot;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel
        implements MouseListener, MouseMotionListener, MouseWheelListener {

   

    // Help display Toggle
    public void toggleHelp() {
        this.DISPLAY_HELP = !this.DISPLAY_HELP;
    }

    private final int MAX_ITER = 500;
    private final int SCREEN_SIZE = 800;
    private double MID_X = -0.25;
    private double MID_Y = 0.85;
    private double RANGE = 0.004;

    private Image mandelbrotImage;
    private Rectangle selectionRectangle;

    private int startX, startY;
    private int mouseX, mouseY;
    private int endX, endY;

    private JLabel pointerLabel;

    private boolean DISPLAY_CROSSHAIRS = true;
    private boolean DISPLAY_HELP = true;

    private Graphics2D graphics2d;

    public float brightness = 1.0f;
    public String mathod = "cos";

    public Panel() {
        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        mandelbrotImage = new Image(SCREEN_SIZE, MAX_ITER);
        graphics2d = (Graphics2D)mandelbrotImage.createGraphics();
        mandelbrotImage.setGraphics2d(graphics2d);

        setView();

        // Initialize pointerLabel
        pointerLabel = new JLabel("Pointer: ");
        add(pointerLabel); // Add label to the panel
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Zoom in on left click, zoom out on right click
        if(!SwingUtilities.isMiddleMouseButton(e)) {

            MID_X = MID_X + RANGE * (e.getX() - SCREEN_SIZE / 2) / (SCREEN_SIZE / 2);
            MID_Y = MID_Y + RANGE * (SCREEN_SIZE / 2 - e.getY()) / (SCREEN_SIZE / 2);
            if (SwingUtilities.isLeftMouseButton(e)) {
                RANGE /= 2;
            } else if (SwingUtilities.isRightMouseButton(e)) {
                RANGE *= 2;
            }
            setView();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Store starting position for middle click drag
        if (e.getButton() == MouseEvent.BUTTON2) {
            startX = e.getX();
            startY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Perform zoom to box on middle click release
        if (e.getButton() == MouseEvent.BUTTON2) {
            endX = e.getX();
            endY = e.getY();

            int width = Math.abs(endX - startX);
            int height = Math.abs(endY - startY);

            double minMidX = MID_X + RANGE * (Math.min(startX, endX) - SCREEN_SIZE / 2) / (SCREEN_SIZE / 2);
            double maxMidY = MID_Y + RANGE * (SCREEN_SIZE / 2 - Math.max(startY, endY)) / (SCREEN_SIZE / 2);

            MID_X = minMidX + RANGE * width / SCREEN_SIZE;
            MID_Y = maxMidY - RANGE * height / SCREEN_SIZE;
            RANGE *= Math.max(width, height) / (double) SCREEN_SIZE;

            setView();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Pan on left click drag, draw selection rectangle on middle click drag
        if (SwingUtilities.isLeftMouseButton(e)) {
            double dx = e.getX() - mouseX;
            double dy = e.getY() - mouseY;
            MID_X -= dx * RANGE / SCREEN_SIZE;
            MID_Y += dy * RANGE / SCREEN_SIZE;
            mouseX = e.getX();
            mouseY = e.getY();
            setView();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            endX = e.getX();
            endY = e.getY();
            selectionRectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update pointer label with current coordinates
        double real = MID_X + RANGE * (e.getX() - SCREEN_SIZE / 2) / (SCREEN_SIZE / 2);
        double imag = MID_Y + RANGE * (SCREEN_SIZE / 2 - e.getY()) / (SCREEN_SIZE / 2);
        mouseX = e.getX();
        mouseY = e.getY();
        if (RANGE < 0.0001)
            pointerLabel.setText(String.format("Pointer: %g + %gi", real, imag));
        else
            pointerLabel.setText(String.format("Pointer: %.4f + %.4fi", real, imag));
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Zoom in/out on scroll wheel
        MID_X = MID_X + RANGE * (e.getX() - SCREEN_SIZE / 2) / (SCREEN_SIZE / 2);
        MID_Y = MID_Y + RANGE * (SCREEN_SIZE / 2 - e.getY()) / (SCREEN_SIZE / 2);

        int rot = e.getWheelRotation();

        if (rot < 0) {
            RANGE *= 0.9 ;
        } else if (e.getWheelRotation() > 0) {
            RANGE *= 1.1;
        }
        RANGE *= Math.abs(rot);
        
        setView();
    }

    // Help strings to display
    private String[] helpStrings = {
        "How To Use:",
        "1. Left Click to Zoom at Point",
        "2. Left Click + Drag to Pan",
        "3. Right Click to Zoom Out at Point",
        "4. Middle Click + Drag to Zoom to Box",
        "5. Scroll Wheel to Zoom In/Out",
        "6. Change Image Brightness using Slider"
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(mandelbrotImage, 0, 0, this);
        if (selectionRectangle != null) {
            g2d.setColor(new Color(0x33, 0x33, 0x33, 0x90));
            g2d.fillRect(selectionRectangle.getLocation().x, selectionRectangle.getLocation().y,
                    (int) selectionRectangle.getWidth(), (int) selectionRectangle.getHeight());
            g2d.draw(selectionRectangle);
        }

        if (DISPLAY_CROSSHAIRS) {
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawLine(0, mouseY, 800, mouseY);
            g2d.drawLine(mouseX, 0, mouseX, 800);

            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(mouseX - 20, mouseY - 20, 40, 40, 40, 40);
        }
        
        // Display help strings
        if(DISPLAY_HELP) {
            g2d.setColor(Color.BLACK);
            int strStartY = 20;
            for(int i = 0; i < helpStrings.length; i++,strStartY+=20) {
                g2d.drawString(helpStrings[i], 20, strStartY);
            }
        }
    }

    /**
     * Iterate through mandelbrotImage and change brightness of each Pixel
     */
    public void updateBrightness() {
        for (int i = 0; i < SCREEN_SIZE; i++) {
            for (int j = 0; j < SCREEN_SIZE; j++) {
                int color = mandelbrotImage.getRGB(i, j);
                int r = (0xFF0000 & color) >> 16; // Get red
                int g = (0x00FF00 & color) >> 8;  // Get green
                int b = 0x0000FF & color;         // Get blue
                float[] hsb = Color.RGBtoHSB(r, g, b, null);
                graphics2d.setColor(Color.getHSBColor(hsb[0], hsb[1], (hsb[2] != 0 ? brightness : 0)));
                graphics2d.fillRect(i, j, 1, 1);
            }
        }
    }

    /**
     * Repaint the Panel
     */
    public void setView() {
        mandelbrotImage.drawSet(MID_X,MID_Y,RANGE,brightness, mathod,null);
        selectionRectangle = null;
        repaint();
    }

    // Reset to default view
    public void gotoReset() {
        MID_X = -0.25;
        MID_Y = 0.85;
        RANGE = 0.004;

        setView();
    }

    // Save image to file
    public void saveImage() {

        JFileChooser fs = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int r = fs.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            try {
                String fpath = fs.getSelectedFile().getAbsolutePath();
                File outputFile = new File(fpath, "mandelbrot.png");
                ImageIO.write(mandelbrotImage, "png", outputFile);
            } catch (IOException e) {
                System.out.println("File does not exist!");
                pointerLabel.setText("File does not exist!");
            }
        }

    }

    // Toggle display of crosshairs
    public void toggleCrosshairDisplay() {
        this.DISPLAY_CROSSHAIRS = !this.DISPLAY_CROSSHAIRS;
    }
}

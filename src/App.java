// App.java
import javax.swing.*;

import Fractal.Mandelbrot.Panel;

import java.awt.*;

public class App extends JPanel {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mandelbrot Set Plotter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel mandelbrotViewer = new Panel();

        // Buttons
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(e -> mandelbrotViewer.toggleHelp());

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> mandelbrotViewer.gotoReset());

        JButton crosshairsButton = new JButton("Crosshairs");
        crosshairsButton.addActionListener(e -> mandelbrotViewer.toggleCrosshairDisplay());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> mandelbrotViewer.saveImage());

        // Dropdown for color functions
        String[] colorFuncs = { "sin", "cos", "tan", "asin", "acos", "atan", "log", "log10", "signum", "ceil",
                "floor" };
        JComboBox<String> functionChooserBox = new JComboBox<>(colorFuncs);
        functionChooserBox.addActionListener(e -> {
            mandelbrotViewer.mathod = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
            mandelbrotViewer.setView();
        });

        // Slider for brightness
        JSlider brightnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        brightnessSlider.addChangeListener(e -> {
            mandelbrotViewer.brightness = (float) ((JSlider) e.getSource()).getValue() / (float) 100;
            mandelbrotViewer.updateBrightness();
        });

        // Panel to hold buttons and sliders
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(helpButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(crosshairsButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(functionChooserBox);
        buttonPanel.add(brightnessSlider);

        // Frame setup
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(mandelbrotViewer, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

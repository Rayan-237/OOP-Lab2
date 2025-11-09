package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model storing RGB values and notifying listeners when updated.
 */
public class ColorModel {
    private int red, green, blue;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public ColorModel() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public int getRed() { return red; }
    public int getGreen() { return green; }
    public int getBlue() { return blue; }

    // Setters with validation (1-255)
    public void setRed(int red) { updateColor(validate(red), green, blue); }
    public void setGreen(int green) { updateColor(red, validate(green), blue); }
    public void setBlue(int blue) { updateColor(red, green, validate(blue)); }

    private int validate(int value) {
        if (value < 1) return 1;
        if (value > 255) return 255;
        return value;
    }

    private void updateColor(int r, int g, int b) {
        int[] oldColor = { this.red, this.green, this.blue };
        this.red = r;
        this.green = g;
        this.blue = b;
        int[] newColor = { r, g, b };
        pcs.firePropertyChange("modelColor", oldColor, newColor);
    }

    // Listener methods
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
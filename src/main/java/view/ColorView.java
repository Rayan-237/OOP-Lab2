package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import presenter.ColorPresenter;

public class ColorView extends JFrame implements ActionListener, FocusListener {

    private final JTextField txtRed = new JTextField(5);
    private final JTextField txtGreen = new JTextField(5);
    private final JTextField txtBlue = new JTextField(5);
    private final JPanel colorPanel = new JPanel();

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public ColorView() {
        super("RGB Color Swatch");
        setLayout(new FlowLayout());

        add(new JLabel("Red:"));   add(txtRed);
        add(new JLabel("Green:")); add(txtGreen);
        add(new JLabel("Blue:"));  add(txtBlue);

        colorPanel.setPreferredSize(new Dimension(150, 150));
        colorPanel.setBackground(Color.BLACK);
        add(colorPanel);

        txtRed.addActionListener(this);
        txtGreen.addActionListener(this);
        txtBlue.addActionListener(this);

        txtRed.addFocusListener(this);
        txtGreen.addFocusListener(this);
        txtBlue.addFocusListener(this);

        setSize(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireColorChange();
    }

    @Override
    public void focusLost(FocusEvent e) {
        fireColorChange();
    }

    @Override
    public void focusGained(FocusEvent e) {}

    private void fireColorChange() {
        int r = validateInput(txtRed);
        int g = validateInput(txtGreen);
        int b = validateInput(txtBlue);
        pcs.firePropertyChange("colorChanged", null, new int[]{r, g, b});
    }

    private int validateInput(JTextField field) {
        try {
            int value = Integer.parseInt(field.getText());
            if (value < 1) value = 1;
            if (value > 255) value = 255;
            field.setText(String.valueOf(value));
            return value;
        } catch (NumberFormatException e) {
            field.setText("1");
            return 1;
        }
    }

    // Listen to presenter updates
    public void bindPresenter(ColorPresenter presenter) {
        presenter.addPropertyChangeListener(evt -> {
            if ("updateViewColor".equals(evt.getPropertyName())) {
                int[] rgb = (int[]) evt.getNewValue();
                colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
            }
        });
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
package presenter;

import model.ColorModel;
import view.ColorView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Presenter listens to View and Model, and communicates via PropertyChangeEvents only.
 */
public class ColorPresenter implements PropertyChangeListener {

    private final ColorModel model;
    private final ColorView view;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public ColorPresenter(ColorModel model, ColorView view) {
        this.model = model;
        this.view = view;

        // Listen to view input
        view.addPropertyChangeListener(this);
        // Listen to model changes
        model.addPropertyChangeListener(this);
    }

    /**
     * Presenter forwards model changes to listeners (View will subscribe)
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Input from View
        if ("colorChanged".equals(evt.getPropertyName())) {
            int[] colors = (int[]) evt.getNewValue();
            model.setRed(colors[0]);
            model.setGreen(colors[1]);
            model.setBlue(colors[2]);
        }

        // Changes from Model
        if ("modelColor".equals(evt.getPropertyName())) {
            int[] rgb = (int[]) evt.getNewValue();
            // Fire an event to notify View
            pcs.firePropertyChange("updateViewColor", null, rgb);
        }
    }
}
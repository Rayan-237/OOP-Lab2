import model.ColorModel;
import view.ColorView;
import presenter.ColorPresenter;
import javax.swing.SwingUtilities;

/**
 * Application entry point.
 * Creates MVC components and launches the GUI in the Event Dispatch Thread.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Instantiate MVC components
            ColorModel model = new ColorModel();
            ColorView view = new ColorView();
            ColorPresenter presenter = new ColorPresenter(model, view);
            view.bindPresenter(presenter);
        });
    }
}
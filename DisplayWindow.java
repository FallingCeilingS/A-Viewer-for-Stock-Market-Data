import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayWindow extends Stage {
    private Label label;
    private int width, height;

    public DisplayWindow() {
        if (UrlData.validation) {
            label = new Label("Display Ticker Data\n" + UrlData.ticker);
            CsvData.readFile();
            width = 600;
            height = 400;
        } else {
            label = new Label("Error!\n" + UrlData.errMsg);
            width = 300;
            height = 200;
        }
        StackPane secondPane = new StackPane(label);
        Scene secondScene = new Scene(secondPane, width, height);
        this.setScene(secondScene);
    }
}

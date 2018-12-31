import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayWindow extends Stage {
    private Label label;
    public DisplayWindow() {
        if (UrlData.validation) {
            label = new Label("Display Ticker Data");
        } else {
            label = new Label(UrlData.errMsg);
        }
        StackPane secondPane = new StackPane(label);
        Scene secondScene = new Scene(secondPane, 300, 200);
        this.setScene(secondScene);
    }
}

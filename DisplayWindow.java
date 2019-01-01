import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayWindow extends Stage {
    private Label label;
    private int width, height;
    StackPane secondPane;

    public DisplayWindow() {
        if (UrlData.validation) {
            CsvData.readFile();
            width = 600;
            height = 400;
            secondPane = new StackPane();
            if (CsvData.TickerData.isEmpty()) {
                label = new Label("No Data to Display in Selected Date Range!");
                secondPane.getChildren().add(label);
            } else {
                FlowPane mainChartPane = new MainChartPane("Ticker Data - " + UrlData.ticker);
                secondPane.getChildren().add(mainChartPane);
            }
        } else {
            label = new Label("Error!\n" + UrlData.errMsg);
            width = 300;
            height = 200;
            secondPane = new StackPane(label);
        }
        Scene secondScene = new Scene(secondPane, width, height);
        this.setScene(secondScene);
    }
}

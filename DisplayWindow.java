import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayWindow extends Stage {
    private Label label;
    private int width, height;
    GridPane secondPane;

    public DisplayWindow() {
        secondPane = new GridPane();
        secondPane.setAlignment(Pos.CENTER);

        if (UrlData.validation) {
            CsvData.readFile();
            width = 600;
            height = 400;

            if (CsvData.TickerData.isEmpty()) {
                label = new Label("No Data to Display in Selected Date Range!");
                secondPane.getChildren().add(label);
            } else {
                StackPane mainChartPane = new MainChartPane("Ticker Data - " + UrlData.ticker);
                StackPane volumeChartPane = new VolumeChartPane("Volume Data - " + UrlData.ticker);

                secondPane.add(mainChartPane, 0, 0);
                secondPane.add(volumeChartPane, 0, 1);

//                mainChartPane.setStyle("-fx-border-color: black");
//                volumeChartPane.setStyle("-fx-border-color: black");
                mainChartPane.prefWidthProperty().bind(secondPane.widthProperty());
                volumeChartPane.prefWidthProperty().bind(secondPane.heightProperty());
            }
        } else {
            label = new Label("Error!\n" + UrlData.errMsg);
            width = 300;
            height = 200;
            secondPane.getChildren().add(label);
        }

        Scene secondScene = new Scene(secondPane, width, height);

        this.setScene(secondScene);
    }
}

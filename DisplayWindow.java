import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayWindow extends Stage {
    private String title;
    private Label label;
    private int width, height;

    public DisplayWindow() {
        GridPane secondPane = new GridPane();
        secondPane.setAlignment(Pos.CENTER);

        if (UrlData.validation) {
            CsvData.readFile();
            if (CsvData.TickerData.isEmpty()) {
                title = "Empty DATA";

                width = 720;
                height = 540;

                label = new Label(
                        "No Data to Display in Selected Date Range!\n" +
                        "Please CLOSE this WINDOW and TRY AGAIN!"
                );
                secondPane.getChildren().add(label);
            } else {
                title = "Display Stock DATA";

                width = 1024;
                height = 768;

                StackPane topPane = new TopPane();
                StackPane mainChartPane = new MainChartPane("Price Data - " + UrlData.ticker);
                StackPane volumeChartPane = new VolumeChartPane("Volume Data - " + UrlData.ticker);

                secondPane.add(topPane, 0, 0);
                secondPane.add(mainChartPane, 0, 1);
                secondPane.add(volumeChartPane, 0, 2);

//                mainChartPane.setStyle("-fx-border-color: black");
//                volumeChartPane.setStyle("-fx-border-color: black");
                mainChartPane.prefWidthProperty().bind(secondPane.widthProperty());
                volumeChartPane.prefWidthProperty().bind(secondPane.heightProperty());

                this.setMinWidth(width);
                this.setMinHeight(height);
            }
        } else {
            title = "Invalid message";

            label = new Label("Error!\n" + UrlData.errMsg);
            width = 300;
            height = 200;
            secondPane.getChildren().add(label);
        }

        Scene secondScene = new Scene(secondPane, width, height);

        this.setTitle(title);
        this.setScene(secondScene);
    }
}

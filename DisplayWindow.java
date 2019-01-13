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
            // in the condition that no data available in the selected range of date
            if (CsvData.TickerData.isEmpty()) {
                title = "Empty DATA";

                width = 720;
                height = 540;

                label = new Label(
                        "No Data to Display! Due to: \n" +
                        UrlData.errMsg +
                        "Please CLOSE this WINDOW and TRY AGAIN!"
                );
                secondPane.getChildren().add(label);
            } else {
                // in the condition that data has been retrieved from the Internet and not empty
                // display graphs showing the stock's high, low, opening, closing and volume value for each day
                // in the range that was entered
                title = "Display Stock Market DATA";

                width = 1024;
                height = 768;

                // the pane on the top, which contains a button
                // click the button to show the description and user guide of the graphs
                // makes it easier for users to understand the representation of graphs
                StackPane userGuideButtonPane = new UserGuideButtonPane();
                // these panes display the information retrieved from WSJ
                // the graph pane displays price (high, low, opening and closing) data for each day
                StackPane mainChartPane = new MainChartPane("Price Data - " + UrlData.ticker);
                // the graph pane displays volume data for each day
                StackPane volumeChartPane = new VolumeChartPane("Volume Data - " + UrlData.ticker);

                secondPane.add(userGuideButtonPane, 0, 0);
                secondPane.add(mainChartPane, 0, 1);
                secondPane.add(volumeChartPane, 0, 2);

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

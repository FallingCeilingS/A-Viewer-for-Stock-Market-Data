/**
 * This class construct the window of displaying relevant type of information,
 * including error message and graphs.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * DisplayWindow class
 */
public class DisplayWindow extends Stage {
    private String title;
    private Label label;
    private int width, height;

    /**
     * the constructor function
     */
    public DisplayWindow() {
        // construct a GridPane to contain different types of information
        GridPane secondPane = new GridPane();
        secondPane.setAlignment(Pos.CENTER);

        if (UrlData.validation) {
            CsvData.readFile();
            // in the condition that no data available in the selected range of date
            if (CsvData.TickerData.isEmpty()) {
                title = "Empty DATA";

                width = 720;
                height = 540;

                // display the empty error message:
                // either by no Internet connection or no relevant data in the selected date range for specific Ticker
                label = new Label(
                        "No Data to Display! Due to: \n" +
                                UrlData.errMsg +
                                "Please CLOSE this WINDOW and TRY AGAIN!"
                );
                // add label to the pane
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

                /*
                add components to the pane
                 */
                secondPane.add(userGuideButtonPane, 0, 0);
                secondPane.add(mainChartPane, 0, 1);
                secondPane.add(volumeChartPane, 0, 2);

                /*
                bind width and height properties to the pane so that the pane can be resized
                when resizing the window
                 */
                mainChartPane.prefWidthProperty().bind(secondPane.widthProperty());
                volumeChartPane.prefWidthProperty().bind(secondPane.heightProperty());

                /*
                set minimum width and height
                 */
                this.setMinWidth(width);
                this.setMinHeight(height);
            }
        } else {
            // in this case user does not provide sufficient data for data retrieving
            title = "Invalid message";

            // set error message to the label
            label = new Label("Error!\n" + UrlData.errMsg);
            width = 300;
            height = 200;
            // add label to the pane
            secondPane.getChildren().add(label);
        }

        // construct a scene with pane
        Scene secondScene = new Scene(secondPane, width, height);

        // set title and scene
        this.setTitle(title);
        this.setScene(secondScene);
    }
}

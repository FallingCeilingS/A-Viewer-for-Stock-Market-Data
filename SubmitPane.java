/**
 * The class construct the submit pane for submitting user data,
 * and validate whether the data is sufficient and there has Internet connection.
 * If it is valid situation, the program will call functions to retrieve data from the Internet.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * SubmitPane class
 */
public class SubmitPane extends FlowPane {
    // declare member component
    private Button submitButton;

    /**
     * constructor
     */
    public SubmitPane() {
        // construct a button
        submitButton = new Button();
        submitButton.setText("Submit");
        // add button to the pane
        this.getChildren().add(submitButton);

        // add tooltip for the button
        Tooltip.install(submitButton, new Tooltip("Click Here to Submit your Request.\n" +
                "MAX RETRIEVE DATA ITEM: 300."));

        // set the event handler to the button
        // when the button is pressed, another window to display relevant information
        // (error message, empty data warning or graph showing the stock's data retrieved from the Internet)
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // delete the exist *.csv file
                // in case that there is no Internet connection, the program will parse the exist file
                CsvData.deleteFile();
                // validate whether the user input is valid for retrieving data
                UrlData.setValidation();
                // if the user's selection is valid, the program will set an URL for downloading data
                UrlData.setUrl();
                // download data from the WSJ Finance server, and store the *.csv file
                UrlData.saveUrlAs();

                // when displaying the new window, disable the button so that it will not pop up multiple new windows
                submitButton.setDisable(true);

                // then pop up a window to display relevant message
                Stage secondStage = new DisplayWindow();
                secondStage.show();

                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    /**
                     * enable the button when then window is closed
                     * @param event WindowEvent the event that the window is closed
                     */
                    @Override
                    public void handle(WindowEvent event) {
                        submitButton.setDisable(false);
                    }
                });
            }
        });
    }
}

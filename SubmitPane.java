import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SubmitPane extends FlowPane {
    private Button submitButton;

    public SubmitPane() {
        submitButton = new Button();
        submitButton.setText("Submit");
        this.getChildren().add(submitButton);

        Tooltip.install(submitButton, new Tooltip("Click Here to Submit your Request.\n" +
                "MAX RETRIEVE DATA ITEM: 300."));

        // set the event handler to the button
        // when the button is pressed, another window to display relevant information
        // (error message, empty data warning or graph showing the stock's data retrieved from the Internet)
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // validate whether the user input is valid for retrieving data
                UrlData.setValidation();
                // if the user's selection is valid, the program will set an URL for downloading data
                UrlData.setUrl();
                // download data from the WSJ Finance server, and store the *.csv file
                UrlData.saveUrlAs();

                submitButton.setDisable(true);

                // then pop up a window to display relevant message
                Stage secondStage = new DisplayWindow();
                secondStage.show();

                secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        submitButton.setDisable(false);
                    }
                });
            }
        });
    }
}

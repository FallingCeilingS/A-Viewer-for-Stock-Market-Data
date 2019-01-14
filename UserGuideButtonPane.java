/**
 * This class constructs a pane contains the button to pop up a new window of user guide (description of the graph).
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * UserGuideButton Pane
 */
public class UserGuideButtonPane extends StackPane {
    /**
     * the constructor function
     */
    public UserGuideButtonPane() {
        // construct a new button
        Button button = new Button("Show Diagram Description (User Guide)");
        // add button
        this.getChildren().add(button);

        // add tooltip
        Tooltip.install(button, new Tooltip("Click Here to Show Description (Explanation) of Diagram."));

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * when the new window pops up, set the button disabled so that it cannot pop up multiple windows
             * @param event MouseEvent, the click event of mouse
             */
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked");

                // disable the button
                button.setDisable(true);

                // pop up a new window
                Stage thirdStage = new DescriptionWindow();
                thirdStage.show();

                thirdStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    /**
                     * when the new window closed, set the button enabled
                     * @param event WindowEvent, the close request
                     */
                    @Override
                    public void handle(WindowEvent event) {
                        // enable the button
                        button.setDisable(false);
                    }
                });
            }
        });
    }
}

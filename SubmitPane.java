import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SubmitPane extends FlowPane {
    private Button submitButton;

    public SubmitPane() {
        submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked");

                submitButton.setDisable(true);

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

        this.getChildren().add(submitButton);
    }
}

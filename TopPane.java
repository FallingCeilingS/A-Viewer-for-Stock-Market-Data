import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TopPane extends StackPane {
    public TopPane() {
        Button button = new Button("Show Diagram Description");
        this.getChildren().add(button);

        Tooltip.install(button, new Tooltip("Click Here to Show Description (Explaination) of Diagram."));

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked");

                button.setDisable(true);

                Stage thirdStage = new DescriptionWindow();
                thirdStage.show();

                thirdStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        button.setDisable(false);
                    }
                });
            }
        });
    }
}

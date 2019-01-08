import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DescriptionWindow extends Stage {
    public DescriptionWindow() {
        int width = 720;
        int height = 405;

        StackPane descriptionPane = new StackPane();
        Image image = new Image("banner-sample.png", true);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.setPreserveRatio(true);

        descriptionPane.getChildren().add(imageView);

        Scene thirdScene = new Scene(descriptionPane, width, height);

        this.setTitle("Description of Diagram");
        this.setScene(thirdScene);
    }
}

/**
 * This class construct the window of description of the graph,
 * display the image of description.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * DescriptionWindow class
 */
public class DescriptionWindow extends Stage {
    /**
     * the constructor function
     */
    public DescriptionWindow() {
        int width = 720;
        int height = 405;

        // instantiate a pane
        StackPane descriptionPane = new StackPane();
        // load an image
        Image image = new Image("banner-sample.png", true);
        // instantiate an ImageView object and set image
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        // set flexible resizing property
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.fitHeightProperty().bind(this.heightProperty());
        // preserve image ratio
        imageView.setPreserveRatio(true);

        // add component to the pane
        descriptionPane.getChildren().add(imageView);

        // construct a scene and add pane
        Scene thirdScene = new Scene(descriptionPane, width, height);

        // set window title
        this.setTitle("Description (User Guide) of Diagram");
        // set scene
        this.setScene(thirdScene);
    }
}

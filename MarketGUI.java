import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;

public class MarketGUI extends Application {

    public static void main(String[] args) {
        System.out.println("execution");

//        JFrame inputFrame = new InputDataFrame();
//        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        inputFrame.setVisible(true);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);

        FlowPane submitPane = new SubmitPane();
        root.getChildren().add(submitPane);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // use Platform.runLater(new Runnable() {//...}) solve the problem:
                // Not on FX application thread; currentThread=JavaFX Application Thread error
                // when using Platform.exit() directly
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                });
            }
        });
    }
}

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MarketGUI extends Application {

    public static void main(String[] args) {
        System.out.println("execution");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(root, 600, 250);

        FlowPane tickerSelectPane = new TickerSelectPane();
        root.add(tickerSelectPane, 0, 0);

        FlowPane datePickerPane = new DatePickerPane();
        root.add(datePickerPane, 0, 2);

        FlowPane submitPane = new SubmitPane();
        root.add(submitPane, 0, 4);
        submitPane.setAlignment(Pos.CENTER);

        primaryStage.setTitle("Market GUI");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(250);
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

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

    /**
     * when the program starts, display a window with dropdown boxes
     * @param primaryStage the first stage displayed when the program starts
     * @throws Exception the exception of the program
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(root, 580, 300);

        // a panel that contains a dropdown box for the user to select a ticker symbol from a list
        FlowPane tickerSelectPane = new TickerSelectPane();
        root.add(tickerSelectPane, 0, 0);

        // a panel that contains two dropdown calendars for the user to select a start and an end date
        FlowPane datePickerPane = new DatePickerPane();
        root.add(datePickerPane, 0, 2);

        // a panel that contains a button to submit user's request for specific ticker symbol and date range,
        // and retrieve the appropriate stock market data
        FlowPane submitPane = new SubmitPane();
        root.add(submitPane, 0, 4);
        submitPane.setAlignment(Pos.CENTER);

        primaryStage.setTitle("Market GUI");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(320);
        primaryStage.setMinHeight(300);
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

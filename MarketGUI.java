/**
 * This class is the main class of the whole program.
 * Design and build a viewer for stock market data.
 * The viewer download the data from the internet and present it to the user via a GUI.
 *
 * When the program starts, a window displays with drop down boxes containing 10 ticker symbols for the
 * user to select a ticker symbol from a list, and select the start and end dates using drop down
 * calendars for the years, months and days. When the user has selected the dates and ticker
 * symbol, clicking on a "Submit" button should retrieve the appropriate stock market data.
 * The data that is retrieved then is displayed in a new window.
 * If the user select a date range have no data, or there is no Internet connection,
 * or user has not selected sufficient data from drop down boxes,
 * the new window will display error message. In contrast, if the user select sufficient data,
 * and there is good Internet connection, and the range of date in the ticker symbol has data, this window
 * should display a graph showing the stock’s closing value for each day in the range that
 * was entered.
 *
 * Additionally, if the user click the button "Description (User Guide) of Diagram", it will pop up
 * a new window to display the description of the graph for better understanding the diagram.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
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

/**
 * main class, MarketGUI
 */
public class MarketGUI extends Application {

    /**
     * main function
     * @param args String[], default argument in main function
     */
    public static void main(String[] args) {
        System.out.println("execution");

        // launch window
        launch(args);
    }

    /**
     * when the program starts, display a window with dropdown boxes
     * @param primaryStage Stage, the first stage displayed when the program starts
     * @throws Exception the exception of the program
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // create a new GridPane
        GridPane root = new GridPane();
        // set layout
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        // create a new scene
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

        /*
        set attributes of primaryStage,
        including title, scene, minimum width and height
         */
        primaryStage.setTitle("Market GUI");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(320);
        primaryStage.setMinHeight(300);
        // display window
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // use Platform.runLater(new Runnable() {//...}) solve the problem:
                // Not on FX application thread; currentThread=JavaFX Application Thread error
                // when using Platform.exit() directly
                Platform.runLater(new Runnable() {
                    /**
                     * override the run function for exit
                     * when the user closes the main window, the subsequent windows will be closed
                     */
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                });
            }
        });
    }
}

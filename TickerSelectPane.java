/**
 * The class constructs the pane of ticker selection.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;

/**
 * TickerSelectPane class
 */
public class TickerSelectPane extends FlowPane {
    /**
     * the constructor function
     */
    public TickerSelectPane() {
        // construct a new label
        Label label = new Label("Select a Ticker: ");
        label.setMinWidth(120);

        // construct a new ChoiceBox
        ChoiceBox<Object> choiceBox = new ChoiceBox<>();
        choiceBox.setMaxWidth(240);

        // add Ticker objects to the TreeSet
        TickerOptions.addOptions();
        // assign the list of tickers to the sortedTickers
        String[] sortedTickers = TickerOptions.iterOptions();
        // set options to ChoiceBox
        choiceBox.setItems(FXCollections.observableArrayList(
                (Object[]) sortedTickers
        ));

        // add components to the pane
        this.getChildren().add(label);
        this.getChildren().add(choiceBox);

        // add tooltip to the ChoiceBox
        Tooltip.install(choiceBox, new Tooltip("Click Here to Select a Ticker from the Dropdown List."));

        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            /**
             * an event listener to listen to changes that the user makes
             * @param observable ObservableValue, the object contains observable data
             * @param oldValue Object, the previous value that the user selected
             * @param newValue Object, the current value that the user selected
             */
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
//                System.out.println("old " + oldValue);
//                System.out.println("new " + newValue);
                UrlData.ticker = newValue.toString();
            }
        });
    }
}

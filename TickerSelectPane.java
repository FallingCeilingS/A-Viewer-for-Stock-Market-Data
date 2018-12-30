import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class TickerSelectPane extends FlowPane {
    public TickerSelectPane() {
        Label label = new Label("Select a Ticker: ");
        ChoiceBox<Object> choiceBox = new ChoiceBox<>();
        TickerOptions.addOptions();
        String[] sortedTickers = TickerOptions.iterOptions();
        choiceBox.setItems(FXCollections.observableArrayList(
                sortedTickers
        ));

        this.getChildren().add(label);
        this.getChildren().add(choiceBox);

        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                System.out.println("old " + oldValue);
                System.out.println("new " + newValue);
            }
        });
    }
}

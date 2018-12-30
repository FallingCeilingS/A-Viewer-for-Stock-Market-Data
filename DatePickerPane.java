import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerPane extends FlowPane {
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private final String pattern = "MM/dd/yyyy";
    private StringConverter stringConverter;

    public DatePickerPane() {
        Label labelStart = new Label("Select Start Date: ");
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        Label labelEnd = new Label("Select End Date: ");

        this.getChildren().add(labelStart);
        this.getChildren().add(startDatePicker);
        this.getChildren().add(labelEnd);
        this.getChildren().add(endDatePicker);

        setStringConverter();
        setDateConverter();
        setPromptText();

        setStartDateCellFactory();
        setEndDateCellFactory();

    }

    private void setStringConverter() {
        stringConverter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateTimeFormatter.format(object);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    private void setDateConverter() {
        startDatePicker.setConverter(stringConverter);
        endDatePicker.setConverter(stringConverter);
    }

    private void setPromptText() {
        startDatePicker.setPromptText(pattern.toLowerCase());
        endDatePicker.setPromptText(pattern.toLowerCase());
    }

    private void setStartDateCellFactory() {
        Callback<DatePicker, DateCell> startDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (endDatePicker.getValue() != null && item.isAfter(endDatePicker.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        startDatePicker.setDayCellFactory(startDayCellFactory);
    }

    private void setEndDateCellFactory() {
        Callback<DatePicker, DateCell> endDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (startDatePicker.getValue() != null && item.isBefore(startDatePicker.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        endDatePicker.setDayCellFactory(endDayCellFactory);
    }
}

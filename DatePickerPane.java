/**
 * This class construct the GUI of Date Picker in the main panel,
 * limit the range between start and end date to ensure the date range is valid,
 * and add tooltip to the component.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DatePickerPane class
 */
public class DatePickerPane extends FlowPane {
    /*
    declare instance variables
     */
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private final String pattern = "MM/dd/yyyy";
    private StringConverter stringConverter;

    /**
     * the constructor function
     */
    public DatePickerPane() {
        /*
        assign object instances to each component
         */
        Label labelStart = new Label("Select Start Date: ");
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        Label labelEnd = new Label("Select End Date: ");

        /*
        set width and height to each component
         */
        labelStart.setMinWidth(120);
        labelStart.setMinHeight(30);
        startDatePicker.setMinWidth(240);
        labelEnd.setMinWidth(120);
        labelEnd.setMinHeight(30);
        endDatePicker.setMinWidth(240);

        /*
        add components to the panel
         */
        this.getChildren().add(labelStart);
        this.getChildren().add(startDatePicker);
        this.getChildren().add(labelEnd);
        this.getChildren().add(endDatePicker);

        /*
        disable the capability of edit of DatePicker
         */
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);

        /*
        set tooltips to components
         */
        setStartDatePickerToolTip();
        setEndDatePickerToolTip();

        /*
        format string converters, parsers and prompt text to component
         */
        setStringConverter();
        setDateConverter();
        setPromptText();

        /*
        limit the range of selection
         */
        setStartDateCellFactory();
        setEndDateCellFactory();
    }

    /*
    define internal methods
     */

    /**
     * set a StringConverter type converter
     * convert string to the date format using specific pattern,
     * and convert date to the string using specific pattern
     */
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

    /**
     * set converter to component
     */
    private void setDateConverter() {
        startDatePicker.setConverter(stringConverter);
        endDatePicker.setConverter(stringConverter);
    }

    /**
     * set prompt text on the DatePicker components
     */
    private void setPromptText() {
        startDatePicker.setPromptText(pattern.toLowerCase());
        endDatePicker.setPromptText(pattern.toLowerCase());
    }

    /**
     * set start date limitation:
     * the start date could not be after the selected end date,
     * and the start date could not be the future date after today
     */
    private void setStartDateCellFactory() {
        Callback<DatePicker, DateCell> startDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        // disable the start date that is after the selected end date
                        if (endDatePicker.getValue() != null && item.isAfter(endDatePicker.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #3498db;");
                        }
                        // disable the start date that is after today
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #3498db;");
                        }
                        // display string on the component
                        if (startDatePicker.getValue() != null) {
                            UrlData.startDate = startDatePicker.getValue().format(DateTimeFormatter.ofPattern(pattern));
                        }
                    }
                };
            }
        };
        startDatePicker.setDayCellFactory(startDayCellFactory);
    }

    /**
     * set end date limitation:
     * the end date could not be before the selected start date,
     * and the end date could not be the future date after today
     */
    private void setEndDateCellFactory() {
        Callback<DatePicker, DateCell> endDayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        // disable the end date that is before the selected start date
                        if (startDatePicker.getValue() != null && item.isBefore(startDatePicker.getValue())) {
                            setDisable(true);
                            // the disable color is blue
                            setStyle("-fx-background-color: #3498db;");
                        }
                        // disable the end date that is after today
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            // the disable color is blue
                            setStyle("-fx-background-color: #3498db;");
                        }
                        // display string on the component
                        if (endDatePicker.getValue() != null) {
                            UrlData.endDate = endDatePicker.getValue().format(DateTimeFormatter.ofPattern(pattern));
                        }
                    }
                };
            }
        };
        endDatePicker.setDayCellFactory(endDayCellFactory);
    }

    /**
     * set tooltip for the StartDatePicker component
     */
    private void setStartDatePickerToolTip() {
        Tooltip.install(startDatePicker, new Tooltip("Click Here to Select a Start Date.\n" +
                "Note: You CANNOT Select a Date AFTER TODAY or the Selected END DATE!"));
    }

    /**
     * set tooltip for the EndDatePicker component
     */
    private void setEndDatePickerToolTip() {
        Tooltip.install(endDatePicker, new Tooltip("Click Here to Select an End Date.\n" +
                "Note: You CANNOT Select a Date AFTER TODAY or BEFORE the Selected START DATE!"));
    }
}

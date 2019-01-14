/**
 * The class constructs the pane of tooltip of the symbols in line chart and area line chart.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ToolTipContent class
 */
class ToolTipContent extends GridPane {
    /*
    declare member variables
     */
    private Label dateValueLabel = new Label();
    private Label yValueLabel = new Label();

    /**
     * the constructor function
     *
     * @param yText String, the text in the left of value
     */
    public ToolTipContent(String yText) {
        /*
        construct labels
         */
        Label date = new Label("DATE:");
        Label yLabel = new Label(yText);
        /*
        set layouts
         */
        setConstraints(date, 0, 0);
        setConstraints(dateValueLabel, 1, 0);
        setConstraints(yLabel, 0, 1);
        setConstraints(yValueLabel, 1, 1);
        // set style of labels
        String labelStyle =
                "-fx-font-size: 0.75em;\n" +
                        "-fx-font-weight: bold;\n" +
                        "-fx-text-fill: #666666;\n" +
                        "-fx-padding: 2 5 2 0;";
        date.setStyle(labelStyle);
        yLabel.setStyle(labelStyle);
        // add components to the pane
        getChildren().addAll(date, dateValueLabel, yLabel, yValueLabel);
    }

    /*
    define instant methods
     */

    /**
     * update values of the pane every time the user's mouse hovers a symbol
     *
     * @param date String, the date
     * @param val  Number, the relative value with regard to the pane
     * @throws ParseException the exception of the error
     */
    public void update(String date, Number val) throws ParseException {
        DateFormat parseDateFormat = new SimpleDateFormat("MM/dd/yy");
        Date dateValue = parseDateFormat.parse(date);
        DateFormat toStringDateFormatter = new SimpleDateFormat("E, MMM dd yyyy");
        String strDate = toStringDateFormatter.format(dateValue);
        dateValueLabel.setText(strDate);
        yValueLabel.setText(val.toString());
    }
}

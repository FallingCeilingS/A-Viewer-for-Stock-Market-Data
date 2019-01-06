import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class ToolTipContent extends GridPane {
    private Label dateValueLabel = new Label();
    private Label yValueLabel = new Label();

    public ToolTipContent(String yText) {
        Label date = new Label("DATE:");
        Label yLabel = new Label(yText);
        setConstraints(date, 0, 0);
        setConstraints(dateValueLabel, 1, 0);
        setConstraints(yLabel, 0, 1);
        setConstraints(yValueLabel, 1, 1);
        String labelStyle =
                "-fx-font-size: 0.75em;\n" +
                "-fx-font-weight: bold;\n" +
                "-fx-text-fill: #666666;\n" +
                "-fx-padding: 2 5 2 0;";
        date.setStyle(labelStyle);
        yLabel.setStyle(labelStyle);
        getChildren().addAll(date, dateValueLabel, yLabel, yValueLabel);
    }

    public void update(String date, Number val) throws ParseException {
        DateFormat parseDateFormat = new SimpleDateFormat("MM/dd/yy");
        Date dateValue = parseDateFormat.parse(date);
        DateFormat toStringDateFormatter = new SimpleDateFormat("E, MMM dd yyyy");
        String strDate = toStringDateFormatter.format(dateValue);
        dateValueLabel.setText(strDate);
        yValueLabel.setText(val.toString());
    }
}

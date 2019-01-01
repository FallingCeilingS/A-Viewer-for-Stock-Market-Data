import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

public class MainChartPane extends StackPane {
    public MainChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");

        XYChart.Series series = new XYChart.Series();
        series.setName("Close Price");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getClose()));
        }
        lineChart.getData().addAll(series);

//        lineChart.setMinWidth(600);
//        lineChart.setMinHeight(400);
//        lineChart.prefWidthProperty().bind(this.widthProperty());
//        lineChart.prefHeightProperty().bind(this.heightProperty());

        this.getChildren().add(lineChart);
    }
}

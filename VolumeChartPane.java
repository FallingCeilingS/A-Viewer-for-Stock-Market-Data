import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class VolumeChartPane extends StackPane {
    public VolumeChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Volume");
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);

        XYChart.Series series = new XYChart.Series();
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume()));
        }
        areaChart.getData().addAll(series);

        areaChart.getYAxis().setTickLabelRotation(270);

        this.getChildren().add(areaChart);
    }
}

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class VolumeChartPane extends StackPane {
    public VolumeChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<String, Number> areaChart = new AreaChart<String, Number>(xAxis, yAxis);
        areaChart.setTitle(title);

        XYChart.Series series = new XYChart.Series();
        areaChart.getData().addAll(series);

        this.getChildren().add(areaChart);
    }
}

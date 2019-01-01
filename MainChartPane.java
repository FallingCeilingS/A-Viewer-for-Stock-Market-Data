import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

import java.util.Set;

public class MainChartPane extends StackPane {
    public MainChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        lineChart.getYAxis().setTickLabelRotation(270);

        XYChart.Series series = new XYChart.Series();
        series.setName("Low");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), (dailyTickerData.getLow())));
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Open");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series1.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getOpen()));
        }

//        lineChart.setStyle(".default-color0.chart-series-line { -fx-stroke: black; }");

        lineChart.getData().add(series);
        lineChart.getData().add(series1);

        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: black");
        }

        Set<Node> lineNodes = lineChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes) {
            node.setStyle("-fx-stroke: blue");
        }
//        lineChart.setMinWidth(600);
//        lineChart.setMinHeight(400);

        lineChart.setCreateSymbols(false);
//        lineChart.setAlternativeRowFillVisible(false);
//        lineChart.setAlternativeColumnFillVisible(false);
//        lineChart.setHorizontalGridLinesVisible(false);
//        lineChart.setVerticalGridLinesVisible(false);
//        lineChart.getXAxis().setVisible(false);
//        lineChart.getYAxis().setVisible(false);
//        lineChart.setOpacity(0);

        this.getChildren().add(lineChart);
//        this.getChildren().add(lineChart);
    }
}

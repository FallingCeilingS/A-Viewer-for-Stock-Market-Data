import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

import java.util.Set;

public class MainChartPane extends StackPane {
    public MainChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);
        yAxis.setLowerBound(CsvData.setLowerBound());
        yAxis.setUpperBound(CsvData.setUpperBound());
        stackedBarChart.getYAxis().setTickLabelRotation(270);

        XYChart.Series lowSeries = new XYChart.Series();
        lowSeries.setName("Low");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            lowSeries.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), (dailyTickerData.getLow())));
        }

        XYChart.Series highSeries = new XYChart.Series();
        highSeries.setName("High");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            highSeries.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), (dailyTickerData.getHigh() - dailyTickerData.getLow())));
        }

        stackedBarChart.getData().add(lowSeries);
        stackedBarChart.getData().add(highSeries);

        Set<Node> lineNodes = stackedBarChart.lookupAll(".default-color0.chart-bar");
        for (Node node : lineNodes) {
            node.setStyle("-fx-bar-fill: blue");
            node.setStyle("visibility: hidden");
        }

        Set<Node> legendNodes = stackedBarChart.lookupAll(".chart-legend");
        for (Node node : legendNodes) {
            node.setStyle("-fx-padding: 6px 300px 6px 6px");
        }
//        lineChart.setMinWidth(600);
//        lineChart.setMinHeight(400);

//        lineChart.setAlternativeRowFillVisible(false);
//        lineChart.setAlternativeColumnFillVisible(false);
//        lineChart.setHorizontalGridLinesVisible(false);
//        lineChart.setVerticalGridLinesVisible(false);
//        lineChart.getXAxis().setVisible(false);
//        lineChart.getYAxis().setVisible(false);
//        lineChart.setOpacity(0);

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
        lineChart.setCreateSymbols(false);

        XYChart.Series openSeries = new XYChart.Series();
        openSeries.setName("Open");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            openSeries.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getOpen()));
        }

        XYChart.Series closeSeries = new XYChart.Series();
        closeSeries.setName("Close");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            closeSeries.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getClose()));
        }

        lineChart.getData().add(openSeries);
        lineChart.getData().add(closeSeries);

        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: transparent");
        }

//        this.getChildren().add(stackedBarChart);
//        this.getChildren().add(lineChart);
        this.getChildren().addAll(stackedBarChart, lineChart);
    }
}

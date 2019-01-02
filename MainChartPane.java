import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

import java.util.Iterator;
import java.util.Set;

public class MainChartPane extends StackPane {
    public MainChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Price");
//        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);
        yAxis.setLowerBound(CsvData.setLowerBound());
        yAxis.setUpperBound(CsvData.setUpperBound());
        stackedBarChart.getYAxis().setTickLabelRotation(270);
        stackedBarChart.setCategoryGap(0);

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

        Set<Node> bar0Nodes = stackedBarChart.lookupAll(".default-color0.chart-bar");
        for (Node node : bar0Nodes) {
            node.setStyle("-fx-bar-fill: white");
            node.setStyle("visibility: hidden");
        }

        Set<Node> bar1Nodes = stackedBarChart.lookupAll(".default-color1.chart-bar");
        Iterator<DailyTickerData> iterator = CsvData.TickerData.iterator();
        for (Node node : bar1Nodes) {
            boolean isIncrease = iterator.next().getIsIncrease();
            if (isIncrease) {
                node.setStyle("-fx-bar-fill: #27ae60");
            } else {
                node.setStyle("-fx-bar-fill: #e74c3c");
            }
        }

        Set<Node> legendNodes1 = stackedBarChart.lookupAll(".chart-legend");
        for (Node node : legendNodes1) {
            node.setStyle("-fx-padding: 6px 300px 6px 6px");
        }
//        lineChart.setMinWidth(600);
//        lineChart.setMinHeight(400);



//        lineChart.getXAxis().setVisible(false);
//        lineChart.getYAxis().setVisible(false);
//        lineChart.setOpacity(0);

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);

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

        Set<Node> line0Nodes = lineChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : line0Nodes) {
            double width = 3 / Math.log10(CsvData.TickerData.size());
            StringBuilder style = new StringBuilder("-fx-stroke-width: " + width + "px;");
            node.setStyle(style.toString() + "-fx-stroke: #bdc3c7");
        }

        Set<Node> line1Nodes = lineChart.lookupAll(".default-color1.chart-series-line");
        for (Node node : line1Nodes) {
            double width = 3 / Math.log10(CsvData.TickerData.size());
            StringBuilder style = new StringBuilder("-fx-stroke-width: " + width + "px;");
            node.setStyle(style.toString() + "-fx-stroke: #7f8c8d");
        }

        this.getChildren().addAll(stackedBarChart, lineChart);
    }
}

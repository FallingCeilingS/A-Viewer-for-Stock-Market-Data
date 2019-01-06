import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
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
        lowSeries.setName("The Stock Rose");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            lowSeries.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), (dailyTickerData.getLow())));
        }

        XYChart.Series highSeries = new XYChart.Series();
        highSeries.setName("The Stock Fell");
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

        Set<Node> barLegendNodes = stackedBarChart.lookupAll(".chart-legend");
        for (Node node : barLegendNodes) {
            node.setStyle("-fx-translate-x: -50px");
        }

        stackedBarChart.setStyle("CHART_COLOR_1: #27ae60 ; CHART_COLOR_2: #e74c3c");

        for (XYChart.Series<String, Number> s : stackedBarChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
//                System.out.println(d.getNode());
//                System.out.println(d.getYValue());
            }
        }
//        lineChart.setMinWidth(600);
//        lineChart.setMinHeight(400);

//        lineChart.setOpacity(0);

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
//        lineChart.setCreateSymbols(false);
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

        Set<Node> lineNodes = lineChart.lookupAll(".chart-series-line");
        for (Node node : lineNodes) {
            double width = 3 / Math.log10(CsvData.TickerData.size());
            StringBuilder style = new StringBuilder("-fx-stroke-width: " + width + "px;");
            node.setStyle(style.toString());
        }

        Set<Node> lineLegendNodes = lineChart.lookupAll(".chart-legend");
        for (Node node : lineLegendNodes) {
            node.setStyle("-fx-translate-x: 150px");
        }

        Set<Node> symbolNodes = lineChart.lookupAll(".chart-line-symbol");
        for (Node node : symbolNodes) {
            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
            StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
            node.setStyle(style.toString());
        }

        lineChart.setStyle("CHART_COLOR_1: #bdc3c7 ; CHART_COLOR_2: #7f8c8d");

        int i = 1;
        for (XYChart.Series<String, Number> s : lineChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                System.out.println(d.getNode());
                System.out.println(d.getYValue());
                Tooltip tooltip = new Tooltip();
                if (i <= 1) {
                    tooltip.setGraphic(new ToolTipContent("OPEN:"));
                    Tooltip.install(d.getNode(), tooltip);
                    ToolTipContent toolTipContent = (ToolTipContent) tooltip.getGraphic();
                    try {
                        toolTipContent.update(d.getXValue(), d.getYValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
                            d.getNode().setStyle("-fx-background-color: #95a5a6;" + style);
                        }
                    });
                    d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
                            d.getNode().setStyle("-fx-background-color: #bdc3c7, white;" + style);
                        }
                    });
                } else {
                    tooltip.setGraphic(new ToolTipContent("CLOSE:"));
                    Tooltip.install(d.getNode(), tooltip);
                    ToolTipContent toolTipContent = (ToolTipContent) tooltip.getGraphic();
                    try {
                        toolTipContent.update(d.getXValue(), d.getYValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
                            d.getNode().setStyle("-fx-background-color: #34495e;" + style);
                        }
                    });
                    d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
                            d.getNode().setStyle("-fx-background-color: #7f8c8d, white;" + style);
                        }
                    });
                }
            }
            i = i + 1;
        }

        this.getChildren().addAll(stackedBarChart, lineChart);
    }
}

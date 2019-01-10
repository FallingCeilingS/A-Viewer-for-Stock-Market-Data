import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Iterator;
import java.util.Set;

public class MainChartPane extends StackPane {
    private String title;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series lowSeries, highSeries, openSeries, closeSeries;
    private StackedBarChart<String, Number> stackedBarChart;
    private LineChart<String, Number> lineChart;

    public MainChartPane(String title) {
        this.title = title;

        setxAxis();
        setyAxis();

        stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        setStackedBarChartAttr();

        setLowSeries();
        setHighSeries();
        stackedBarChart.getData().add(lowSeries);
        stackedBarChart.getData().add(highSeries);

        setBarColor();
        setBarLegend();

        lineChart = new LineChart<>(xAxis, yAxis);
        setLineChartAttr();

        setOpenSeries();
        setCloseSeries();
        lineChart.getData().add(openSeries);
        lineChart.getData().add(closeSeries);

        setLineChartBg();
        setLineChartLineWidth();
        setLineChartSymbol();
        setLineChartLegend();
        setLineChartToolTip();

        this.getChildren().addAll(stackedBarChart, lineChart);
    }

    private void setxAxis() {
        xAxis.setLabel("Date");
    }

    private void setyAxis() {
        yAxis.setLabel("Price (USD)");
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);
        yAxis.setLowerBound(CsvData.setLowerBound());
        yAxis.setUpperBound(CsvData.setUpperBound());
    }

    private void setStackedBarChartAttr() {
        stackedBarChart.setTitle(title);
        stackedBarChart.getYAxis().setTickLabelRotation(270);
        stackedBarChart.setCategoryGap(0);
    }

    private void setLowSeries() {
        lowSeries = new XYChart.Series();
        lowSeries.setName("The Stock Rose  (High - Top, Low - Bottom)");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            lowSeries.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), (dailyTickerData.getLow()))
            );
        }
    }

    private void setHighSeries() {
        highSeries = new XYChart.Series();
        highSeries.setName("The Stock Fell  (High - Top, Low - Bottom)");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            highSeries.getData().add(
                    new XYChart.Data<String, Number>(
                            dailyTickerData.getDateString(), (dailyTickerData.getHigh() - dailyTickerData.getLow())
                    )
            );
        }
    }

    private void setBarColor() {
        Set<Node> bar0Nodes = stackedBarChart.lookupAll(".default-color0.chart-bar");
        for (Node node : bar0Nodes) {
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
    }

    private void setBarLegend() {
        Set<Node> barLegendNodes = stackedBarChart.lookupAll(".chart-legend");
        for (Node node : barLegendNodes) {
            node.setStyle("-fx-translate-x: -80px");
        }
        stackedBarChart.setStyle("CHART_COLOR_1: #27ae60 ; CHART_COLOR_2: #e74c3c");
    }

    private void setLineChartAttr() {
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
    }

    private void setOpenSeries() {
        openSeries = new XYChart.Series();
        openSeries.setName("Open");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            openSeries.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getOpen())
            );
        }
    }

    private void setCloseSeries() {
        closeSeries = new XYChart.Series();
        closeSeries.setName("Close");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            closeSeries.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getClose())
            );
        }
    }

    private void setLineChartBg() {
        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: transparent");
        }
    }

    private void setLineChartLineWidth() {
        Set<Node> lineNodes = lineChart.lookupAll(".chart-series-line");
        for (Node node : lineNodes) {
            double width = 3 / Math.log10(CsvData.TickerData.size());
            String style = "-fx-stroke-width: " + width + "px;";
            node.setStyle(style);
        }
    }

    private void setLineChartSymbol() {
        Set<Node> symbolNodes = lineChart.lookupAll(".chart-line-symbol");
        for (Node node : symbolNodes) {
            double radius = Math.round(5.05 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
            node.setStyle(style);
        }
    }

    private void setLineChartLegend() {
        Set<Node> lineLegendNodes = lineChart.lookupAll(".chart-legend");
        for (Node node : lineLegendNodes) {
            node.setStyle("-fx-translate-x: 300px");
        }
        lineChart.setStyle("CHART_COLOR_1: #bdc3c7 ; CHART_COLOR_2: #7f8c8d");
    }

    private void setLineChartToolTip() {
        int i = 1;
        for (XYChart.Series<String, Number> s : lineChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip tooltip = new Tooltip();
                if (i <= 1) {
                    tooltip.setGraphic(new ToolTipContent("OPEN:"));

                    d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #95a5a6;" + style);
                        }
                    });
                    d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #bdc3c7, white;" + style);
                        }
                    });
                } else {
                    tooltip.setGraphic(new ToolTipContent("CLOSE:"));

                    d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #34495e;" + style);
                        }
                    });
                    d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #7f8c8d, white;" + style);
                        }
                    });
                }

                Tooltip.install(d.getNode(), tooltip);
                ToolTipContent toolTipContent = (ToolTipContent) tooltip.getGraphic();
                try {
                    toolTipContent.update(d.getXValue(), d.getYValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i = i + 1;
        }
    }
}

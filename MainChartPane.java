/**
 * This class construct the pane of displaying price data graph,
 * setting styles of the graph.
 *
 * @Author: Junxiang Chen
 * @RegistrationNumber: 180127586
 * @Email: jchen115@sheffield.ac.uk
 */

/*
import dependencies
 */

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Iterator;
import java.util.Set;

/**
 * MainChartPane class
 */
public class MainChartPane extends StackPane {
    /*
    declare member variables
     */
    private String title;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series lowSeries, highSeries, openSeries, closeSeries;
    private StackedBarChart<String, Number> stackedBarChart;
    private LineChart<String, Number> lineChart;

    /**
     * the constructor function
     *
     * @param title Sting, the title of the chart
     */
    public MainChartPane(String title) {
        /*
        assign title to the member variable
         */
        this.title = title;

        /*
        set axes in each coordinates
         */
        setxAxis();
        setyAxis();

        // construct a stacked bar chart to draw red and green rectangles to indicate low and high price data
        stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        // set attribution of stacked bar chart
        setStackedBarChartAttr();

        /*
        set and add series of low and high data to the stacked bar chart
         */
        setLowSeries();
        setHighSeries();
        stackedBarChart.getData().add(lowSeries);
        stackedBarChart.getData().add(highSeries);

        /*
        set color and legend of stacked bar chart
         */
        setBarColor();
        setBarLegend();

        // construct a line chart to draw light grey and dark grey lines to indicate opening and closing price data
        lineChart = new LineChart<>(xAxis, yAxis);
        // set attribution of line chart
        setLineChartAttr();

        /*
        set and add series of opening and closing data to the line chart
        note that the background of the line chart is transparent
         */
        setOpenSeries();
        setCloseSeries();
        lineChart.getData().add(openSeries);
        lineChart.getData().add(closeSeries);

        /*
        set styles of line chart
         */
        setLineChartBg();
        setLineChartLineWidth();
        setLineChartSymbol();
        setLineChartLegend();
        setLineChartToolTip();

        // add two charts to the pane
        this.getChildren().addAll(stackedBarChart, lineChart);
    }

    /*
    define member methods
     */

    /**
     * set x axis
     */
    private void setxAxis() {
        xAxis.setLabel("Date");
    }

    /**
     * set y axis
     */
    private void setyAxis() {
        yAxis.setLabel("Price (USD)");
        yAxis.setAutoRanging(false);
        yAxis.setForceZeroInRange(false);
        yAxis.setLowerBound(CsvData.setLowerBound());
        yAxis.setUpperBound(CsvData.setUpperBound());
    }

    /**
     * set attribution of stacked bar chart,
     * including title, rotation of tick in the y axis and bar gaps
     */
    private void setStackedBarChartAttr() {
        stackedBarChart.setTitle(title);
        stackedBarChart.getYAxis().setTickLabelRotation(270);
        stackedBarChart.setCategoryGap(0);
    }

    /**
     * set series of low price
     */
    private void setLowSeries() {
        lowSeries = new XYChart.Series();
        lowSeries.setName("The Stock Rose  (High - Top, Low - Bottom)");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            lowSeries.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), (dailyTickerData.getLow()))
            );
        }
    }

    /**
     * set series of high price
     */
    private void setHighSeries() {
        highSeries = new XYChart.Series();
        highSeries.setName("The Stock Fell  (High - Top, Low - Bottom)");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            highSeries.getData().add(
                    new XYChart.Data<String, Number>(
                            // the value of the second bar is the value of high minus the value of low
                            // to satisfy the stacked bar
                            dailyTickerData.getDateString(), (dailyTickerData.getHigh() - dailyTickerData.getLow())
                    )
            );
        }
    }

    /**
     * set color of bar
     */
    private void setBarColor() {
        // hide the first bar
        Set<Node> bar0Nodes = stackedBarChart.lookupAll(".default-color0.chart-bar");
        for (Node node : bar0Nodes) {
            node.setStyle("visibility: hidden");
        }

        // set different colors (red or green) to each bar,
        // based on whether the stock price of the day increased (green) or decreased (red)
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

    /**
     * set the location and color of legend
     */
    private void setBarLegend() {
        Set<Node> barLegendNodes = stackedBarChart.lookupAll(".chart-legend");
        for (Node node : barLegendNodes) {
            node.setStyle("-fx-translate-x: -80px");
        }
        stackedBarChart.setStyle("CHART_COLOR_1: #27ae60 ; CHART_COLOR_2: #e74c3c");
    }

    /**
     * set attribution of line chart,
     * including title, rotation of ticks in the y axis, visibility of row and column fill
     * and visibility of horizontal and vertical grid lines
     */
    private void setLineChartAttr() {
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
    }

    /**
     * set series of opening price
     */
    private void setOpenSeries() {
        openSeries = new XYChart.Series();
        openSeries.setName("Open");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            openSeries.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getOpen())
            );
        }
    }

    /**
     * set series of closing price
     */
    private void setCloseSeries() {
        closeSeries = new XYChart.Series();
        closeSeries.setName("Close");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            closeSeries.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getClose())
            );
        }
    }

    /**
     * set background to transparent so that we can see the stacked bar chart behind the line chart
     */
    private void setLineChartBg() {
        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: transparent");
        }
    }

    /**
     * set the line width to the line chart
     * the width can change with regard to the amount of data
     */
    private void setLineChartLineWidth() {
        Set<Node> lineNodes = lineChart.lookupAll(".chart-series-line");
        for (Node node : lineNodes) {
            double width = 3 / Math.log10(CsvData.TickerData.size());
            String style = "-fx-stroke-width: " + width + "px;";
            node.setStyle(style);
        }
    }

    /**
     * set color and radius of line chart symbols
     * the radius of symbols can change with regard to the amount of data
     */
    private void setLineChartSymbol() {
        Set<Node> symbolNodes = lineChart.lookupAll(".chart-line-symbol");
        for (Node node : symbolNodes) {
            double radius = Math.round(5.05 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
            node.setStyle(style);
        }
    }

    /**
     * set location and color of legend of line chart
     */
    private void setLineChartLegend() {
        Set<Node> lineLegendNodes = lineChart.lookupAll(".chart-legend");
        for (Node node : lineLegendNodes) {
            node.setStyle("-fx-translate-x: 300px");
        }
        lineChart.setStyle("CHART_COLOR_1: #bdc3c7 ; CHART_COLOR_2: #7f8c8d");
    }

    /**
     * set tooltip to line chart
     * when the mouse hovers the symbol, the tooltip will pop up to show the actual data with regard to the point
     */
    private void setLineChartToolTip() {
        int i = 1;
        for (XYChart.Series<String, Number> s : lineChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip tooltip = new Tooltip();
                // set first half part of series with the tooltip of open
                if (i <= 1) {
                    tooltip.setGraphic(new ToolTipContent("OPEN:"));

                    d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        /**
                         * set the style of symbol to change when the mouse hovers the point
                         * @param event MouseEvent, the mouse event of hover
                         */
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #95a5a6;" + style);
                        }
                    });
                    d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        /**
                         * set the style of symbol to change when the mouse leaves the point
                         * @param event MouseEvent, the mouse event of leave
                         */
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #bdc3c7, white;" + style);
                        }
                    });
                } else {
                    // set last half part of series with the tooltip of close
                    tooltip.setGraphic(new ToolTipContent("CLOSE:"));

                    d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                        /**
                         * set the style of symbol to change when the mouse hovers the point
                         * @param event MouseEvent, the mouse event
                         */
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #34495e;" + style);
                        }
                    });
                    d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                        /**
                         * set the style of symbol to change when the mouse leaves the point
                         * @param event MouseEvent, the mouse event
                         */
                        @Override
                        public void handle(MouseEvent event) {
                            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                            d.getNode().setStyle("-fx-background-color: #7f8c8d, white;" + style);
                        }
                    });
                }

                // register the tooltip
                Tooltip.install(d.getNode(), tooltip);
                // set the tooltip content
                ToolTipContent toolTipContent = (ToolTipContent) tooltip.getGraphic();
                // update the data in the tooltip content
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

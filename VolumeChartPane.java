/**
 * The class construct a pane containing the graph of volume data.
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

import java.util.Set;

/**
 * VolumeChartPane class
 */
public class VolumeChartPane extends StackPane {
    /*
    declare member variables
     */
    private String title;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series series1, series2;
    private AreaChart<String, Number> areaChart;
    private LineChart<String, Number> lineChart;

    /**
     * the constructor function
     * @param title String, the title of the pane
     */
    public VolumeChartPane(String title) {
        this.title = title;

        /*
        set axes
         */
        setxAxis();
        setyAxis();

        // construct an area chart
        areaChart = new AreaChart<>(xAxis, yAxis);
        // set attributes of the area chart
        setAreaChartAttr();

        // set series and add it to the chart
        setSeries1();
        areaChart.getData().addAll(series1);

        /*
        set styles of area chart
         */
        setAreaChartFill();
        setAreaChartLine();
        setAreaChartLegend();

        // construct a line chart
        lineChart = new LineChart<>(xAxis, yAxis);
        // set attributes of line chart
        setLineChartAttr();

        // set series and add it to the line chart
        setSeries2();
        lineChart.getData().addAll(series2);

        /*
        set styles of line chart
         */
        setLineChartLine();
        setLineChartBg();
        setLineChartSymbol();
        setLineChartLegend();
        // add tooltip to the line chart
        setLineChartToolTip();

        // add components to the pane
        this.getChildren().addAll(areaChart, lineChart);
    }

    /**
     * set x axis
     */
    private void setxAxis() {
        xAxis.setLabel("Date");
        xAxis.setAutoRanging(true);
    }

    /**
     * set y axis
     */
    private void setyAxis() {
        yAxis.setLabel("Volume");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
    }

    /**
     * set area chart attribution,
     * including title, symbols and rotation of tick in the y axis
     */
    private void setAreaChartAttr() {
        areaChart.setTitle(title);
        areaChart.setCreateSymbols(false);
        areaChart.getYAxis().setTickLabelRotation(270);
    }

    /**
     * set series for the area chart
     */
    private void setSeries1() {
        series1 = new XYChart.Series();
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series1.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume())
            );
        }
    }

    /**
     * set fill color of the area chart
     */
    private void setAreaChartFill() {
        Set<Node> areaNodes = areaChart.lookupAll(".default-color0.chart-series-area-fill");
        for (Node node : areaNodes) {
            node.setStyle("-fx-fill: #f1c40f");
        }
    }

    /**
     * set the line width and color to the area chart
     */
    private void setAreaChartLine() {
        Set<Node> lineNodes1 = areaChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes1) {
            node.setStyle("-fx-stroke-width: 0; -fx-stroke: transparent");
        }
    }

    /**
     * set legend to the area chart
     */
    private void setAreaChartLegend() {
        areaChart.setStyle("CHART_COLOR_1: #f39c12");
    }

    /**
     * set line chart attribution,
     * including title, rotation of tick in the y axis and visibility of horizontal and vertical grid lines
     */
    private void setLineChartAttr() {
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
    }

    /**
     * set series for the line chart
     */
    private void setSeries2() {
        series2 = new XYChart.Series();
        series2.setName("Volume");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series2.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume())
            );
        }
    }

    /**
     * set the color and the width of the line chart
     * the width can change with regard to the amount of data
     */
    private void setLineChartLine() {
        Set<Node> lineNodes2 = lineChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes2) {
            double width = Math.round(3 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-stroke-width: " + width + "px;";
            node.setStyle(style + "-fx-stroke: #f39c12");
        }
    }

    /**
     * set the background color of the line chart to transparent
     * so that we can see the area chart behind the line chart
     */
    private void setLineChartBg() {
        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: transparent");
        }
    }

    /**
     * set the color and radius of line chart symbols
     * the radius of symbols can change with regard to the amount of data
     */
    private void setLineChartSymbol() {
        Set<Node> symbolNodes = lineChart.lookupAll(".default-color0.series0.chart-line-symbol");
        for (Node node : symbolNodes) {
            double radius = Math.round(5.15 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
            node.setStyle(style);
        }
    }

    /**
     * set the color of legend of the line chart
     */
    private void setLineChartLegend() {
        lineChart.setStyle("CHART_COLOR_1: #e67e22");
    }

    /**
     * set the tooltip for the line chart
     * when the mouse hovers the symbol, the tooltip will pop up to show the actual data with regard to the point
     */
    private void setLineChartToolTip() {
        for (XYChart.Series<String, Number> s : lineChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setGraphic(new ToolTipContent("VOLUME:"));
                Tooltip.install(d.getNode(), tooltip);
                ToolTipContent toolTipContent = (ToolTipContent) tooltip.getGraphic();
                try {
                    toolTipContent.update(d.getXValue(), d.getYValue());
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
                d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    /**
                     * set the style of symbol to change when the mouse hovers the point
                     * @param event MouseEvent, the mouse event of hover
                     */
                    @Override
                    public void handle(MouseEvent event) {
                        double radius = Math.round(5.15 / Math.log10(CsvData.TickerData.size()));
                        String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                        d.getNode().setStyle("-fx-background-color: #d35400;" + style);
                    }
                });
                d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                    /**
                     * set the style of symbol to change when the mouse leaves the point
                     * @param event MouseEvent the mouse event of leave
                     */
                    @Override
                    public void handle(MouseEvent event) {
                        double radius = Math.round(5.15 / Math.log10(CsvData.TickerData.size()));
                        String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                        d.getNode().setStyle("-fx-background-color: #e67e22, white;" + style);
                    }
                });
            }
        }
    }
}

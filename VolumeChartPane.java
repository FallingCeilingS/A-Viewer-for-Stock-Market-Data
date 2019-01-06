import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Set;

public class VolumeChartPane extends StackPane {
    private String title;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series series1, series2;
    private AreaChart<String, Number> areaChart;
    private LineChart<String, Number> lineChart;

    public VolumeChartPane(String title) {
        this.title = title;

        setxAxis();
        setyAxis();

        areaChart = new AreaChart<>(xAxis, yAxis);
        setAreaChartAttr();

        setSeries1();
        areaChart.getData().addAll(series1);

        setAreaChartFill();
        setAreaChartLine();
        setAreaChartLegend();

        lineChart = new LineChart<>(xAxis, yAxis);
        setLineChartAttr();

        setSeries2();
        lineChart.getData().addAll(series2);

        setLineChartLine();
        setLineChartBg();
        setLineChartSymbol();
        setLineChartLegend();
        setLineChartToolTip();

        this.getChildren().addAll(areaChart, lineChart);
    }

    private void setxAxis() {
        xAxis.setLabel("Date");
        xAxis.setAutoRanging(true);
    }

    private void setyAxis() {
        yAxis.setLabel("Volume");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
    }

    private void setAreaChartAttr() {
        areaChart.setTitle(title);
        areaChart.setCreateSymbols(false);
        areaChart.getYAxis().setTickLabelRotation(270);
    }

    private void setSeries1() {
        series1 = new XYChart.Series();
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series1.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume())
            );
        }
    }

    private void setAreaChartFill() {
        Set<Node> areaNodes = areaChart.lookupAll(".default-color0.chart-series-area-fill");
        for (Node node : areaNodes) {
            node.setStyle("-fx-fill: #f1c40f");
        }
    }

    private void setAreaChartLine() {
        Set<Node> lineNodes1 = areaChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes1) {
            node.setStyle("-fx-stroke-width: 0; -fx-stroke: transparent");
        }
    }

    private void setAreaChartLegend() {
        areaChart.setStyle("CHART_COLOR_1: #f39c12");
    }

    private void setLineChartAttr() {
        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
    }

    private void setSeries2() {
        series2 = new XYChart.Series();
        series2.setName("Volume");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series2.getData().add(
                    new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume())
            );
        }
    }

    private void setLineChartLine() {
        Set<Node> lineNodes2 = lineChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes2) {
            double width = Math.round(3 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-stroke-width: " + width + "px;";
            node.setStyle(style + "-fx-stroke: #f39c12");
        }
    }

    private void setLineChartBg() {
        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: transparent");
        }
    }

    private void setLineChartSymbol() {
        Set<Node> symbolNodes = lineChart.lookupAll(".default-color0.series0.chart-line-symbol");
        for (Node node : symbolNodes) {
            double radius = Math.round(5.15 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
            node.setStyle(style);
        }
    }

    private void setLineChartLegend() {
        lineChart.setStyle("CHART_COLOR_1: #e67e22");
    }

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
                    @Override
                    public void handle(MouseEvent event) {
                        double radius = Math.round(5.15 / Math.log10(CsvData.TickerData.size()));
                        String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
                        d.getNode().setStyle("-fx-background-color: #d35400;" + style);
                    }
                });
                d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
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

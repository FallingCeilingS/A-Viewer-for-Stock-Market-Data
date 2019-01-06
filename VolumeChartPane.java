import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Set;

public class VolumeChartPane extends StackPane {
    private XYChart.Series series1, series2;

    public VolumeChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle(title);
        areaChart.setCreateSymbols(false);
        xAxis.setLabel("Date");
        yAxis.setLabel("Volume");
        areaChart.getYAxis().setTickLabelRotation(270);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);

        series1 = new XYChart.Series();
//        series1.setName("Volume");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series1.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume()));
        }
        areaChart.getData().addAll(series1);

        Set<Node> areaNodes = areaChart.lookupAll(".default-color0.chart-series-area-fill");
        for (Node node : areaNodes) {
            node.setStyle("-fx-fill: #f1c40f");
        }

        Set<Node> lineNodes1 = areaChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes1) {
            node.setStyle("-fx-stroke-width: 0; -fx-stroke: transparent");
        }

        areaChart.setStyle("CHART_COLOR_1: #f39c12");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle(title);
        lineChart.getYAxis().setTickLabelRotation(270);
//        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);

        series2 = new XYChart.Series();
        series2.setName("Volume");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series2.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume()));
        }
        lineChart.getData().addAll(series2);

        Set<Node> lineNodes2 = lineChart.lookupAll(".default-color0.chart-series-line");
        for (Node node : lineNodes2) {
            double width = Math.round(3 / Math.log10(CsvData.TickerData.size()));
            StringBuilder style = new StringBuilder("-fx-stroke-width: " + width + "px;");
            node.setStyle(style + "-fx-stroke: #f39c12");
        }

        Set<Node> bgNodes = lineChart.lookupAll(".chart-plot-background");
        for (Node node : bgNodes) {
            node.setStyle("-fx-background-color: transparent");
        }

        Set<Node> symbolNodes = lineChart.lookupAll(".default-color0.series0.chart-line-symbol");
        for (Node node : symbolNodes) {
            double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
            String style = "-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px";
            node.setStyle(style);
        }

        lineChart.setStyle("CHART_COLOR_1: #e67e22");

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
                        double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                        StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
                        d.getNode().setStyle("-fx-background-color: #d35400;" + style);
                    }
                });
                d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        double radius = Math.round(5 / Math.log10(CsvData.TickerData.size()));
                        StringBuilder style = new StringBuilder("-fx-background-radius: " + radius + "px; -fx-padding: " + radius + "px");
                        d.getNode().setStyle("-fx-background-color: #e67e22, white;" + style);
                    }
                });
            }
        }

        this.getChildren().addAll(areaChart, lineChart);
    }
}

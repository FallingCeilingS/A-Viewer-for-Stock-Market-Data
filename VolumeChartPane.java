import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Set;

public class VolumeChartPane extends StackPane {
    private XYChart.Series series;

    public VolumeChartPane(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle(title);
//        areaChart.setCreateSymbols(false);
        xAxis.setLabel("Date");
        yAxis.setLabel("Volume");
        areaChart.getYAxis().setTickLabelRotation(270);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);

        series = new XYChart.Series();
        series.setName("Volume");
        for (DailyTickerData dailyTickerData : CsvData.TickerData) {
            series.getData().add(new XYChart.Data<String, Number>(dailyTickerData.getDateString(), dailyTickerData.getVolume()));
        }
        areaChart.getData().addAll(series);

        Set<Node> lineNodes = areaChart.lookupAll(".default-color0.chart-series-area-line");
        for (Node node : lineNodes) {
            double width = Math.round(3 / Math.log10(CsvData.TickerData.size()));
            StringBuilder style = new StringBuilder("-fx-stroke-width: " + width + "px;");
            node.setStyle(style + "-fx-stroke: #f39c12");
        }

        Set<Node> areaNodes = areaChart.lookupAll(".default-color0.chart-series-area-fill");
        for (Node node : areaNodes) {
            node.setStyle("-fx-fill: #f1c40f");
        }

        Set<Node> symbolNodes = areaChart.lookupAll(".default-color0.chart-series-area-symbol");
        for (Node node : symbolNodes) {
//            System.out.println(node.toString());
            node.setStyle("-fx-background-color: white");
            node.setStyle("-fx-stroke: black");
        }

        areaChart.setStyle("CHART_COLOR_1: #f39c12");

        for (XYChart.Series<String, Number> s : areaChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                System.out.println(d.toString());
                System.out.println(d.getNode());

                Tooltip.install(d.getNode(), new Tooltip(
                        d.getXValue().toString() + "\n" + d.getYValue()
                ));
                d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        d.getNode().getStyleClass().add("onHover");
                    }
                });
            }
        }

        Set<Node> onHoverNodes = areaChart.lookupAll(".onHover");
        for (Node node : onHoverNodes) {
            node.setStyle("-fx-background-color: orange");
        }

        this.getChildren().add(areaChart);
    }
}

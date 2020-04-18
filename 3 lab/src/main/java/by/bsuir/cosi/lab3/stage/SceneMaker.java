package by.bsuir.cosi.lab3.stage;

import by.bsuir.cosi.lab3.auxiliary.SeriesGenerator;
import by.bsuir.cosi.lab3.entity.GeneralFunction;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Map;

public class SceneMaker {

    private SceneMaker(){
    }

    public static GridPane getMainGridPane(NumberAxis xAxis, NumberAxis yAxis, GeneralFunction generalFunction){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series mainSeries= SeriesGenerator.mainFunction(generalFunction,"y=cos(3x)+sin(2x)");

        lineChart.getData().add(mainSeries);
        lineChart.setStyle("CHART_COLOR_1: #00ff21 ;");
        return setGridPaneUsualSettings(lineChart);
    }


    public static GridPane getGridPaneFromMap(NumberAxis xAxis, NumberAxis yAxis, Map<Integer, Double> map, String color, String name){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series mainSeries=SeriesGenerator.seriesFromMapWithOutNulling(map, name);

        lineChart.getData().add(mainSeries);
        lineChart.setStyle(color);
        return setGridPaneUsualSettings(lineChart);
    }


    private static GridPane setGridPaneUsualSettings(LineChart<Number, Number> lineChart) {
        lineChart.setCreateSymbols(false);

        GridPane gridPane=new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.add(lineChart, 0, 0);

        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().add(rowConstraints);

        return gridPane;
    }
}

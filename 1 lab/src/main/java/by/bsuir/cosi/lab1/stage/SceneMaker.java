package by.bsuir.cosi.lab1.stage;

import by.bsuir.cosi.lab1.entity.GeneralFunction;
import by.bsuir.cosi.lab1.logic.FunctionGenerator;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SceneMaker {

    private static int xSize=1800;
    private static int ySize=1000;

    private SceneMaker(){
    }

    public static GridPane getMainGridPane(NumberAxis xAxis, NumberAxis yAxis, GeneralFunction generalFunction){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series mainSeries= FunctionGenerator.mainFunction(generalFunction,"y=cos(3x)+sin(2x)");

        lineChart.getData().add(mainSeries);
        lineChart.setStyle("CHART_COLOR_1: #00ff21 ;");
        lineChart.setCreateSymbols(false);

        GridPane gridPane=new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.add(lineChart, 0, 0);

        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().add(rowConstraints);

        return gridPane;
    }

    public static GridPane getMainGridPane1(NumberAxis xAxis, NumberAxis yAxis, GeneralFunction generalFunction){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series mainSeries= FunctionGenerator.mainFunction(generalFunction,"y=cos(3x)+sin(2x)");

        lineChart.getData().add(mainSeries);
        lineChart.setStyle("CHART_COLOR_1: #ff0021 ;");
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

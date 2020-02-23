package by.bsuir.cosi.lab1.stage;

import by.bsuir.cosi.lab1.entity.GeneralFunction;
import by.bsuir.cosi.lab1.auxiliary.SeriesGenerator;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Map;

public class SceneMaker {

    private static int xSize=1800;
    private static int ySize=1000;

    private SceneMaker(){
    }

    public static GridPane getMainGridPane(NumberAxis xAxis, NumberAxis yAxis, GeneralFunction generalFunction){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series mainSeries= SeriesGenerator.mainFunction(generalFunction,"y=cos(3x)+sin(2x)");

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

    public static GridPane getDPF(NumberAxis xAxis, NumberAxis yAxis, int N){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series realDPFPart= SeriesGenerator.realDPFPart(N,"ReXm(m)");
        XYChart.Series imgDPFPart=SeriesGenerator.imgDPFPart(N, "ImXm(m)");

        lineChart.getData().add(realDPFPart);
        lineChart.getData().add(imgDPFPart);
        lineChart.setStyle("CHART_COLOR_1: #ff64e9 ;");
        lineChart.setStyle("CHART_COLOR_2: #2ff7ff ;");
        lineChart.setCreateSymbols(false);

        GridPane gridPane=new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.add(lineChart, 0, 0);

        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().add(rowConstraints);

        return gridPane;
    }

    public static GridPane getAmplitudes(NumberAxis xAxis, NumberAxis yAxis, Map<Integer, Double> amplitudeMap){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series amplitudeSeries= SeriesGenerator.seriesFromMap(amplitudeMap,"Amplitudes");

        lineChart.getData().add(amplitudeSeries);
        lineChart.setStyle("CHART_COLOR_1: #c821ff ;");
        lineChart.setCreateSymbols(false);

        GridPane gridPane=new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.add(lineChart, 0, 0);

        RowConstraints rowConstraints=new RowConstraints();
        rowConstraints.setPercentHeight(100);
        gridPane.getRowConstraints().add(rowConstraints);

        return gridPane;
    }

    public static GridPane getPhases(NumberAxis xAxis, NumberAxis yAxis, Map<Integer, Double> phaseMap){
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        XYChart.Series phaseSeries= SeriesGenerator.seriesFromMap(phaseMap,"Phases");

        lineChart.getData().add(phaseSeries);
        lineChart.setStyle("CHART_COLOR_1: #ff9800 ;");
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

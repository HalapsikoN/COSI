package by.bsuir.cosi.lab1.auxiliary;

import by.bsuir.cosi.lab1.entity.Complex;
import by.bsuir.cosi.lab1.entity.GeneralFunction;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class SeriesGenerator {

    private SeriesGenerator() {
    }

    public static XYChart.Series mainFunction(GeneralFunction function, String name) {
        XYChart.Series series = new XYChart.Series();

        series.setName(name);
        ObservableList listOfData = series.getData();
        for (double i = function.getMinX(); i < function.getMaxX(); i += function.getStep()) {
            listOfData.add(new XYChart.Data(i, FunctionGenerator.getValueOfMainFunction(i)));
        }

        return series;
    }

    public static XYChart.Series realDPFPart(int N, String name) {
        XYChart.Series series = new XYChart.Series();

        series.setName(name);
        ObservableList listOfData = series.getData();
        for (int i = 0; i <= N; i ++) {
            double realPart=FunctionGenerator.getDPFComponent(i, N).getReal();
            listOfData.add(new XYChart.Data(i, realPart));
        }

        return series;
    }

    public static XYChart.Series imgDPFPart(int N, String name) {
        XYChart.Series series = new XYChart.Series();

        series.setName(name);
        ObservableList listOfData = series.getData();
        for (int i = 0; i <= N; i ++) {
            double realPart=FunctionGenerator.getDPFComponent(i, N).getImaginary();
            listOfData.add(new XYChart.Data(i, realPart));
        }

        return series;
    }

    public static XYChart.Series seriesFromMap(Map<Integer, Double> map, String name){
        XYChart.Series series = new XYChart.Series();

        series.setName(name);
        ObservableList listOfData = series.getData();
        for (Integer i : map.keySet()) {
            listOfData.add(new XYChart.Data(i, map.get(i)));
        }

        correctSeries(series, map, 0.001);

        return series;
    }

    private static void correctSeries(XYChart.Series series, Map<Integer, Double> map, double shift){
        ObservableList listOfData=series.getData();
        for (Integer i:map.keySet()){
            listOfData.add(new XYChart.Data(i-shift, 0));
            listOfData.add(new XYChart.Data(i+shift, 0));
        }
    }

}

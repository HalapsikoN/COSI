package by.bsuir.cosi.lab1.logic;

import by.bsuir.cosi.lab1.entity.GeneralFunction;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class FunctionGenerator {

    private FunctionGenerator(){
    }

    public static double getValueOfMainFunction(double x){
        return (Math.cos(3*x)+Math.sin(2*x));
    }

    public static XYChart.Series mainFunction(GeneralFunction function, String name){
        XYChart.Series series = new XYChart.Series();

        series.setName(name);
        ObservableList listOfData = series.getData();
        for(double i=function.getMinX(); i<function.getMaxX(); i+=function.getStep()){
            listOfData.add(new XYChart.Data(i, getValueOfMainFunction(i)));
        }

        return series;
    }

}

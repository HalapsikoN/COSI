package by.bsuir.cosi.lab1.entity;

import javafx.scene.chart.NumberAxis;

public class AxisGenerator {
    
    private static final AxisGenerator instance=new AxisGenerator();
    
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    
    private AxisGenerator(){
        xAxis=new NumberAxis();
        yAxis=new NumberAxis();

        xAxis.setLabel("x");
        yAxis.setLabel("y");

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-15);
        xAxis.setUpperBound(15);
        xAxis.setTickUnit(0.5);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-3);
        yAxis.setUpperBound(3);
        yAxis.setTickUnit(0.2);
    }

    public static AxisGenerator getInstance() {
        return instance;
    }
    
    public NumberAxis getXAxis(){
        return getNumberAxis(xAxis);
    }

    public NumberAxis getYAxis(){
        return getNumberAxis(yAxis);
    }

    private NumberAxis getNumberAxis(NumberAxis modelAxis) {
        NumberAxis numberAxis=new NumberAxis();

        numberAxis.setLabel(modelAxis.getLabel());
        numberAxis.setAutoRanging(modelAxis.isAutoRanging());
        numberAxis.setLowerBound(modelAxis.getLowerBound());
        numberAxis.setUpperBound(modelAxis.getUpperBound());
        numberAxis.setTickUnit(modelAxis.getTickUnit());

        return numberAxis;
    }
}

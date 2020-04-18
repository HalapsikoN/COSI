package by.bsuir.cosi.lab3.auxiliary;

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
        xAxis.setLowerBound(0);
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

    public NumberAxis getCustomXAxis(double min, double max, String name){
        return getCustomNumberAxis(min, max, name, xAxis);
    }

    private NumberAxis getCustomNumberAxis(double min, double max, String name, NumberAxis xAxis) {
        NumberAxis numberAxis=new NumberAxis();

        numberAxis.setLabel((name.isEmpty())?xAxis.getLabel():name);
        numberAxis.setAutoRanging(xAxis.isAutoRanging());
        numberAxis.setLowerBound(min);
        numberAxis.setUpperBound(max);
        numberAxis.setTickUnit(xAxis.getTickUnit());

        return numberAxis;
    }

    public NumberAxis getCustomYAxis(double min, double max, String name){
        return getCustomNumberAxis(min, max, name, yAxis);
    }
}

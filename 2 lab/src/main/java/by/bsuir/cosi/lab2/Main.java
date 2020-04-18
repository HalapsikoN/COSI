package by.bsuir.cosi.lab2;

import by.bsuir.cosi.lab2.auxiliary.AxisGenerator;
import by.bsuir.cosi.lab2.auxiliary.FunctionGenerator;
import by.bsuir.cosi.lab2.entity.Complex;
import by.bsuir.cosi.lab2.entity.GeneralFunction;
import by.bsuir.cosi.lab2.entity.logic.ComplexLogic;
import by.bsuir.cosi.lab2.stage.SceneMaker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.*;

import javafx.application.Application;

public class Main extends Application {

    private  int xSize=1800;
    private  int ySize=1000;
    private int currentScene=0;
    private int NFFT=4;
    private int N=8;

    private Map<GridPane, Scene> SceneBuffer =new HashMap<GridPane, Scene>();

    public void start(Stage stage) throws Exception {
        stage.setTitle("COSI 2");

        System.out.println("start");

        AxisGenerator axisGenerator=AxisGenerator.getInstance();

        GeneralFunction generalFunction=new GeneralFunction(axisGenerator.getXAxis().getLowerBound(), axisGenerator.getXAxis().getUpperBound(), 0.01);

        List<GridPane> gridPaneList=new LinkedList<GridPane>();

        //usual
        gridPaneList.add(SceneMaker.getMainGridCosPane(axisGenerator.getXAxis(), axisGenerator.getYAxis(), generalFunction));
        gridPaneList.add(SceneMaker.getMainGridSinPane(axisGenerator.getXAxis(), axisGenerator.getYAxis(), generalFunction));

        //convertion
        Map<Integer, Double> convolution= FunctionGenerator.getConvolution(N);
        gridPaneList.add(SceneMaker.getGridPaneFromMap(axisGenerator.getCustomXAxis(0, N-1, "x"), axisGenerator.getCustomYAxis(-1, 1, "y"), convolution, "CHART_COLOR_1: #ff9800 ;", "Convolution"));


        //cos
        List<Complex> complexListCos=new ArrayList<>();
        for (int i=0; i<N; ++i){
            complexListCos.add(new Complex(FunctionGenerator.getValueOfMainCosFunction(i)));
        }
        List<Complex> resultFFTCos=FunctionGenerator.doBPF(complexListCos, N, -1);
//        for(Complex complex:resultFFTCos){
//            complex.setReal(complex.getReal()/N);
//            complex.setImaginary(complex.getImaginary()/N);
//        }
        System.out.println(resultFFTCos);

        //sin
        List<Complex> complexListSin=new ArrayList<>();
        for (int i=0; i<N; ++i){
            complexListSin.add(new Complex(FunctionGenerator.getValueOfMainSinFunction(i)));
        }
        List<Complex> resultFFTSin=FunctionGenerator.doBPF(complexListSin, N, -1);
//        for(Complex complex:resultFFTSin){
//            complex.setReal(complex.getReal()/N);
//            complex.setImaginary(complex.getImaginary()/N);
//        }
        System.out.println(resultFFTSin);

        //cos obr
//        List<Complex>  complexConjugateCos=new ArrayList<>();
//        for (int i=0; i<N; ++i){
//            complexConjugateCos.add(ComplexLogic.getComplexConjugate(resultFFTCos.get(i)));
//        }
//        System.out.println(complexConjugateCos);

        //a*b
        List<Complex> resultOfMulti=new ArrayList<>();
        for (int i=0; i<N; ++i){
            resultOfMulti.add(ComplexLogic.multiplicationOfTwoComplex(resultFFTCos.get(i), resultFFTSin.get(i)));
        }
        System.out.println(resultOfMulti);

        //bpf obr
        List<Complex> res=FunctionGenerator.doBPF(resultOfMulti, N, 1);
        System.out.println(res);

        Map<Integer, Double> backFunctionFFT=new HashMap<>();
        for (int i=0; i<N; ++i){
            backFunctionFFT.put(i, res.get(i).getReal()/N/N);
        }

        gridPaneList.add(SceneMaker.getGridPaneFromMap(axisGenerator.getCustomXAxis(0, N-1, "x"), axisGenerator.getCustomYAxis(-1, 1, "y"), backFunctionFFT, "CHART_COLOR_1: #c821ff ;", "Convolution with FFT"));


        //convertion
        Map<Integer, Double> correlation= FunctionGenerator.getCorrelation(N);
        gridPaneList.add(SceneMaker.getGridPaneFromMap(axisGenerator.getCustomXAxis(0, N-1, "x"), axisGenerator.getCustomYAxis(-1, 1, "y"), correlation, "CHART_COLOR_1: #009898 ;", "Correlation"));

        //cos obr
        List<Complex>  complexConjugateCos=new ArrayList<>();
        for (int i=0; i<N; ++i){
            complexConjugateCos.add(ComplexLogic.getComplexConjugate(resultFFTCos.get(i)));
        }
        System.out.println(complexConjugateCos);

        //a*b
        List<Complex> resultOfMulti2=new ArrayList<>();
        for (int i=0; i<N; ++i){
            resultOfMulti2.add(ComplexLogic.multiplicationOfTwoComplex(complexConjugateCos.get(i), resultFFTSin.get(i)));
        }
        System.out.println(resultOfMulti2);

        //bpf obr
        List<Complex> res2=FunctionGenerator.doBPF(resultOfMulti2, N, 1);
        System.out.println(res);

        Map<Integer, Double> backFunctionFFT2=new HashMap<>();
        for (int i=0; i<N; ++i){
            backFunctionFFT2.put(i, res2.get(i).getReal()/N/N);
        }

        gridPaneList.add(SceneMaker.getGridPaneFromMap(axisGenerator.getCustomXAxis(0, N-1, "x"), axisGenerator.getCustomYAxis(-1, 1, "y"), backFunctionFFT2, "CHART_COLOR_1: #ff64e9 ;", "сorrelation with FFT"));


        makeScene(stage, gridPaneList);
        System.out.println("finish");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void makeScene(final Stage stage, final List<GridPane> gridPaneList){

        GridPane gridPane=gridPaneList.get(currentScene);

        if(!SceneBuffer.containsKey(gridPane)){
            TilePane tilePane=new TilePane();
            Button buttonNext=new Button("Next");
            buttonNext.setPrefWidth(80);
            buttonNext.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    currentScene++;
                    if(currentScene>=gridPaneList.size()) {
                        currentScene=0;
                    }
                    makeScene(stage, gridPaneList);
                }
            });

            Button buttonPrevious=new Button("Previous");
            buttonPrevious.setPrefWidth(80);
            buttonPrevious.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    currentScene--;
                    if(currentScene<0){
                        currentScene=gridPaneList.size()-1;
                    }
                    makeScene(stage, gridPaneList);
                }
            });
            tilePane.getChildren().addAll(buttonPrevious, buttonNext);
            tilePane.setAlignment(Pos.CENTER);

            gridPane.add(tilePane, 0, 1);

            ColumnConstraints columnConstraints=new ColumnConstraints();
            columnConstraints.setPercentWidth(100);
            columnConstraints.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints=new RowConstraints();
            rowConstraints.setPercentHeight(5);
            gridPane.getRowConstraints().add(rowConstraints);
            SceneBuffer.put(gridPane, new Scene(gridPane, xSize, ySize));
        }

        stage.setScene(SceneBuffer.get(gridPane));
    }

}

package by.bsuir.cosi.lab1;

import by.bsuir.cosi.lab1.auxiliary.AxisGenerator;
import by.bsuir.cosi.lab1.auxiliary.FunctionGenerator;
import by.bsuir.cosi.lab1.entity.Complex;
import by.bsuir.cosi.lab1.entity.GeneralFunction;
import by.bsuir.cosi.lab1.stage.SceneMaker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class Fourier extends Application {

    private  int xSize=1800;
    private  int ySize=1000;
    private int currentScene=0;
    private int NFFT=8;
    private int N=8;

    private Map<GridPane, Scene> SceneBuffer =new HashMap<GridPane, Scene>();

    public void start(Stage stage) throws Exception {
        stage.setTitle("COSI 1");

        System.out.println("start");

        AxisGenerator axisGenerator=AxisGenerator.getInstance();

        GeneralFunction generalFunction=new GeneralFunction(axisGenerator.getXAxis().getLowerBound(), axisGenerator.getXAxis().getUpperBound(), 0.01);

        List<GridPane> gridPaneList=new LinkedList<GridPane>();

        gridPaneList.add(SceneMaker.getMainGridPane(axisGenerator.getXAxis(), axisGenerator.getYAxis(), generalFunction));

        Map<Integer, Complex> dpfMap= FunctionGenerator.getDPFMap(N);
        gridPaneList.add(SceneMaker.getDPF(axisGenerator.getCustomXAxis(0, N, "m"), axisGenerator.getCustomYAxis(-0.4, 0.8, "X(m)"), N));

        Map<Integer, Double> amplitudeMap=FunctionGenerator.getAmplitudeMap(dpfMap);
        gridPaneList.add(SceneMaker.getAmplitudes(axisGenerator.getCustomXAxis(0, N, "m"), axisGenerator.getCustomYAxis(-0.2, 0.8, ""), amplitudeMap));

        Map<Integer, Double> phaseMap=FunctionGenerator.getPhaseMap(dpfMap);
        gridPaneList.add(SceneMaker.getPhases(axisGenerator.getCustomXAxis(0, N, "m"), axisGenerator.getCustomYAxis(-0.2, 0.2, ""), phaseMap));

        Map<Integer, Complex> returnDPFMap=FunctionGenerator.getReturnDPFMap(N, dpfMap);
        Map<Integer, Double> backFunction=new HashMap<>();
        for (Integer i: returnDPFMap.keySet()){
            backFunction.put(i, returnDPFMap.get(i).getReal());
        }

        gridPaneList.add(SceneMaker.getBackFunction(axisGenerator.getCustomXAxis(0, N, "m"), axisGenerator.getCustomYAxis(-0.5, 0.5, ""), backFunction));

        List<Complex> complexList=new ArrayList<>();

        for(int i=0; i<NFFT; ++i){
            complexList.add(new Complex(FunctionGenerator.getValueOfMainFunction(i)));
        }

        List<Complex> resultFFT=FunctionGenerator.doBPF(complexList, NFFT, -1);

        Map<Integer, Complex> resultFFTMap=new HashMap<>();

        for(int i=0; i<NFFT; ++i){
            resultFFTMap.put(i, new Complex(resultFFT.get(i).getReal()/NFFT, resultFFT.get(i).getImaginary()/NFFT));
        }

        Map<Integer, Double> amplitudeMapFFT=FunctionGenerator.getAmplitudeMap(resultFFTMap);
        gridPaneList.add(SceneMaker.getAmplitudes(axisGenerator.getCustomXAxis(0, NFFT, "m"), axisGenerator.getCustomYAxis(-0.2, 0.8, ""), amplitudeMapFFT));

        Map<Integer, Double> phaseMapFFT=FunctionGenerator.getPhaseMap(resultFFTMap);
        gridPaneList.add(SceneMaker.getPhases(axisGenerator.getCustomXAxis(0, NFFT, "m"), axisGenerator.getCustomYAxis(-0.2, 0.2, ""), phaseMapFFT));

        List<Complex> returnFFT=FunctionGenerator.doBPF(resultFFT, NFFT, 1);

        Map<Integer, Double> backFunctionFFT=new HashMap<>();
        for (int i=0; i<NFFT; ++i){
            backFunctionFFT.put(i, returnFFT.get(i).getReal()/NFFT/NFFT);
        }

        gridPaneList.add(SceneMaker.getBackFunction(axisGenerator.getCustomXAxis(0, N, "m"), axisGenerator.getCustomYAxis(-0.5, 0.5, ""), backFunctionFFT));


        System.out.println(dpfMap);
        System.out.println("\n"+resultFFTMap);

        makeScene(stage, gridPaneList);
        System.out.println("finish");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void makeScene(final Stage stage,final List<GridPane> gridPaneList){

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

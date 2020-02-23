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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Fourier extends Application {

    private  int xSize=1800;
    private  int ySize=1000;
    private int currentScene=0;
    private int m=16;
    private int N=200;

    private Map<GridPane, Scene> SceneBuffer =new HashMap<GridPane, Scene>();

    public void start(Stage stage) throws Exception {
        stage.setTitle("COSI 1");

        System.out.println("start");

        AxisGenerator axisGenerator=AxisGenerator.getInstance();

        GeneralFunction generalFunction=new GeneralFunction(axisGenerator.getXAxis().getLowerBound(), axisGenerator.getXAxis().getUpperBound(), 0.01);

        List<GridPane> gridPaneList=new LinkedList<GridPane>();

        gridPaneList.add(SceneMaker.getMainGridPane(axisGenerator.getXAxis(), axisGenerator.getYAxis(), generalFunction));

        Map<Integer, Complex> dpfMap= FunctionGenerator.getDPFMap(N);
        gridPaneList.add(SceneMaker.getDPF(axisGenerator.getCustomXAxis(0, N/2, "m"), axisGenerator.getCustomYAxis(-0.4, 0.4, "X(m)"), N));

        Map<Integer, Double> amplitudeMap=FunctionGenerator.getAmplitudeMap(dpfMap);
        gridPaneList.add(SceneMaker.getAmplitudes(axisGenerator.getCustomXAxis(0, N/2, "m"), axisGenerator.getCustomYAxis(-0.2, 0.5, ""), amplitudeMap));

        Map<Integer, Double> phaseMap=FunctionGenerator.getPhaseMap(dpfMap);
        gridPaneList.add(SceneMaker.getPhases(axisGenerator.getCustomXAxis(0, N/2, "m"), axisGenerator.getCustomYAxis(-78, 78, ""), phaseMap));

        Map<Integer, Complex> returnDPFMap=FunctionGenerator.getReturnDPFMap(N, dpfMap);
        System.out.println(returnDPFMap);

        System.out.println(phaseMap);

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

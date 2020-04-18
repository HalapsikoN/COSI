package by.bsuir.cosi.lab3;

import by.bsuir.cosi.lab3.auxiliary.AxisGenerator;
import by.bsuir.cosi.lab3.auxiliary.FunctionGenerator;
import by.bsuir.cosi.lab3.entity.GeneralFunction;
import by.bsuir.cosi.lab3.stage.SceneMaker;
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

public class Main extends Application {

    private  int xSize=1800;
    private  int ySize=1000;
    private int currentScene=0;
    private int N=16;

    private Map<GridPane, Scene> SceneBuffer =new HashMap<GridPane, Scene>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("COSI 3");
        System.out.println("start");

        AxisGenerator axisGenerator=AxisGenerator.getInstance();
        GeneralFunction generalFunction=new GeneralFunction(axisGenerator.getXAxis().getLowerBound(), axisGenerator.getXAxis().getUpperBound(), 0.01);
        List<GridPane> gridPaneList=new LinkedList<GridPane>();

        gridPaneList.add(SceneMaker.getMainGridPane(axisGenerator.getXAxis(), axisGenerator.getYAxis(), generalFunction));

        List<Double> valuesOfFunction=new ArrayList<>();
        for(int i=0; i<N; ++i){
            valuesOfFunction.add(FunctionGenerator.getValueOfMainFunction(i));
        }
        System.out.println("Function input values:");
        System.out.println(valuesOfFunction);

        List<Double> resultBPU=FunctionGenerator.doFWC(valuesOfFunction, N);
        System.out.println();
        System.out.println("Unsorted BPU:");
        System.out.println(resultBPU);

        Map<Integer, Double> sortedBPU=new HashMap<>();
        for (int i=0; i<N; ++i){
            sortedBPU.put(i, resultBPU.get(FunctionGenerator.getMirrorNumber(i, N))/N);
        }
        System.out.println();
        System.out.println("BPU values:");
        System.out.println(sortedBPU);

        gridPaneList.add(SceneMaker.getGridPaneFromMap(axisGenerator.getCustomXAxis(0, 15, "x"), axisGenerator.getCustomYAxis(-3,3,"y"), sortedBPU, "CHART_COLOR_1: #ff9800 ;", "fast Walsh conversion"));

        List<Double> listOfMapValues=new ArrayList<>();
        listOfMapValues.addAll(sortedBPU.values());

        List<Double> returnBPUValues=FunctionGenerator.doFWCReturn(listOfMapValues, N);
        for (int i=0; i<N; ++i){
            returnBPUValues.set(i, returnBPUValues.get(i));
        }
        Collections.reverse(returnBPUValues);
        System.out.println();
        System.out.println("BPU return values:");
        System.out.println(returnBPUValues);

        Map<Integer, Double> sortedReturnBPU=new HashMap<>();
        for (int i=0; i<N; ++i){
            sortedReturnBPU.put(i, returnBPUValues.get(i));
        }

        gridPaneList.add(SceneMaker.getGridPaneFromMap(axisGenerator.getCustomXAxis(0, 15, "x"), axisGenerator.getCustomYAxis(-3,3,"y"), sortedReturnBPU, "CHART_COLOR_1: #c821ff ;", "fast Walsh conversion reverse"));

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

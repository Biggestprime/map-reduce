
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class Editor extends Application {

    private TextArea mapperArea;
    private TextArea reducerArea;
    private TextArea numOfSplitsArea;
    private TextArea filePathArea;

    @Override
    public void start(Stage primaryStage) {

        Label mapperLabel = new Label("Mapper");
        Label reducerLabel = new Label("Reducer");
        Label numOfSplits = new Label("Number of splits");
        Label inputFilePath = new Label("Input file path");

        mapperArea = new TextArea();
        reducerArea = new TextArea();
        numOfSplitsArea = new TextArea();
        filePathArea = new TextArea();


        VBox mp = new VBox();
        mp.getChildren().addAll(mapperLabel, mapperArea);

        VBox rp = new VBox();
        rp.getChildren().addAll(reducerLabel, reducerArea);

        VBox v3 = new VBox();
        v3.getChildren().addAll(numOfSplits, numOfSplitsArea);

        VBox v4 = new VBox();
        v4.getChildren().addAll(inputFilePath, filePathArea);


        Button runBt = new Button("run");
        runBt.setOnAction(new SubmissionHandler());

        GridPane root = new GridPane();
        root.setPrefSize(primaryStage.getMaxWidth(),
                              primaryStage.getMaxHeight());
        root.setPadding(new Insets(10, 10, 10, 10));

        root.addColumn(0, mp);
        root.addColumn(1, rp);
        root.addRow(1, v3, v4);
        root.addRow(2, runBt);
        root.setHgap(10);
        root.setVgap(10);

        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("Java Editor");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private class SubmissionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            boolean isValid = !mapperArea.getText().trim().equals("") &&
                    !reducerArea.getText().trim().equals("") &&
                    !numOfSplitsArea.getText().trim().equals("") &&
                    !filePathArea.getText().trim().equals("");

            if (isValid) {

                String mapString = mapperArea.getText();
                String reduceString = reducerArea.getText();
                String runCode = "public class Run {\n" +
                        "   public static void main(String[] args) {\n" +
                        "\n" +
                        "         MapReduce mapReduce = " +
                        "new MapReduce(new GUIMapper(), new GUIReducer());\n"+
                        "mapReduce.setNumOfSplits("+ numOfSplitsArea.getText().trim()+");\n"+
                        "mapReduce.initializeSplitter (\""+filePathArea.getText().trim()+"\");\n"+
                        "        mapReduce.start();\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "}";

                try {
                    writeCodeToFile(mapString, new File("src/GUIMapper.java"));
                    writeCodeToFile(reduceString, new File("src/GUIReducer.java"));
                    writeCodeToFile(runCode, new File("src/Run.java"));

                    String[] args = new String[]
                            {"GUIMapper", "GUIReducer", "Run"};

                    for (int i = 0; i < 3; i++) {
                        Process process = new
                                ProcessBuilder(new String[]{"javac", args[i] + ".java"})
                                .directory(new File("/home/biggestprime/IdeaProjects/MapReduce/src"))
                                .start();
                        System.out.println(process.waitFor());
                    }

                    Process process = new
                            ProcessBuilder(new String[]{"java", "Run"})
                            .directory(new File(
                                    "/home/biggestprime/IdeaProjects/MapReduce/src"))
                            .start();
                    System.out.println(process.waitFor());


                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void writeCodeToFile(String code, File file) throws FileNotFoundException {
            PrintWriter out = new PrintWriter(file);
            out.print(code);
            out.close();
        }
    }
}

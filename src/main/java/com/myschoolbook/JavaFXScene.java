package com.myschoolbook;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaFXScene extends Application {

    private TextArea textArea1 = new TextArea();
    private TextField textField1 = new TextField();
    private Label label1 = new Label();
    private Label label2 = new Label();
    PdfMerger pdfMerger = new PdfMerger();

    @Override // Override the start method in the Application class
        public void start(Stage primaryStage) {
            GridPane pane = new GridPane();
            pane.setAlignment(Pos.CENTER);
            pane.setPadding(new Insets(11, 11, 11, 11));
            pane.setHgap(5.5);
            pane.setVgap(5.5);

            // Place nodes in the pane
            pane.add(new Label("Localization of PDF files " + System.lineSeparator() + "(each path in new line)"), 0, 0);
            textArea1.setMaxSize(400,100);
            textArea1.setMinSize(400,100);
            pane.add(textArea1, 1, 0);

            pane.add(new Label("Output path for file"), 0, 1);
            textField1.setMaxWidth(400);
            pane.add(textField1, 1, 1);

            pane.add(new Label("Succes?"), 0, 2);
            pane.add(label1, 1, 2);

            pane.add(new Label("Error Message"), 0, 3);
            pane.add(label2, 1, 3);

            Button btAdd = new Button("MERGE");
            pane.add(btAdd, 1, 4);
            GridPane.setHalignment(btAdd, HPos.CENTER);
            MergeHandlerClass mergeHandlerClass = new MergeHandlerClass();
            btAdd.setMinSize(100,50);
            btAdd.setOnAction(mergeHandlerClass);

            pane.autosize();
            Scene scene = new Scene(pane, 550, 300);
            primaryStage.setTitle("PDF Merger"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.setResizable(false);
            primaryStage.show(); // Display the stage
        }

        public static void main(String[] args) {
            Application.launch(args);
        }

        public class MergeHandlerClass implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent e) {
                File file1;
                boolean exist;
                String errorMessage = "";
                label1.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
                try {
                    File mergedFile = new File(textField1.getText());
                    exist = mergedFile.exists();
                    if (!textArea1.getText().equals("")) {
                        if (exist) {
                            label1.setText("NO");
                            label1.setTextFill(Color.RED);
                            errorMessage = "File with this path already exists!";
                            label2.setText(errorMessage);
                        } else {
                            label1.setText("YES");
                            label1.setTextFill(Color.GREEN);
                            List<String> fileList = new ArrayList<>();
                            for (String file : textArea1.getText().split("\\r?\\n")) {
                                file1 = new File(file);
                                if(file1.exists()) {
                                    fileList.add(file);
                                }else {
                                    label1.setText("NO");
                                    label1.setTextFill(Color.RED);
                                    errorMessage = "File " + file + " doesn't esists";
                                    label2.setText(errorMessage);
                                    return;
                                }
                            }
                            pdfMerger.combine(fileList, textField1.getText());
                            errorMessage = "NONE";
                            label2.setText(errorMessage);
                        }
                    } else {
                        label1.setText("NO");
                        label1.setTextFill(Color.RED);
                        errorMessage = "No text in text area!";
                        label2.setText(errorMessage);
                    }
                } catch (Exception e1){
                    label1.setText("NO");
                    label1.setTextFill(Color.RED);
                    errorMessage = e1.toString();
                    label2.setText(errorMessage);
                }
            }
        }
}


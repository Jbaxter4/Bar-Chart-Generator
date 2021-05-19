/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BarChartGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author James
 */
public class BarChartGenerator extends Application {

    int numEnabled;

    @Override
    public void start(Stage primaryStage) {

        //Column 0
        Label title = new Label("Title:");
        Label xLabel = new Label("X-Axis Label:");
        Label yLabel = new Label("Y-Axis Label:");
        Button btn1 = new Button();
        Button btn2 = new Button();
        btn1.setText("Generate Bar Chart");
        btn2.setText("Generate From CSV");
        Text t = new Text();
        t.setText("");
        //Column 1
        TextField tBox = new TextField();
        tBox.setPromptText("Enter Title");
        TextField xBox = new TextField();
        xBox.setPromptText("Description of Items");
        TextField yBox = new TextField();
        yBox.setPromptText("Description of Item Value");
        //Column 2
        Label iName = new Label("Item Name");
        TextField iBox[] = new TextField[10];
        for (int i = 0; i < 10; i++) {
            iBox[i] = new TextField();
            iBox[i].setPromptText("Item name");
            iBox[i].setDisable(true);
        }
        //Column 3
        Label vName = new Label("Value");
        TextField vBox[] = new TextField[10];
        for (int i = 0; i < 10; i++) {
            vBox[i] = new TextField();
            vBox[i].setPromptText("e.g. 50");
            vBox[i].setDisable(true);
        }
        //Column 4
        Label eName = new Label("Enabled");
        CheckBox eBox[] = new CheckBox[10];
        for (int i = 0; i < 10; i++) {
            eBox[i] = new CheckBox();
        }

        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setHgap(10);

        //Column 0
        VBox firstColumn = new VBox(20, title, xLabel, yLabel, btn1, btn2, t);
        root.add(firstColumn, 0, 0);
        //Column 1
        VBox secondColumn = new VBox(10, tBox, xBox, yBox);
        root.add(secondColumn, 1, 0);
        //Column 2
        VBox itemColumn = new VBox(10, iName, iBox[0], iBox[1], iBox[2],
                iBox[3], iBox[4], iBox[5], iBox[6], iBox[7], iBox[8], iBox[9]);
        root.add(itemColumn, 2, 0);
        itemColumn.setAlignment(Pos.TOP_CENTER);
        //Column 3
        VBox valueColumn = new VBox(10, vName, vBox[0], vBox[1], vBox[2],
                vBox[3], vBox[4], vBox[5], vBox[6], vBox[7], vBox[8], vBox[9]);
        root.add(valueColumn, 3, 0);
        valueColumn.setAlignment(Pos.TOP_CENTER);
        //Column 4
        VBox enabledColumn = new VBox(17, eName, eBox[0], eBox[1], eBox[2],
                eBox[3], eBox[4], eBox[5], eBox[6], eBox[7], eBox[8], eBox[9]);
        root.add(enabledColumn, 4, 0);
        enabledColumn.setAlignment(Pos.TOP_CENTER);

        EventHandler<ActionEvent> handler;
        eBox[0].setOnAction(handler = (ActionEvent event) -> {
            numEnabled = 0;
            for (int i = 0; i < eBox.length; i++) {
                boolean isEnabled = eBox[i].isSelected();
                if (isEnabled) {
                    iBox[i].setDisable(false);
                    vBox[i].setDisable(false);
                    numEnabled++;
                } else {
                    iBox[i].setDisable(true);
                    vBox[i].setDisable(true);
                }
            }
        });
        eBox[1].setOnAction(handler);
        eBox[2].setOnAction(handler);
        eBox[3].setOnAction(handler);
        eBox[4].setOnAction(handler);
        eBox[5].setOnAction(handler);
        eBox[6].setOnAction(handler);
        eBox[7].setOnAction(handler);
        eBox[8].setOnAction(handler);
        eBox[9].setOnAction(handler);

        btn1.setOnAction((ActionEvent event) -> {
            String bcTitle = tBox.getText();
            String xA = xBox.getText();
            String yA = yBox.getText();
            int emptyError = 0;
            for (int i = 0; i < 10; i++) {
                if ((iBox[i].getText().isEmpty() || vBox[i].getText().isEmpty())
                        && eBox[i].isSelected()) {
                    emptyError++;
                }
            }
            if (bcTitle.trim().isEmpty() || xA.trim().isEmpty()|| 
                    yA.trim().isEmpty() || numEnabled == 0 || emptyError > 0) {
                t.setText("Error:\n- Missing Information");
            } else {
                t.setText("");
                Stage secondStage = new Stage();
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel(xA);
                yAxis.setLabel(yA);
                BarChart bc = new BarChart(xAxis, yAxis);
                bc.setTitle(bcTitle);
                XYChart.Series data = new XYChart.Series();
                data.setName(bcTitle);
                for (int a = 0; a < 10; a++) {
                    if (eBox[a].isSelected()) {
                        String itemName = iBox[a].getText();
                        String itemV = vBox[a].getText();
                        String iV = itemV.replaceAll("[^\\d.]", "");
                        if (iV.isEmpty()) {
                            t.setText("Error:\n- Input not valid");
                        }
                        else {
                            double itemValue = Double.parseDouble(vBox[a].getText());
                            data.getData().add(new XYChart.Data(itemName,
                                itemValue));
                        }
                    }
                }
                bc.getData().add(data);
                VBox barChart = new VBox(bc);
                Scene bcScene = new Scene(barChart, 500, 400);
                secondStage.setTitle(bcTitle);
                secondStage.setScene(bcScene);
                secondStage.setResizable(false);
                secondStage.show();
            }
        });

        btn2.setOnAction((ActionEvent event) -> {
            Stage thirdStage = new Stage();
            FileChooser fc = new FileChooser();
            fc.setTitle("Open .csv file");
            fc.getExtensionFilters().add(
                    new ExtensionFilter("CSV Files", "*.csv"));
            File file = fc.showOpenDialog(thirdStage);
            if (file != null) {
                try (Scanner sc = new Scanner(file)) {
                    String label = sc.nextLine();
                    String[] labels = label.split(",");
                    ArrayList values = new ArrayList();
                    while (sc.hasNext()) {
                        values.add(sc.next());
                    }
                    t.setText("");
                    CategoryAxis xAxis = new CategoryAxis();
                    NumberAxis yAxis = new NumberAxis();
                    if (labels.length < 3 || labels[0].isEmpty()
                            || labels[1].isEmpty() || labels[2].isEmpty()) {
                        t.setText("Error:\n- File structure is not correct"
                                + "\n- Could not aquire one\n   or more of the"
                                + " following:\n \u2022 Title\n \u2022 x-Axis"
                                + " Label\n \u2022 y-Axis Label");
                    } else {
                        xAxis.setLabel(labels[2]);
                        yAxis.setLabel(labels[1]);
                        BarChart bc = new BarChart(xAxis, yAxis);
                        bc.setTitle(labels[0]);
                        XYChart.Series data = new XYChart.Series();
                        data.setName(labels[0]);
                        for (int i = 0; i < values.size(); i++) {
                            String itemN = values.get(i).toString();
                            String itemName = itemN.replaceAll("\\P{L}", "");
                            String itemV = values.get(i).toString();
                            String iV = itemV.replaceAll("[^\\d.]", "");
                            if (itemName.isEmpty() || iV.isEmpty()) {
                                t.setText("Error:\n- File structure is not"
                                        + " correct\n- Data may not be"
                                        + " correctly displayed");
                            } else {
                                double itemValue = Double.parseDouble(iV);
                                data.getData().add(new XYChart.Data(itemName,
                                        itemValue));
                            }
                        }
                        bc.getData().add(data);
                        VBox barChart = new VBox(bc);
                        Scene bcScene = new Scene(barChart, 500, 400);
                        thirdStage.setTitle(labels[0]);
                        thirdStage.setScene(bcScene);
                        thirdStage.setResizable(false);
                        thirdStage.show();
                    }

                } catch (FileNotFoundException ex) {
                    t.setText("Error:\n- File structure is not correct");
                    Logger.getLogger(BarChartGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Scene scene = new Scene(root, 700, 400);

        primaryStage.setTitle("Bar Chart Generator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

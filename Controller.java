package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class Controller implements Initializable {
    @FXML TableView<Result> tableClauses;
    @FXML ComboBox<Double> comboWp;
    @FXML ComboBox<Integer> comboPopulation;
    @FXML
    ComboBox<Integer> comboMaxIteration;
    @FXML
    ComboBox<Integer> comboInstance;
    @FXML
    TableColumn<File, Integer> tableInstance;
    @FXML
    TableColumn<Result, Double> ColRate;
    @FXML
    TableColumn<Result, Double> ColTime;
    @FXML ComboBox<String> comboType;
    ObservableList<String> listeType = FXCollections.observableArrayList("instance satisfiable par temps", "instancesaisfiable par gain");

    ObservableList<Double> listeWp = FXCollections.observableArrayList(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0);
    //? extends Number
    public ObservableList<Result> listeClauses = FXCollections.observableArrayList();
    ObservableList<Integer> listePopulation = FXCollections.observableArrayList(10, 20, 30, 40, 100, 200, 400, 600, 800, 1000);
    ObservableList<Integer> listemaxIteration = FXCollections.observableArrayList(10, 20, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);
    ObservableList<Integer> listeInstance = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    ObservableList<Result> listeOneClause = FXCollections.observableArrayList();

    @FXML
    LineChart<String, Number> lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboWp.setItems(listeWp);
        comboInstance.setItems(listeInstance);
        comboPopulation.setItems(listePopulation);
        comboMaxIteration.setItems(listemaxIteration);
        tableClauses.setItems(listeClauses);
        ColRate.setCellValueFactory(new PropertyValueFactory<>("averageGain"));
        ColTime.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        tableInstance.setCellValueFactory(new PropertyValueFactory<>("instance"));

comboType.setItems(listeType);
    }

    @FXML
    void handleInstance(Event event) throws Exception{


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("instance.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        ControllerInstance controllerInstance=fxmlLoader.getController();
        controllerInstance.myFunction(tableClauses.getSelectionModel().getSelectedItem().getInstance(),listeOneClause);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root1));
        stage.show();
    }
    public void ButtonAction(ActionEvent actionEvent) throws Exception {
        FileChooser fc = new FileChooser();
        String filename = "";
        fc.setInitialDirectory(new File("C:\\Users\\hammo\\Downloads\\ecommerce\\groupe5"));
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            if (comboWp.getSelectionModel().getSelectedItem() != null &&
                    comboMaxIteration.getSelectionModel().getSelectedItem() != null
                    && comboPopulation.getSelectionModel().getSelectedItem() != null) {
                for (int j = 0; j <= comboInstance.getSelectionModel().getSelectedIndex(); j++) {
                    String name = selectedFiles.get(j).getAbsolutePath();
                    FormuleWDP instance = new FormuleWDP(name);
                    double sumGain = 0;
                    double sumTime = 0;
                    int numberOfAttempts = 10;
                    GA genetic = new GA(instance);
                    for (int i = 1; i <= numberOfAttempts; i++) {
                        long currentTime = System.currentTimeMillis();
                        SolutionWDP s = genetic.memeticAlgo(instance, 10, 10, 0.3);
                        long exeTime = System.currentTimeMillis() - currentTime;
//System.out.println(s.getBids().get(0));
                        listeOneClause.add(new Result(s.getGain(), exeTime / 1000, selectedFiles.get(j).getName()));
                        sumGain += s.getGain();
                        sumTime += exeTime;

                    }


                    sumGain /= numberOfAttempts;
                    sumTime /= numberOfAttempts;
                    filename = selectedFiles.get(j).getName();
                    double t = ((double) sumTime / (1000));
                    sumGain=sumGain-55471;
                    listeClauses.add(new Result(sumGain, t, filename));

                    System.out.println("sumGain" + sumGain + " sumTime " + t + " instance" + selectedFiles.get(j).getName());
                    listeOneClause.removeAll();


                }


            }
            tableClauses.setItems(listeClauses);
        }
    }

    public void tracerLeGraphe(ActionEvent actionEvent) throws Exception {


        XYChart.Series<String, Number> series = new XYChart.Series<>();
       // if (comboType != null) {
            if (comboType.getSelectionModel().getSelectedIndex() == 0) {
                lineChart.getData().clear();
                lineChart.setVisible(true);
                if (tableClauses.getItems().size() > 0) {
                    for (int i = 0; i <= tableClauses.getItems().size(); i++) {
                        series.getData().add(new XYChart.Data<>(tableClauses.getItems().get(i).getInstance(), tableClauses.getItems().get(i).getAverageTime()));
                        lineChart.getData().add(series);
                    }

                }
         //   }
        }
        //if (comboType != null) {
            if (comboType.getSelectionModel().getSelectedIndex() == 1  /*taux satisfabilite par instance*/) {
                lineChart.getData().clear();
                lineChart.setVisible(true);
                if (tableClauses.getItems().size() > 0) { //instance par clauses bar chart
                    for (int i = 0; i <= tableClauses.getItems().size(); i++) {
                        series.getData().add(new XYChart.Data<>(tableClauses.getItems().get(i).getInstance(), tableClauses.getItems().get(i).getAverageGain()));
                        lineChart.getData().add(series);
                    }
          //      }




            }
        }

    }


}

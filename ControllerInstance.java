package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;



public class ControllerInstance  implements Initializable  {

    @FXML
    TableView<Result> tableOneClause;
    @FXML
    Text textFile;
   @FXML
   Text textClause;
   @FXML
   TableColumn<Result, Double> colRates;

    @FXML
    TableColumn<Result, Double> colTimes;
    @FXML TableColumn<File, Integer> tableInstances;
      ObservableList<Result> listeOneClausess = FXCollections.observableArrayList();
    @FXML Text textBest ;
    public void myFunction(String text,ObservableList<Result> listeOneClauses ) {
        textFile.setText(text);
        Double sumSat=0.0;
        int max=0;
        for (int i = 0; i < listeOneClauses.size(); i++) {
            if (listeOneClauses.get(i).getInstance().equals(text)) {
                listeOneClausess.add(listeOneClauses.get(i));
                sumSat=sumSat+listeOneClauses.get(i).getAverageGain();
            if(max<(int)listeOneClauses.get(i).getAverageGain()) max=(int)listeOneClauses.get(i).getAverageGain();
            }

        }Double res=sumSat/listeOneClausess.size();
        textClause.setText(""+res) ;
        textBest.setText(""+max);
        tableOneClause.setItems(listeOneClausess);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
System.out.println("hello instance");

        colRates.setCellValueFactory(new PropertyValueFactory<>("averageGain"));
        colTimes.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        tableInstances.setCellValueFactory(new PropertyValueFactory<>("instance"));
        tableOneClause.setItems(listeOneClausess);

    }



                }




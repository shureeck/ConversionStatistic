package gui;

import actions.Reload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import report.Table;

import java.util.ArrayList;


public class Controller {
    private ObservableList<Table> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Table> reportTable;
    @FXML
    private TableColumn<Table, String> category;

    @FXML
    private TableColumn<Table, String> tableColumn;

    @FXML
    private Button analyze;

    private int i =0;

    @FXML
    public void onClickMethod(){
        Reload reload= new Reload();
        ArrayList <Table> tables = new ArrayList<>(reload.reload());
        initialize(tables);

    }

    private void initialize(ArrayList<Table> tables){
        initData(tables);
        category.setCellValueFactory(new PropertyValueFactory<Table, String>("category"));
     //   build.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));


            int i=0;
            while (usersData.size()>i) {
                int j=i;
                TableColumn tableColumn=null;
                String currentBuild = usersData.get(i).getBuild();
                if (!reportTable.getColumns().stream().anyMatch((p)->(p.getId().equalsIgnoreCase(currentBuild)))){
                    tableColumn = new TableColumn(currentBuild);
                    tableColumn.setId(currentBuild);
                   // this.tableColumn=tableColumn;
                    tableColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("value"));
                    reportTable.getItems().setAll(usersData);
                }
                i++;

            }




        }

    private void initData(ArrayList<Table> tables) {
        usersData.setAll(tables);


    }
}

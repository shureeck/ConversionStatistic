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

import static constants.DatabaseStrings.TABLE_CONVERSION_GENERAL_STATISTIC;


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
        ArrayList <String> buildsList = new ArrayList<>(reload. getBuilds(TABLE_CONVERSION_GENERAL_STATISTIC));

        ArrayList<Table> tables =new ArrayList<>();
        int i=0;
        while (i<buildsList.size()) {
            tables.addAll(reload.reload(buildsList.get(i)));
            initialize(tables);
            tables.clear();
            i++;
        }

    }

    private void initialize(ArrayList<Table> tables){
        initData(tables);
        TableView<Table> temp = new TableView<>();
        category.setCellValueFactory(new PropertyValueFactory<Table, String>("category"));
     //   build.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
       // reportTable.getItems().setAll(usersData);
        ArrayList<TableColumn> columnList = new ArrayList<>( reportTable.getColumns());
        int i = 1;
        while (i<columnList.size()){
            columnList.get(i).setCellValueFactory(new PropertyValueFactory<Table,String>("close"));
            i++;
        }

        TableColumn tableColumn=null;
                    String currentBuild = usersData.get(i).getBuild();
                //Add new column
                if (!reportTable.getColumns().stream().anyMatch((p)->(p.getId().equalsIgnoreCase(currentBuild)))){
                    tableColumn = new TableColumn(currentBuild);
                    tableColumn.setId(currentBuild);
                    tableColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("value"));
                    temp.getColumns().add(tableColumn);
                   // this.tableColumn=tableColumn;

                }
                temp.getItems().addAll(usersData);

        reportTable.getColumns().add(temp.getColumns().get(0));

                    reportTable.setItems(temp.getItems());
        }


         //   tableColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("close"));

    private void initData(ArrayList<Table> tables) {
        usersData.setAll(tables);

    }
}

package gui;

import actions.Reload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import report.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Controller {
    private ObservableList<Table> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Map> reportTable=new TableView<>();
    @FXML
    private TableColumn<Map, String> category=new TableColumn<>();

    @FXML
    private Button analyze;

    private int i =0;

    @FXML
    public void onClickMethod(){
        Reload reload= new Reload();
        ArrayList <String> buildsList = new ArrayList<>();

        HashMap<String,String> tables =new HashMap<>();
        int i=0;
        while (i<buildsList.size()) {
            tables.putAll(reload.reload(buildsList.get(i)));
            initialize(tables, buildsList);
            tables.clear();
            i++;
        }

    }

    private void  initialize(HashMap<String,String> tables, ArrayList <String> buildsList) {
        reportTable.setItems(initData(tables));


        //  initData(tables);
        //  TableView<Table> temp = new TableView<>();

        category.setCellValueFactory(new MapValueFactory("category"));
        TableColumn<Map, String> tableColumn = null;
        int i = 0;
        while (i < buildsList.size()) {
            String currentBuild = buildsList.get(i);
            //Add new column
            if (!reportTable.getColumns().stream().anyMatch((p) -> (p.getId().equalsIgnoreCase(currentBuild)))) {
                tableColumn = new TableColumn<>(currentBuild);
                tableColumn.setId(currentBuild);
                tableColumn.setCellValueFactory(new MapValueFactory(currentBuild));
                reportTable.getColumns().add(tableColumn);
                // this.tableColumn=tableColumn;

            }
            i++;
        }
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = (TableColumn<Map, String> p) ->
                new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }
                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
         i=0;
         ArrayList<TableColumn> columnsList = new ArrayList<>(reportTable.getColumns());
       while (i<columnsList.size()) {
           columnsList.get(i).setCellFactory(cellFactoryForMap);
            i++;
            reportTable.refresh();
        }

    }
    private ObservableList<Map> initData(HashMap <String,String> tables) {
        Map<String, String> rtemperay = new HashMap<>(tables);
        ObservableList<Map> allData = FXCollections.observableArrayList();
        allData.setAll(rtemperay);
        return allData;

    }
}

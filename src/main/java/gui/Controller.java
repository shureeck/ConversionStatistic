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

import static constants.DatabaseStrings.TABLE_CONVERSION_BY_CATEGORIES;
import static constants.DatabaseStrings.TABLE_CONVERSION_GENERAL_STATISTIC;


public class Controller {
    private ObservableList<Table> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Map> convGenStatTable =new TableView<>();
    @FXML
    private TableColumn<Map, String> categoryGenStat =new TableColumn<>();
    @FXML
    private TableView <Map> convFailedTable = new  TableView();
    @FXML
    private TableColumn<Map, String> categoryFailConv =new TableColumn<>();
    @FXML
    private TableView <Map> convPassTable = new  TableView();
    @FXML
    private TableColumn<Map, String> categoryPassConv =new TableColumn<>();

    @FXML
    private Button analyze;

    private int i =0;

    @FXML
    public void onClickMethod(){
        convGenStatTable.getItems().clear();
        convFailedTable.getItems().clear();
        convPassTable.getItems().clear();

        convGenStatTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        convFailedTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        convPassTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));

        Reload reload= new Reload();
        ArrayList<String> cagoriesList = new ArrayList<>(reload.getCategorites(TABLE_CONVERSION_GENERAL_STATISTIC));
        ArrayList <String> buildsList = new ArrayList<>(reload.getBuilds(TABLE_CONVERSION_GENERAL_STATISTIC));
        ArrayList<String> cagoriesList1 = new ArrayList<>(reload.getCategorites(TABLE_CONVERSION_BY_CATEGORIES));
        addColumns(buildsList, convGenStatTable);
        addColumns(buildsList, convFailedTable);
        addColumns(buildsList, convPassTable);

        HashMap<String,String> tables =new HashMap<>();
        int i=0;
        while (i<cagoriesList.size()) {
            tables.putAll(reload.reload(TABLE_CONVERSION_GENERAL_STATISTIC, cagoriesList.get(i)));
            initialize(tables, convGenStatTable);
            tables.clear();
            i++;
        }

        tables.clear();
        i=0;
        while (i<cagoriesList1.size()) {
            tables.putAll(reload.reload(TABLE_CONVERSION_BY_CATEGORIES, cagoriesList1.get(i), "failed"));
            initialize(tables,  convFailedTable);
            tables.clear();
            i++;
        }
        tables.clear();
        i=0;
        while (i<cagoriesList1.size()) {
            tables.putAll(reload.reload(TABLE_CONVERSION_BY_CATEGORIES, cagoriesList1.get(i), "passed"));
            initialize(tables,  convPassTable);
            tables.clear();
            i++;
        }

    }

    private void  initialize(HashMap<String,String> tables, TableView<Map> tableName) {
        tableName.getItems().addAll(initData(tables));
        fillData(tableName);


    }

    private ObservableList<Map> initData(HashMap <String,String> tables) {
        Map<String, String> rtemperay = new HashMap<>(tables);
        ObservableList<Map> allData = FXCollections.observableArrayList();
        allData.setAll(rtemperay);
        return allData;

    }

    private void addColumns(ArrayList <String> buildsList, TableView<Map> tableName){
        int i = 0;
        while (i < buildsList.size()) {
            TableColumn<Map, String> column;
            String currentBuild = buildsList.get(i);
            //Add new column
            if (!tableName.getColumns().stream().anyMatch((p) -> (p.getId().equalsIgnoreCase(currentBuild)))) {
                column = new TableColumn<>(currentBuild);
               column.setId(currentBuild);
                column.setCellValueFactory(new MapValueFactory(currentBuild));
                tableName.getColumns().add(column);
            }
            i++;
        }
    }

    private void fillData(TableView<Map> tableName){
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
        ArrayList<TableColumn> columnsList = new ArrayList<>(tableName.getColumns());
        while (i<columnsList.size()) {
            columnsList.get(i).setCellFactory(cellFactoryForMap);
            i++;
           tableName.refresh();
        }
    }
}

package gui;

import actions.Reload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.*;

public class tabModelController{
    @FXML
    private TableView<Map> sorceStatistic = new TableView();
    @FXML
    private TableView<Map> applyGenStatTable = new TableView();

    @FXML
    private TableView<Map> applyFailedTable = new TableView();
    @FXML
    private TableView<Map> applyPassTable = new TableView();
    @FXML
    private TableView<Map> convGenStatTable = new TableView<>();
    @FXML
    private TableView<Map> convFailedTable = new TableView();
    @FXML
    private TableView<Map> convPassTable = new TableView();
    @FXML
    private TableView<Map> errorTable = new TableView();
    @FXML
    private TableView<Map> actionItemsTable = new TableView();
    @FXML
    private TabPane tabPane = new TabPane();
    @FXML
    private GridPane gridPane = new GridPane();

    public Node Refresh(String tabId) {

        ArrayList<Node> tablesList = new ArrayList<>(gridPane.getChildren().filtered((p) -> (p.getStyleClass().get(0).equalsIgnoreCase(TABLE_VIEW))));

        //Clear table data
        int i = 0;
        while (i < tablesList.size()) {
            ((TableView) tablesList.get(i)).getItems().clear();
            i++;
        }

        //Stet key for firs column
        i = 0;
        while (i < tablesList.size()) {
            ((TableView<Map>) tablesList.get(i)).getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
            i++;
        }

        //Get builds from database
        Reload reload = new Reload();
        ArrayList<String> buildsList = new ArrayList<>(reload.getBuilds(tabId));

        // Add column into tables
        i = 0;
        while (i < tablesList.size()) {
            addColumns(buildsList, (TableView<Map>) tablesList.get(i));
            i++;
        }

        //Get data from DB and past into UI tables
        getDataFormDB(TABLE_STATISTIC_BY_SOURCE, sorceStatistic, "", tabId);
        getDataFormDB(TABLE_APPLY_GENERAL_STATISTIC, applyGenStatTable, "", tabId);
        getDataFormDB(TABLE_APPLY_BY_CATEGORIES, applyFailedTable, FAILED, tabId);
        getDataFormDB(TABLE_APPLY_BY_CATEGORIES, applyPassTable, PASSED, tabId);

        getDataFormDB(TABLE_CONVERSION_GENERAL_STATISTIC, convGenStatTable, "", tabId);
        getDataFormDB(TABLE_CONVERSION_BY_CATEGORIES, convFailedTable, FAILED, tabId);
        getDataFormDB(TABLE_CONVERSION_BY_CATEGORIES, convPassTable, PASSED, tabId);
        getDataFormDB(TABLE_ERRORS_BY_CATEGORIES, errorTable, FAILED, tabId);
        getDataFormDB(TABLE_ACTION_ITEMS_GENERAL_STATISTIC, actionItemsTable, "", tabId);

        //Set height of table
        double hight;
        i = 0;
        while (i < tablesList.size()) {
            ((TableView<Map>) tablesList.get(i)).setFixedCellSize(25);
            hight = ((TableView<Map>) tablesList.get(i)).getItems().size() * 25 + 26;
            ((TableView<Map>) tablesList.get(i)).setPrefHeight(hight);
            i++;
        }

        decorateTable(sorceStatistic);


        return tabPane.getTabs().get(0).getContent();
    }

    @FXML
    private void initialize(HashMap<String, String> tables, TableView<Map> tableName) {
        tableName.getItems().addAll(initData(tables));
        fillData(tableName);

    }

    private ObservableList<Map> initData(HashMap<String, String> tables) {
        Map<String, String> rtemperay = new HashMap<>(tables);
        ObservableList<Map> allData = FXCollections.observableArrayList();
        allData.setAll(rtemperay);
        return allData;

    }

    private void addColumns(ArrayList<String> buildsList, TableView<Map> tableName) {
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

    private void fillData(TableView<Map> tableName) {
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = (TableColumn<Map, String> p) ->
                new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        if (t != null) {
                            return t.toString();
                        }
                        return "";
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
        int i = 0;
        ArrayList<TableColumn> columnsList = new ArrayList<>(tableName.getColumns());
        while (i < columnsList.size()) {
            columnsList.get(i).setCellFactory(cellFactoryForMap);
            i++;
            tableName.refresh();
        }
    }

    public void getDataFormDB(String dataBaseTable, TableView<Map> uiTable, String type, String tabId) {
        Reload temp = new Reload();
        ArrayList<String> cagoriesList = new ArrayList<>(temp.getCategories(dataBaseTable, tabId));
        HashMap<String, String> tables = new HashMap<>();
        int i = 0;
        while (i < cagoriesList.size()) {
            if (type.equalsIgnoreCase(PASSED) || type.equalsIgnoreCase(FAILED)) {
                tables.putAll(temp.reload(dataBaseTable, cagoriesList.get(i), tabId, type));
            } else
                tables.putAll(temp.reload(dataBaseTable, cagoriesList.get(i), tabId));
            initialize(tables, uiTable);
            tables.clear();
            i++;
        }
    }

    public void decorateTable(TableView table) {
        TableColumn first = (TableColumn) table.getColumns().get(1);

        first.setCellFactory((column) -> new TableCell<Map, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                ArrayList<Integer> redId = new ArrayList<>(checkStyle(table));
                if (redId.size()!=0) {
                    if (redId.contains(getIndex())) {
                        setStyle("-fx-background-color: F78686;");
                        setText(item);
                    } else {
                        setText(item);
                    }
                }
                else {setText(item);}
            }
        });
    }

    private ArrayList<Integer> checkStyle(TableView table){
        boolean result=false;
        ArrayList<Integer> redIdSList=new ArrayList<>();
        if (table.getColumns().size()<3){
            return redIdSList;
        }

        TableColumn first = (TableColumn) table.getColumns().get(1);
        TableColumn second = (TableColumn) table.getColumns().get(2);
        int i =0;
        while (first.getCellObservableValue(i)!=null){
            String valueFirst = (String) first.getCellObservableValue(i).getValue();
            String valueSecond =(String) second.getCellObservableValue(i).getValue();
            if (valueFirst!=null && valueSecond!=null) {
                if (valueFirst.compareTo(valueSecond)>0){
                    redIdSList.add(i);
                }
            }
            i++;
        }
        return redIdSList;

    }

}
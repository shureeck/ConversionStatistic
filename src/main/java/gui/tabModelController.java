package gui;

import actions.Reload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import report.ObjectInfo;

import java.io.IOException;
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
    @FXML
    private TableView<ObjectInfo> applyFailedObjects = new TableView<>();
    @FXML
    private GridPane gridPaneFailedObjects = new GridPane();
    @FXML
    private TableView<ObjectInfo> aiFailedObjects = new TableView<>();
    @FXML
    private TableView<ObjectInfo> errorFailedObjects = new TableView<>();
    @FXML
    private TableView<ObjectInfo> conversionFailedObjects = new TableView<>();

    public Node Refresh(String tabId) {

        ArrayList<Node> tablesList = new ArrayList<>(gridPane.getChildren().filtered((p) -> (p.getStyleClass().get(0).equalsIgnoreCase(TABLE_VIEW))));
        ArrayList<Node> tablesFailedObjects = new ArrayList<>(gridPaneFailedObjects.getChildren().filtered((p) -> (p.getStyleClass().get(0).equalsIgnoreCase(TABLE_VIEW))));

        //Clear table data
        int i = 0;
        while (i < tablesList.size()) {
            ((TableView) tablesList.get(i)).getItems().clear();
            i++;
        }
       i = 0;
        while (i <  tablesFailedObjects.size()) {
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

        getFailedObjectsFromDB(TABLE_APPLY_FAILED_OBJECTS, applyFailedObjects, tabId);
        getFailedObjectsFromDB(TABLE_CONVERSION_FAILED_OBJECTS, conversionFailedObjects, tabId);
        getFailedObjectsFromDB(TABLE_ERRORS_FAILED_OBJECTS, errorFailedObjects, tabId);
        getFailedObjectsFromDB(TABLE_ACTION_ITEMS_FAILED_OBJECTS, aiFailedObjects, tabId);


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
        decorateTable(applyPassTable);
        decorateTable(applyGenStatTable);
        decorateTable(applyFailedTable);
        decorateTable(convGenStatTable);
        decorateTable(convPassTable);
        decorateTable(convFailedTable);
        decorateTable(errorTable);
        decorateTable(actionItemsTable);

        highlightFailedObjects(applyFailedObjects);
        highlightFailedObjects(conversionFailedObjects);
        highlightFailedObjects(errorFailedObjects);
        highlightFailedObjects(aiFailedObjects);

        return tabPane.getTabs().get(0).getContent();
    }

    @FXML
    private void initialize(HashMap<String, String> tables, TableView<Map> tableName) {
        tableName.getItems().addAll(initData(tables));
        fillData(tableName);

        failedObjectsListener(applyFailedObjects);
        failedObjectsListener(conversionFailedObjects);
        failedObjectsListener(aiFailedObjects);
        failedObjectsListener(errorFailedObjects);


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

    public void getDataFormDB(String dataBaseTable, TableView uiTable, String type, String tabId) {
        Reload temp = new Reload();
        ArrayList<String> cagoriesList = new ArrayList<>(temp.getCategories(dataBaseTable, tabId));
        HashMap<String, String> tables = new HashMap<>();
        int i = 0;
        while (i < cagoriesList.size()) {
            if (type.equalsIgnoreCase(PASSED) || type.equalsIgnoreCase(FAILED)) {
                tables.putAll(temp.reload(dataBaseTable, cagoriesList.get(i), tabId, type));
            }
            else{tables.putAll(temp.reload(dataBaseTable, cagoriesList.get(i), tabId));}

            if (tables.size()>0){ initialize(tables, uiTable);}

            tables.clear();
            i++;
        }
    }

    public void  getFailedObjectsFromDB(String dataBaseTable, TableView uiTable, String tabId){
        Reload temp = new Reload();
        ObservableList<ObjectInfo> failedObjectList = FXCollections.observableArrayList(temp.reloadFailedObject(dataBaseTable, tabId));
        fillFailedObjects(uiTable, failedObjectList);
    }

    public void decorateTable(TableView table) {
        if (table.getItems().size()>1) {
            TableColumn first = (TableColumn) table.getColumns().get(1);

            first.setCellFactory((column) -> new TableCell<Map, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    ArrayList<Integer> redId = new ArrayList<>(checkStyle(table));
                    if (redId.size() != 0) {
                        if (redId.contains(getIndex())) {
                            if (getIndex() % 2 == 0) {
                                setStyle("-fx-background-color: rgba(255, 165, 0, 0.5);");
                            }
                            else {setStyle("-fx-background-color: rgba(255, 140, 0, 0.5);");}
                            setText(item);
                        } else {
                            setText(item);
                        }
                    } else {
                        setText(item);
                    }
                }
            });
        }
    }

    public void highlightFailedObjects(TableView table) {
        if(table.getItems().size()>0) {

            table.setRowFactory((Object column) -> {
                return new TableRow<ObjectInfo>() {

                    @Override
                    protected void updateItem(ObjectInfo item, boolean empty) {
                        int id = table.getItems().indexOf(item);
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item.isFixed()) {
                                if (id % 2 == 0) {
                                setStyle("-fx-background-color: rgba(221, 255, 221, 0.6);");}
                                else setStyle("-fx-background-color: rgba(116, 204, 116, 0.5);");
                            } else if (item.getComment() == null || item.getComment().equals("")) {
                                if (id % 2 == 0) {
                                    setStyle("-fx-background-color: rgba(247, 134, 134, 0.6);");
                                } else setStyle("-fx-background-color: rgba(252, 151, 151, 0.6);");
                            }
                            else setStyle(null);
                        }
                    }
                };
            });
        }
    }

    private ArrayList<Integer> checkStyle(TableView table){
        ArrayList<Integer> redIdSList=new ArrayList<>();
        if (table.getColumns().size()<3){
            return redIdSList;
        }

        TableColumn first = (TableColumn) table.getColumns().get(1);
        TableColumn second = (TableColumn) table.getColumns().get(2);
        int i =1;
        while (first.getCellObservableValue(i)!=null){
            String valueFirst = (String) first.getCellObservableValue(i).getValue();
            String valueSecond =(String) second.getCellObservableValue(i).getValue();
            if (valueFirst!=null && valueSecond!=null) {
                if (valueFirst.compareTo(valueSecond)!=0){
                    redIdSList.add(i);
                }
            }
            i++;
        }
        return redIdSList;
    }

    public void fillFailedObjects(TableView tableUI, ObservableList<ObjectInfo> failedObjects){
        ArrayList<TableColumn<ObjectInfo, String>> columnsList = new ArrayList<>(tableUI.getColumns());
        columnsList.get(0).setCellValueFactory(new PropertyValueFactory<>("Category"));
        columnsList.get(1).setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnsList.get(2).setCellValueFactory(new PropertyValueFactory<>("Comment"));
        tableUI.setItems(failedObjects);

        tableUI.setFixedCellSize(25);
        tableUI.setPrefHeight(failedObjects.size()*25+26);
    }

    public void failedObjectsListener(TableView table){
        table.setOnMouseClicked(event -> {
            if (event. getClickCount()==2 ){
                try {
                    ObjectInfo obj = (ObjectInfo) table.getSelectionModel().getSelectedItem();
                    loadObjectInfo(obj, table);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void loadObjectInfo(ObjectInfo failedObject, TableView table) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("objectinfogui.fxml"));
        AnchorPane pane = loader.load();

        ObjectInfoController objectinfoController = loader.getController();
        objectinfoController.setData(failedObject, table.getId());
        Stage objectInfoStage = new Stage();
        objectInfoStage.setResizable(false);
        objectInfoStage.setTitle(OBJECT_INFO);
        objectInfoStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(pane);
        objectInfoStage.setScene(scene);
        objectInfoStage.showAndWait();

        if(objectinfoController.ifOkClocked()) {
            ObjectInfo newObjectt = objectinfoController.getFailedObject();
            int z = table.getItems().indexOf(newObjectt);
            table.getItems().set(z, newObjectt);
        }
    }
}
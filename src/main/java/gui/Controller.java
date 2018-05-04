package gui;

import actions.AddReports;
import actions.Reload;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import report.Table;
import settings.XMLReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.*;



public class Controller {
    private ObservableList<Table> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Map> sorceStatistic = new TableView();
    @FXML
    private TableView<Map> applyGenStatTable = new TableView();
    @FXML
    private TableView<Map> applyFailedTable = new TableView();
    @FXML
    private TableView<Map> applyPassTable = new TableView();
    @FXML
    private TableView<Map> convGenStatTable = new TableView();
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

    private int i = 0;
    private int j = 1;

    @FXML
    public void onCancelClickMethod() {
        System.exit(0);
    }

    @FXML
    public void onAddReportsClick() {
        DirectoryChooser direcoryChooser = new DirectoryChooser();
        direcoryChooser.setTitle(CHOSE_REPORTS_FOLDER);
        File reportsFolder = direcoryChooser.showDialog(new Stage());
        AddReports addReports = new AddReports();
        addReports.addReports(reportsFolder);
    }

    @FXML
    public void onAddTabClick() {
        TabModel tabModel = new TabModel();
        Main main = new Main();
        main.addTabDialog(tabModel);
        addtab(tabModel);
    }

    @FXML
    public void onTestAddData(){
        TabModel tabModel = new TabModel();
    //    tabModel.getController().Refresh();
       // tabModelController tabModelController = new tabModelController();
        tabPane.getTabs().get(j).setContent( tabModel.getController().Refresh());
        j++;
    }
    @FXML
    public void onRefreshClickMethod() {
        //Clear table data
        sorceStatistic.getItems().clear();
        applyFailedTable.getItems().clear();
        applyPassTable.getItems().clear();
        applyGenStatTable.getItems().clear();
        convGenStatTable.getItems().clear();
        convFailedTable.getItems().clear();
        convPassTable.getItems().clear();
        errorTable.getItems().clear();
        actionItemsTable.getItems().clear();
        //Stet key for firs column
        sorceStatistic.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        applyFailedTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        applyPassTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        applyGenStatTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        convGenStatTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        convFailedTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        convPassTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        errorTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        actionItemsTable.getColumns().get(0).setCellValueFactory(new MapValueFactory<>("category"));
        //Get builds from database
        Reload reload = new Reload();
        ArrayList<String> buildsList = new ArrayList<>(reload.getBuilds(TABLE_CONVERSION_GENERAL_STATISTIC));
        // Add column into tables
        addColumns(buildsList, sorceStatistic);
        addColumns(buildsList, applyGenStatTable);
        addColumns(buildsList, applyFailedTable);
        addColumns(buildsList, applyPassTable);
        addColumns(buildsList, convGenStatTable);
        addColumns(buildsList, convFailedTable);
        addColumns(buildsList, convPassTable);
        addColumns(buildsList, errorTable);
        addColumns(buildsList, actionItemsTable);
        //Get data from DB and past into UI tables
        getDataFormDB(TABLE_STATISTIC_BY_SOURCE, sorceStatistic, "");
        getDataFormDB(TABLE_APPLY_GENERAL_STATISTIC, applyGenStatTable, "");
        getDataFormDB(TABLE_APPLY_BY_CATEGORIES, applyFailedTable, FAILED);
        getDataFormDB(TABLE_APPLY_BY_CATEGORIES, applyPassTable, PASSED);

        getDataFormDB(TABLE_CONVERSION_GENERAL_STATISTIC, convGenStatTable, "");
        getDataFormDB(TABLE_CONVERSION_BY_CATEGORIES, convFailedTable, FAILED);
        getDataFormDB(TABLE_CONVERSION_BY_CATEGORIES, convPassTable, PASSED);
        getDataFormDB(TABLE_ERRORS_BY_CATEGORIES, errorTable, FAILED);
        getDataFormDB(TABLE_ACTION_ITEMS_GENERAL_STATISTIC, actionItemsTable, "");

        //Set height of table
        double hight;
        sorceStatistic.setFixedCellSize(25);
        applyFailedTable.setFixedCellSize(25);
        applyPassTable.setFixedCellSize(25);
        applyGenStatTable.setFixedCellSize(25);
        convPassTable.setFixedCellSize(25);
        convGenStatTable.setFixedCellSize(25);
        convFailedTable.setFixedCellSize(25);
        errorTable.setFixedCellSize(25);
        actionItemsTable.setFixedCellSize(25);

        hight = convGenStatTable.getItems().size() * convGenStatTable.getFixedCellSize() + 26;
        convGenStatTable.setPrefHeight(hight);
        applyGenStatTable.setPrefHeight(hight);
        actionItemsTable.setPrefHeight(hight);
        hight = convPassTable.getItems().size() * convPassTable.getFixedCellSize() + 26;
        convPassTable.setPrefHeight(hight);
        convFailedTable.setPrefHeight(hight);
        convFailedTable.setPrefHeight(hight);
        hight = errorTable.getItems().size() * convPassTable.getFixedCellSize() + 26;
        errorTable.setPrefHeight(hight);
        hight = sorceStatistic.getItems().size() * 25 + 26;
        sorceStatistic.setPrefHeight(hight);
        hight = applyFailedTable.getItems().size() * 25 + 26;
        applyFailedTable.setPrefHeight(hight);
        applyPassTable.setPrefHeight(hight);


    }

    @FXML
    private void initialize(){
        XMLReader xmlReader  =  new XMLReader("E:\\IdeaProjects\\ConversionStatistic\\Project.xml");
        NodeList tabs = xmlReader.getTabNode();

        ArrayList <TabModel> tabModelsList = new ArrayList<>();

        int i =0;
        while (i<tabs.getLength()){
            String id =((Element)tabs.item(i)).getAttribute(ID);
            String folder =((Element)tabs.item(i)).getAttribute(FOLDER);
            String name =((Element)tabs.item(i)).getAttribute(NAME);
            String pair =((Element)tabs.item(i)).getAttribute(PAIR);
            tabModelsList.add(new TabModel(name,id,folder,pair));
            i++;
        }
        i=0;
        while (i<tabModelsList.size()) {
            tabPane.getTabs().add(tabModelsList.get(i).getTab());
            i++;

        }
    }


    private void initializeAS(HashMap<String, String> tables, TableView<Map> tableName) {
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
        i = 0;
        ArrayList<TableColumn> columnsList = new ArrayList<>(tableName.getColumns());
        while (i < columnsList.size()) {
            columnsList.get(i).setCellFactory(cellFactoryForMap);
            i++;
            tableName.refresh();
        }
    }

    public void getDataFormDB(String dataBaseTable, TableView<Map> uiTable, String type) {
        Reload temp = new Reload();
        ArrayList<String> cagoriesList = new ArrayList<>(temp.getCategorites(dataBaseTable));
        HashMap<String, String> tables = new HashMap<>();
        int i = 0;
        while (i < cagoriesList.size()) {
            if (type.equalsIgnoreCase(PASSED) || type.equalsIgnoreCase(FAILED)) {
                tables.putAll(temp.reload(dataBaseTable, cagoriesList.get(i), type));
            } else
                tables.putAll(temp.reload(dataBaseTable, cagoriesList.get(i)));
            initializeAS(tables, uiTable);
            tables.clear();
            i++;
        }

    }

    public void addtab(TabModel tabModel) {
        Tab tab = new Tab(tabModel.getTabName());
        tab.setContent(tabModel.getTabContent());
        tab.setId(tabModel.getTabId());

        tabPane.getTabs().addAll( tabModel.getTab());
}

}

package gui;

import actions.AddReports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import report.Table;
import settings.Settings;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static constants.StringsConstant.ADD_NEW_TAB;
import static constants.StringsConstant.CHOSE_REPORTS_FOLDER;



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

    private  Settings settings = new Settings();

    private int i = 0;

    @FXML
    public void onCancelClickMethod() {
        System.exit(0);
    }

    @FXML
    public void onAddReportsClick() {
        DirectoryChooser direcoryChooser = new DirectoryChooser();
        direcoryChooser.setTitle(CHOSE_REPORTS_FOLDER);
        File reportsFolder = direcoryChooser.showDialog(new Stage());
        AddReports addReports = new AddReports(settings);
        addReports.addReports(reportsFolder);
    }

    @FXML
    public void onAddTabClick() {
        TabModel tabModel = new TabModel();
        if (addTabDialog(tabModel)) {
            addtab(tabModel);
            this.settings.addTabModel(tabModel);
        }
    }

    @FXML
    public void onRefreshClickMethod(){
        TabModel tabModel = new TabModel();
        int i=0;
        while (i<tabPane.getTabs().size()){
            String tabId = tabPane.getTabs().get(i).getId();
            tabPane.getTabs().get(i).setContent(tabModel.getController().Refresh(tabId));
            i++;
        }
    }

    @FXML
    private void initialize(){
        tabPane.getTabs().addAll(settings.getTabList());
    }

    public void addtab(TabModel tabModel) {
        Tab tab = new Tab(tabModel.getTabName());
        tab.setContent(tabModel.getTabContent());
        tab.setId(tabModel.getTabId());
        tabPane.getTabs().addAll( tabModel.getTab());
}

    public boolean addTabDialog(TabModel tab){
        boolean ifOkPressed=false;
        try {
            FXMLLoader addTabLoader =new FXMLLoader();
            addTabLoader.setLocation(getClass().getResource("addtab.fxml"));
            AnchorPane pane =  addTabLoader.load();
            Stage addTabStage = new Stage();
            addTabStage.setResizable(false);
            addTabStage.setTitle(ADD_NEW_TAB);
            addTabStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            addTabStage.setScene(scene);

            AddTabController controller = addTabLoader.getController();
            controller.setTabModel(tab);
            addTabStage.showAndWait();
             ifOkPressed=controller.ifOkPressed();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ifOkPressed;
    }

}

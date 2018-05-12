package gui;

import actions.AddReports;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.Settings;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;

import static constants.StringsConstant.ADD_NEW_TAB;
import static constants.StringsConstant.CHOSE_REPORTS_FOLDER;



public class Controller {
    @FXML
    private TabPane tabPane = new TabPane();

    private  Settings settings = new Settings();
    private  TabModel tabModel = new TabModel();


    @FXML
    public void onCancelClickMethod() {
        System.exit(0);
    }

    @FXML
    public void onAddReportsClick() {
        DirectoryChooser direcoryChooser = new DirectoryChooser();
        direcoryChooser.setTitle(CHOSE_REPORTS_FOLDER);

       File reportsFolder = direcoryChooser.showDialog(new Stage());

        AddReports addReports = new AddReports(settings, reportsFolder);
        new Thread (addReports).start();
        showProgressBarDialog(addReports);
   //     addReports.addReports(reportsFolder);
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
        int i=0;
        while (i<tabPane.getTabs().size()){
            String tabId = tabPane.getTabs().get(i).getId();
            tabPane.getTabs().get(i).setContent(tabModel.getController().Refresh(tabId));
            i++;
        }

        new Thread(getTask()).start();
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

    public void showProgressBarDialog(Task task ) {
        try{
        FXMLLoader showProgressBar = new FXMLLoader();
        showProgressBar.setLocation(getClass().getResource("ProgressBar.fxml"));
        AnchorPane pane = showProgressBar.load();
        ProgressBarController progressBarController = showProgressBar.getController();
        progressBarController.setTask(task);
        Stage progressBarStage = new Stage();
        progressBarStage.setResizable(false);
        progressBarStage.initStyle(StageStyle.UNDECORATED);
       // progressBarStage.setTitle("rogressBar");
        progressBarStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(pane);
        progressBarStage.setScene(scene);

    //    AddTabController controller = showProgressBar.getController();
        progressBarStage.showAndWait();

    }
     catch (IOException e) {
        e.printStackTrace();
    }
    }

    Task getTask(){
        return new  Task(){
            @Override
            protected Object call() throws Exception {

                return null;
            }
        };
    }

}

package gui;

import actions.AddReports;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

import java.io.File;
import java.io.IOException;

import static constants.StringsConstant.ADD_NEW_TAB;
import static constants.StringsConstant.ADD_RELESE_BUILD;
import static constants.StringsConstant.CHOSE_REPORTS_FOLDER;



public class Controller {
    @FXML
    private TabPane tabPane = new TabPane();
    private  Settings settings = new Settings();

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

        Task task = getTaskFillTab();
        new Thread(new Task() {
            @Override
            public Object call() {
                showProgressBarDialog(task);
                return null;
            }
        }).start();
       Thread bgThread = new Thread(task);
       bgThread.setDaemon(true);
       bgThread.start();
       showProgressBarDialog(task);
       }

       @FXML
       public void onSetReleseBuils(){
           FXMLLoader loader =new FXMLLoader();
           loader.setLocation(getClass().getResource("addreleasebuild.fxml"));
           AddReleaseBuildController controller =  loader.getController();
           AnchorPane pane = null;
           try {
               pane =  loader.load();
           } catch (IOException e1) {
               e1.printStackTrace();
           }
           Stage addTabStage = new Stage();
           addTabStage.setResizable(false);
           addTabStage.setTitle(ADD_RELESE_BUILD);
           addTabStage.initModality(Modality.APPLICATION_MODAL);
           Scene scene = new Scene(pane);
           addTabStage.setScene(scene);

           addTabStage.showAndWait();
         //  ifOkPressed=controller.ifOkPressed();

       // return ifOkPressed;
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
        pane.setStyle(" -fx-border-color:c3c3c3; -fx-border-width: 2pt;");
        progressBarStage.initStyle(StageStyle.UNDECORATED);
       // progressBarStage.setTitle("rogressBar");
        progressBarStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(pane);
        progressBarStage.setScene(scene);
    //    AddTabController controller = showProgressBar.getController();
        progressBarStage.show();

    }
     catch (IOException e) {
        e.printStackTrace();
    }
    }

    public Task getTaskFillTab(){
        return new Task(){
            @Override
            protected Object call() throws Exception {
                int i=0;
                int size = tabPane.getTabs().size();
                while (i<size){
                    updateProgress(i, size);
                    Thread.sleep(1000);
                    Tab tab =  tabPane.getTabs().get(i);
                    fillTab(tab);
                    i++;

                }
                return null;
            }
        };
    }

    private void fillTab(Tab targetTab ){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TabModel tabModel = new TabModel();
                targetTab.setContent(tabModel.getController().Refresh(targetTab.getId()));            }
        });

    }

    public TabPane getTabPane() {
        return tabPane;
    }
}

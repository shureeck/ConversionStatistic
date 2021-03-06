package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TabModel {
    private String reportFolder;
    private String tabId;
    private String tabName;
    private String pair;

    @FXML
    private Tab myTab = new Tab();

    public TabModel (){
        try {
            AnchorPane anchorpane =  FXMLLoader.load(getClass().getResource("tabmodel.fxml"));
            TabPane tabPane = (TabPane) anchorpane.getChildren().get(0);
            myTab=tabPane.getTabs().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TabModel(String name, String folder, String pair){
        this.tabName = name;
        this.tabId =folder;
        this.reportFolder=folder;
        this.pair=pair;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("tabmodel.fxml"));
            fxmlLoader.load();
            AnchorPane anchorpane =  fxmlLoader.getRoot();
            TabPane tabPane = (TabPane) anchorpane.getChildren().get(0);

            myTab=tabPane.getTabs().get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
       myTab.setText(name);
       myTab.setId(tabId);
    }

    public Tab getTab(){return myTab;}
    public String getTabName(){return tabName;}
    public String getTabId(){return tabId;}
    public String getReportFolder(){return reportFolder;}
    public Node getTabContent(){
        Node content = myTab.getContent();
        return content;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
        myTab.setText(tabName);
    }
    public void setTabId(String tabId) {
        this.tabId = tabId;
        myTab.setId(tabId);
    }
    public void setReportFolder(String reportFolder) {this.reportFolder = reportFolder;}

    public tabModelController getController()
    {
            FXMLLoader addTabLoader =new FXMLLoader();
        addTabLoader.setLocation(getClass().getResource("tabmodel.fxml"));
        try {
            addTabLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tabModelController controller = addTabLoader.getController();
    return controller;}

    public String getPair() { return pair;}

    public void setPair(String pair) {this.pair = pair; }
}

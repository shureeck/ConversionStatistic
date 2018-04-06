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
    public TabModel (String name, String  id){
        this.tabName = name;
        this.tabId =id;
        try {
            AnchorPane anchorpane =  FXMLLoader.load(getClass().getResource("tabmodel.fxml"));
            TabPane tabPane = (TabPane) anchorpane.getChildren().get(0);

            myTab=tabPane.getTabs().get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
       myTab.setText(name);
       myTab.setId(id);
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
}

package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TabModel {
    @FXML
    private Tab myTab = new Tab();

    public TabModel (String name, String  id){
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

    public Tab getTab(){

        return myTab;
    }
}

package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static constants.StringsConstant.ADD_NEW_TAB;
import static constants.StringsConstant.TITLE_MAINN_WINDOW;

public class Main extends Application {
    private Stage primaryStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(TITLE_MAINN_WINDOW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

    public void addTabDialog(TabModel tab){
        try {
            FXMLLoader addTabLoader =new FXMLLoader();
            addTabLoader.setLocation(getClass().getResource("addtab.fxml"));
            AnchorPane pane = (AnchorPane) addTabLoader.load();
            Stage addTabStage = new Stage();
            addTabStage.setResizable(false);
            addTabStage.setTitle(ADD_NEW_TAB);
            addTabStage.initModality(Modality.APPLICATION_MODAL);
            addTabStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
           addTabStage.setScene(scene);

           AddTabController controller = addTabLoader.getController();
           controller.setTabModel(tab);
           addTabStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

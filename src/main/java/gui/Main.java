package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static constants.StringsConstant.TITLE_MAINN_WINDOW;

public class Main extends Application {
    private Stage primaryStage = new Stage();


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        primaryStage.setTitle(TITLE_MAINN_WINDOW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

    public static void main() {
        launch();
    }
}

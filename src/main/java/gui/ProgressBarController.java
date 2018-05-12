package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ProgressBarController {
    @FXML
    private ProgressBar progressBar = new ProgressBar(-1);
    @FXML
    private Label labelStatus = new Label();

    private Task task;

    public void setTask(Task task){
        this.task = task;
        status();
        task.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelStatus.setText(newValue);
            }
        });

        task.runningProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue){
                    Stage stage=(Stage)progressBar.getScene().getWindow();
                    stage.close();
                }
            }
        });

    }

    private void status(){
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());
    }
}

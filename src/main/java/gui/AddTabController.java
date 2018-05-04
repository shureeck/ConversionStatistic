package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import static constants.StringsConstant.CHOSE_REPORTS_FOLDER;
import static constants.StringsConstant.REGEXP_SPLIT_GENERAL_STAT;

public class AddTabController {
    private String TabName;
    private String reportFolder;
    private String TabId;


    @FXML
    TextField tabNameField = new TextField();
    @FXML
    TextField reportFolderField = new TextField();
    @FXML
    Button buttonOK = new Button();
    @FXML
    Button buttonCancel = new Button();
    @FXML
    Button buttonBrowse = new Button();
    @FXML
    ComboBox<String> cbTarget = new ComboBox<>();
    @FXML
    ComboBox<String>cbSource = new ComboBox<>();

    private TabModel tabModel;
    @FXML
    public void onOkClick(){
      TabName=tabNameField.getText();
      tabModel.setTabName(TabName);
      tabModel.setReportFolder(reportFolderField.getText());
      tabModel.setTabId(TabName.replaceAll(REGEXP_SPLIT_GENERAL_STAT, ""));
      onCancelClickMethod();
    }
    @FXML
    public void onCancelClickMethod() {
        // get a handle to the stage
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void onBrowseClickMethod(){
        DirectoryChooser direcoryChooser = new DirectoryChooser();
        direcoryChooser.setTitle(CHOSE_REPORTS_FOLDER);
        File reportsFolder = direcoryChooser.showDialog(new Stage());
        String folder = reportsFolder.getName();
        this.reportFolder = folder;
        this.reportFolderField.setText(folder);

    }

    @FXML
    public void initialize (){
        if (tabNameField.getText().trim().equals("")||reportFolderField.getText().trim().equals("")){
            buttonOK.setDisable(true);
        }

        reportFolderField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ( tabNameField.getText().trim().equals("")||reportFolderField.getText().trim().equals("")){
                    buttonOK.setDisable(true);
                }
                else buttonOK.setDisable(false);
            }
        });

        tabNameField.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
             if ( tabNameField.getText().trim().equals("")||reportFolderField.getText().trim().equals("")){
                 buttonOK.setDisable(true);
             }
             else buttonOK.setDisable(false);
         }
     });
    }



    public void setTabModel(TabModel tabModel) {this.tabModel = tabModel;}


}

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

import static constants.StringsConstant.*;
import static constants.Vendors.*;

public class AddTabController {
    private String TabName;
    private String reportFolder;
    private String TabId;
    private boolean ifOkPressed=false;


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
      tabModel.setPair(cbSource.getValue()+"-"+cbTarget.getValue());
      ifOkPressed=true;
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
            fillSourceComboBox();
            fillTargetComboBox();
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

    @FXML
    public void setOnSelect(){
        if (cbSource.getValue().equals(ORACLE)){
            fillTargetComboBox();
        }
        else if (cbSource.getValue().equals(POSTGRESQL)){
            cbTarget.getItems().setAll( MYSQL,AURORA_MYSQL, POSTGRESQL, AURORA_POSTGRESQL);
            cbTarget.setValue(MYSQL);

        }
        else if (cbSource.getValue().equals(SQLSERVER)){
            cbTarget.getItems().setAll( POSTGRESQL, AURORA_POSTGRESQL, MYSQL,AURORA_MYSQL, SQLSERVER);
            cbTarget.setValue(POSTGRESQL);

        }
        else if (cbSource.getValue().equals(MYSQL)){
            cbTarget.getItems().setAll( POSTGRESQL, AURORA_POSTGRESQL, MYSQL);
            cbTarget.setValue(POSTGRESQL);

        }
        else if (cbSource.getValue().equals(DB2)){
            cbTarget.getItems().setAll( POSTGRESQL, AURORA_POSTGRESQL, MYSQL,AURORA_MYSQL);
            cbTarget.setValue(POSTGRESQL);

        }
        else { cbTarget.getItems().setAll( REDSHIFT);
            cbTarget.setValue(REDSHIFT);
        }
    }

    private void fillSourceComboBox(){
        cbSource.getItems().addAll(
                ORACLE, POSTGRESQL, SQLSERVER, MYSQL, DB2,
                ORACLE_DWH, SQLSERVER_DWH, TERADATA, NETEZA, GREENPLUN, VERTICA, REDSHIFT );
        cbSource.setValue(ORACLE);
    }
    private void fillTargetComboBox(){
        cbTarget.getItems().setAll(POSTGRESQL, AURORA_POSTGRESQL, MYSQL,AURORA_MYSQL, ORACLE);
        cbTarget.setValue(POSTGRESQL);
    }



    public void setTabModel(TabModel tabModel) {this.tabModel = tabModel;}

    public boolean ifOkPressed(){return ifOkPressed; }


}

package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static constants.StringsConstant.REGEXP_SPLIT_GENERAL_STAT;

public class AddTabController {
    private String TabName;
    private String ReportFolder;
    private String TabId;


    @FXML
    TextField tabNameField = new TextField();
    @FXML
    TextField reportFolderField = new TextField();
    @FXML
    Button buttonOK = new Button();
    @FXML
    Button buttonCancel = new Button();

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

    public void setTabModel(TabModel tabModel) {this.tabModel = tabModel;}
}

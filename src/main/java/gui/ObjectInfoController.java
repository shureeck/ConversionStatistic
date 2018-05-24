package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import report.ObjectInfo;
import sqlquery.DataBaseConnection;

import java.sql.Connection;

import static constants.DatabaseStrings.*;
import static constants.DatabaseStrings.UPDATE_COMMENT;

public class ObjectInfoController {
    private ObjectInfo failedObject;

    @FXML
    private Button addCommentBtn = new Button();
    @FXML
    private Button cancelBtn = new Button();
    @FXML
    private TextField texListNumberTxtEdit = new TextField();
    @FXML
    private TextField objectTypeTxtEdit = new TextField();
    @FXML
    private TextField objectNameTxtEdit = new TextField();
    @FXML
    private TextField buildNumberTxtEdit = new TextField();
    @FXML
    private TextField commentTxtEdit = new TextField();
    @FXML
    private TextArea reprotTextArea = new TextArea();

    private String tableId;
    private boolean ifOkCliked=false;
    private String firstComment="";

    @FXML
    public void initialize(){
        commentTxtEdit.textProperty().addListener(new ChangeListener<String>() {
            String oldComment = commentTxtEdit.getText();
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!firstComment.equals(commentTxtEdit.getText())){
                    addCommentBtn.setDisable(false);
                }
                else {addCommentBtn.setDisable(true);}
            }
        });
    }

    @FXML
    public void onCancelClickMethod() {
        // get a handle to the stage
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void onAddReportClickMethod(){
        String comment;
        if(commentTxtEdit.getText()==null){comment="";}
        else {comment = commentTxtEdit.getText();}
        String table = getDBTable(tableId);
        String args[]= {table, comment, failedObject.getCategory(), failedObject.getName(), failedObject.getTab()};
        String query = String.format(UPDATE_COMMENT, args);
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        dbc.executeUpdateStatement(connection, query);
        failedObject.setComment(comment);
        ifOkCliked=true;
        onCancelClickMethod();
    }

    private void initFields(ObjectInfo failedObject) {
        texListNumberTxtEdit.setText(String.valueOf(failedObject.getTestListNumber()));
        objectTypeTxtEdit.setText(failedObject.getCategory());
        objectNameTxtEdit.setText(failedObject.getName());
        buildNumberTxtEdit.setText(String.valueOf(failedObject.getBuild()));
        reprotTextArea.setText(failedObject.getReport());
        if (failedObject.getComment()!=null){
            commentTxtEdit.setText(failedObject.getComment());
        }
        else commentTxtEdit.setText("");
        firstComment = commentTxtEdit.getText();
    }

    public void setData(ObjectInfo failedObject, String tableId){
        this.failedObject = failedObject;
        this.tableId = tableId;
        initFields(failedObject);
    }

    private String getDBTable(String tableId){
        String dbTable = null;
        switch (tableId){
            case "applyFailedObjects": dbTable= TABLE_APPLY_FAILED_OBJECTS;
                break;
            case "conversionFailedObjects": dbTable= TABLE_CONVERSION_FAILED_OBJECTS;
                 break;
            case "errorFailedObjects": dbTable= TABLE_ERRORS_FAILED_OBJECTS;
                break;
            case "aiFailedObjects": dbTable= TABLE_ACTION_ITEMS_FAILED_OBJECTS;
                break;
        }

        return dbTable;
    }


    public boolean ifOkClocked(){return ifOkCliked;}

    public ObjectInfo getFailedObject() {
        return failedObject;
    }
}

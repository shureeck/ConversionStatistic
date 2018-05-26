package gui;

import actions.Reload;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sqlquery.DataBaseConnection;
import sqlquery.Update_SQL_Query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.REGEXP_NUMBERS;

public class AddReleaseBuildController {
    @FXML
    private TextField releasBuild = new TextField();
    @FXML
    private ComboBox currentBuild = new ComboBox();
    @FXML
    private Button addReleseBuild = new Button();
    @FXML
    Button buttonCancel = new Button();

    private TabPane tabPane = new TabPane();



    @FXML
    private void initialize(){

        releasBuild.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(releasBuild.getText().length()>0 && releasBuild.getText().matches(REGEXP_NUMBERS)){
                    addReleseBuild.setDisable(false);
                }
                else  addReleseBuild.setDisable(true);
            }
        });

        tabPane = getTabPane();
        currentBuild.setItems(FXCollections.observableArrayList(getBuilds(tabPane)));
        currentBuild.getSelectionModel().selectFirst();
    }

    @FXML
    public void onAddReleaseBuild(){
        String releaseBuild = releasBuild.getText();
        String currentBuid =(String) currentBuild.getValue();
        ArrayList<String> tableList = new ArrayList<>(getTableList());
        Update_SQL_Query query = new Update_SQL_Query();
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        int i=0;
        while (i<tableList.size()) {
            dbc.executeUpdateStatement(connection, query.addReleaseBuildQuery(tableList.get(i), releaseBuild, currentBuid ));
            i++;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCancelClickMethod();
    }

    @FXML
    public void onCancelClickMethod() {
        // get a handle to the stage
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private SortedSet<String> getBuilds(TabPane tabPane){
        SortedSet<String> result = new TreeSet<>();
        ArrayList<Tab> tabList = new ArrayList<>(tabPane.getTabs()) ;
        int i =0;
        while (tabList.size()>i) {
            Reload reload = new Reload();
            result.addAll(reload.getBuilds(tabList.get(i).getId()));
            i++;
        }
        return result;
    }

    public TabPane getTabPane(){
        TabPane result;
        FXMLLoader loader = new  FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = loader.getController();
        result = controller.getTabPane();
        return  result;
    }

    public ArrayList<String> getTableList(){
        ArrayList<String> tableList = new ArrayList<>();
        tableList.add(TABLE_APPLY_BY_CATEGORIES);
        tableList.add(TABLE_APPLY_GENERAL_STATISTIC);
        tableList.add(TABLE_STATISTIC_BY_SOURCE);
        tableList.add(TABLE_ACTION_ITEMS_GENERAL_STATISTIC);
        tableList.add(TABLE_CONVERSION_GENERAL_STATISTIC);
        tableList.add(TABLE_CONVERSION_BY_CATEGORIES);
        tableList.add(TABLE_ERRORS_BY_CATEGORIES);
        return  tableList;
    }


}

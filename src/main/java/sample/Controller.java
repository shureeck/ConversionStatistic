package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<User> reportTable;
    @FXML
    private TableColumn<User, String> category;
    @FXML
    private TableColumn<User, Integer> build;
    @FXML
    private Button analyze;

    private int i =0;

    @FXML
    public void onClickMethod(){

        initialize();

    }

    private void initialize(){
        initData();
        category.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        build.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));

            TableColumn tableColumn = new TableColumn(String.valueOf(i++));
        reportTable.getColumns().addAll(tableColumn);
        reportTable.setItems(usersData);
        }

    private void initData() {
        usersData.add( new User("Functions",23));
        usersData.add(new User("Table",220));
        usersData.add(new User("View",2));
        usersData.add(new User("Procedure",55));


    }
}

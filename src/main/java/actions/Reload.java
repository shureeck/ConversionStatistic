package actions;

import sqlquery.DataBaseConnection;
import sqlquery.SelectSQLQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.*;

public class Reload {
    public HashMap<String,String> reload(String build){
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        //ResultSet resultSet = dbc.executeStatement(connection, "SELECT * FROM conversion_general_statistic WHERE build = \""+build+"\";");
        ResultSet resultSet = dbc.executeStatement(connection, "SELECT * FROM conversion_general_statistic;");
       HashMap<String,String> result = new HashMap<>();
        result.put("category","passed");
        result.put("category","failed");
        result.put("category","total item");

        try {

            while (resultSet.next()) {
                result.put(resultSet.getString("build"),resultSet.getString("passed"));
                result.put(resultSet.getString("build"),resultSet.getString("failed"));
                result.put(resultSet.getString("build"),resultSet.getString("totalitem"));
//                {
//                    Table table = new Table();
//                    table.setBuild(resultSet.getString("build"));
//                    table.setCategory("passed");
//                    table.setValue(resultSet.getString("passed"));
//                    result.add(table);
//                }
//                {
//                    Table table = new Table();
//                    table.setBuild(resultSet.getString("build"));
//                    table.setCategory("failed");
//                    table.setValue(resultSet.getString("failed"));
//                    result.add(table);
//                }
//                {
//                    Table table = new Table();
//                    table.setBuild(resultSet.getString("build"));
//                    table.setCategory("total items");
//                    table.setValue(resultSet.getString("totalitem"));
//                    result.add(table);
//                }
            }}
        catch (java.sql.SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList <String> getCategorites(String tableName){
        SelectSQLQuery selectQuery = new SelectSQLQuery();
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        ResultSet rs = dbc.executeStatement(connection, selectQuery.selectCategorites(tableName));
        ArrayList<String> categories = new ArrayList<>();
        try {
            while (rs.next()){
                categories.add(rs.getString(CATEGORY));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

}

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
    public HashMap<String,String> reload(String category){
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        ResultSet resultSet = dbc.executeStatement(connection, "SELECT * FROM conversion_general_statistic WHERE category = \""+category+"\";");
       HashMap<String,String> result = new HashMap<>();
        result.put("category",category);

        try {

            while (resultSet.next()) {
                result.put(resultSet.getString("build"),resultSet.getString("count"));
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

    public ArrayList <String> getBuilds(String tableName){
        SelectSQLQuery selectQuery = new SelectSQLQuery();
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        ResultSet rs = dbc.executeStatement(connection, selectQuery. selectBuilds(tableName));
        ArrayList<String> builds = new ArrayList<>();
        try {
            while (rs.next()){
                builds.add(rs.getString(BUILD));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return builds;
    }

}

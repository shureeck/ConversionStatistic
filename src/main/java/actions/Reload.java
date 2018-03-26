package actions;

import report.Table;
import sqlquery.DataBaseConnection;
import sqlquery.SelectSQLQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.BUILD;

public class Reload {
    public ArrayList<Table> reload(String build){
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        ResultSet resultSet = dbc.executeStatement(connection, "SELECT * FROM conversion_general_statistic WHERE build = \""+build+"\";");
        ArrayList<Table> result = new ArrayList<Table>();

        try {

            while (resultSet.next()) {
                {
                    Table table = new Table();
                    table.setBuild(resultSet.getString("build"));
                    table.setCategory("passed");
                    table.setValue(resultSet.getString("passed"));
                    result.add(table);
                }
                {
                    Table table = new Table();
                    table.setBuild(resultSet.getString("build"));
                    table.setCategory("failed");
                    table.setValue(resultSet.getString("failed"));
                    result.add(table);
                }
                {
                    Table table = new Table();
                    table.setBuild(resultSet.getString("build"));
                    table.setCategory("total items");
                    table.setValue(resultSet.getString("totalitem"));
                    result.add(table);
                }
            }}
        catch (java.sql.SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList <String> getBuilds(String tableName){
        SelectSQLQuery selectBuilds = new SelectSQLQuery();
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        ResultSet rs = dbc.executeStatement(connection, selectBuilds.selectBuilds(tableName));
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

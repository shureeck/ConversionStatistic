package actions;

import report.Table;
import sqlquery.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static constants.DatabaseStrings.*;

public class Reload {
    public ArrayList<Table> reload(){
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        ResultSet resultSet = dbc.executeStatement(connection, "SELECT * FROM conversion_general_statistic ORDER BY build;");
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
}

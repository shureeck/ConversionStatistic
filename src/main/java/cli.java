import report.Report;
import sqlquery.DataBaseConnection;
import sqlquery.SQL_Query;

import java.sql.Connection;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.SPACE;

public class cli {
    public static void main(String args[]){
            Report report = new Report("E:\\ReportExamples\\Reports\\Oracle-PostgreSQL\\Oracle_PostgreSQL__Transform.csv");
            int i =0;
        SQL_Query query =new SQL_Query(report);
        int size = query.getSqlStatement().size();

        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
  while (i<size) {
            dbc.executeUpdateStatement(connection, query.getSqlStatement().get(i));
            i++;
        }
        System.out.println(TABLE_STATISTIC_BY_SOURCE+SPACE+i);
        //ResultSet resultSet = dbc.executeStatement(connection, "SELECT * FROM conversion_general_statistic ORDER BY build;");
         

        }
}

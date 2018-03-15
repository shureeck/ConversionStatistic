import org.omg.CORBA.DATA_CONVERSION;

import java.sql.Connection;

import static constants.StringsConstant.*;
import static constants.DatabaseStrings.*;

public class main {
    public static void main(String[]args){
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

    }
}

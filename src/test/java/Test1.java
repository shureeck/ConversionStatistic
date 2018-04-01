import org.testng.Assert;
import org.testng.annotations.*;
import sqlquery.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static constants.DatabaseStrings.BASE;
import static constants.DatabaseStrings.BASE_LOGIN;

@Test
public class Test1 {
    private Connection connection;
    private DataBaseConnection dbc;
    private String[] ext;
    @BeforeClass
    void createConnection(){
       dbc = new DataBaseConnection(BASE,BASE_LOGIN,BASE_LOGIN);
       connection = dbc.connectToDatabase();
    }
    @AfterClass
    void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @BeforeMethod
    void prepera(){
        ext= new String[]{"procedure", "trigger", "index", "constraint", "view", "function", "table", "sequence", "type"};
    }
    @AfterMethod()
    void clearArray(){
        ext=null;
    }
    @Test
    void testGetCategories(){
      ResultSet res = dbc.executeStatement(connection,"SELECT category FROM apply_by_categories WHERE build=2222" );
        ArrayList<String> act = new ArrayList<>();
        try {
            while (res.next()) {
                act.add(res.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertEqualsNoOrder(act.toArray(),ext);

    }
    @Test
    void test2(){
        Assert.assertNotNull(ext);

    }
}

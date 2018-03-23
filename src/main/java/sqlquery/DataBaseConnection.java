package sqlquery;

import java.sql.*;

public class DataBaseConnection {
    private String base;
    private String base_login;
    private String base_password;

    public DataBaseConnection(String base, String base_login, String base_password){
        this.base=base;
        this.base_login=base_login;
        this.base_password=base_password;
    }

        public Connection connectToDatabase (){
            String base = this.base;
            String base_login = this.base_login;
            String base_password = this.base_password;
            Connection connection=null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                connection = DriverManager.getConnection(base, base_login, base_password);
               /*Statement proc = connection.createStatement();
                ResultSet rs = proc.executeQuery("SELECT * FROM apply_general_statistic");
                /*while (rs.next()) {
                    System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3));
                }*/
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            return connection;
        }

        public ResultSet executeStatement(Connection connection, String sqlQuery){
            ResultSet resultSet=null;
            try {

               Statement statement = connection.createStatement();
                resultSet=statement.executeQuery(sqlQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultSet;
        }

    public int executeUpdateStatement(Connection connection, String sqlQuery){
        int resultSet=0;
        try {

            Statement statement = connection.createStatement();
            resultSet=statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    }
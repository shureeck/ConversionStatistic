package actions;

import report.ObjectInfo;
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
    public HashMap<String,String> reload( String tableName, String category, String tabID) {
        String args[] = {tableName, category, tabID};
        String statement = String.format(SELECT_ALL_FROM_WHERE_AND, args);
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        ResultSet resultSet = dbc.executeStatement(connection, statement);
        HashMap<String, String> result = new HashMap<>();
        result.put("category", category);

        if (category.equalsIgnoreCase(RELEASE_BUILD)) {
            result.putAll(getReleseBuild(dbc, connection, tableName, tabID));
        }
        else{
            try {

                while (resultSet.next()) {
                    result.put(resultSet.getString("build"), resultSet.getString("count"));
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
    }
        return result;
    }

    public HashMap<String,String> reload( String tableName, String category,String tabID, String targetColumn){
        String args[] ={tableName, category, tabID};
        String statement =String.format( SELECT_ALL_FROM_WHERE_AND, args);
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        ResultSet resultSet = dbc.executeStatement(connection, statement);
        HashMap<String,String> result = new HashMap<>();
        result.put(CATEGORY,category);


        if (category.equalsIgnoreCase(RELEASE_BUILD)) {
            result.putAll(getReleseBuild(dbc, connection, tableName, tabID));
        }
        else {
            try {

                while (resultSet.next()) {
                    result.put(resultSet.getString(BUILD), resultSet.getString(targetColumn));
                }
                connection.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public  HashMap<String, String> getReleseBuild(DataBaseConnection dbc, Connection connection, String tableName, String tabID ){
        HashMap<String, String> result = new HashMap<>();
        String statement =  String.format(SELECT_RELEASE_BUILD, tableName, tabID);
        ResultSet resultSet = dbc.executeStatement(connection, statement);

        try {
            while (resultSet.next()) {
                String releaseBuild = resultSet.getString("release_build");
                String build = resultSet.getString("build");
                if (releaseBuild!=null){
                    result.put(build, releaseBuild);
                }
                else {
                    result.put(build, "");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList <String> getCategories(String tableName, String tabId){
        SelectSQLQuery selectQuery = new SelectSQLQuery();
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        ResultSet rs = dbc.executeStatement(connection, selectQuery.selectCategories(tableName, tabId));
        ArrayList<String> categories = new ArrayList<>();
        categories.add(RELEASE_BUILD);
        try {
            while (rs.next()){
                categories.add(rs.getString(CATEGORY));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public ArrayList <String> getBuilds(String tabID){
        SelectSQLQuery selectQuery = new SelectSQLQuery();
        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();
        ResultSet rs = dbc.executeStatement(connection, selectQuery. selectBuilds(tabID));
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

    public ArrayList<ObjectInfo> reloadFailedObject(String tableName, String tabId){
        ArrayList <ObjectInfo> failedObjects = new ArrayList<>();
        String args[] ={tableName, tabId};
        String statement =String.format(  SELECT_ALL_FROM_FAILED_OBJECT, args);

        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

        ResultSet resultSet = dbc.executeStatement(connection, statement);

        try {
            while (resultSet.next()) {
                failedObjects.add(new ObjectInfo(resultSet.getInt(TESTLIST_NUMBER), resultSet.getString(OBJECTTYPE), resultSet.getString(OBJECTNAME),
                                                resultSet.getString(REPORT), resultSet.getBoolean(DELETE_OBJECT),
                                               resultSet.getInt(BUILD), resultSet.getString(TAB),  resultSet.getString(COMMENT)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return failedObjects;
    }

}

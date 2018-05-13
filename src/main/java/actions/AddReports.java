package actions;

import javafx.concurrent.Task;
import report.Report;
import settings.Settings;
import sqlquery.DataBaseConnection;
import sqlquery.Update_SQL_Query;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.CSV;

public class AddReports extends Task {
    private Settings settings;
    private File file;

    private int size;
    private int i;
    private int numberFiles=0;

    public AddReports(Settings settings, File file){
        this.settings = settings;
        this.file = file;

    }
    public void addReports(File file) {
        ArrayList<File> reportList = new ArrayList<>(getReportList(file));
        size=reportList.size();

        DataBaseConnection dbc = new DataBaseConnection(BASE, BASE_LOGIN, BASE_PASSWORD);
        Connection connection = dbc.connectToDatabase();

       i = 0;
        while (i < reportList.size()) {
            Report report = new Report(reportList.get(i).getPath(), settings);
            if (report.getTabId() != null) {
                Update_SQL_Query query = new Update_SQL_Query(report);
                int size = query.getSqlStatement().size();
                int j = 0;
                while (j < size) {
                    dbc.executeUpdateStatement(connection, query.getSqlStatement().get(j));
                    j++;

                }
            }
            updateProgress(i + 1, size);
            updateMessage("Uploaded " + i + " reports of " + numberFiles);
            i++;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<File> getReportList(File directory){
        ArrayList<File> fileList = new ArrayList<>();
        File[] fileArray = directory.listFiles();
        int i=0;
        while (fileArray.length>i){
            if(fileArray[i].isDirectory()){
                fileList.addAll(getReportList(fileArray[i]));
            }
            else if (fileArray[i].getName().toLowerCase().endsWith(CSV)){
            fileList.add(fileArray[i]);
            updateMessage((numberFiles++)+" Reports was found");
            }
            i++;
        }
        return fileList;
    }


    @Override
    public Object call() throws Exception {
        addReports(file);
        return null;
    }
}

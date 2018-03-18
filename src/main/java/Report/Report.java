package Report;

import java.util.ArrayList;

import static constants.StringsConstant.*;
import static constants.Messages.*;


public class Report {
    public Report(String path){
        stringsReport.addAll(FileReader.readFile(path));
        buildNumber=ananlyzeBuildNumber(stringsReport);
        reportType = ananlyzeReportType(stringsReport);
        generalStatistic = new GeneralStatistic(stringsReport);
        statisticByCategories = new StatisticByCategories(stringsReport);
        failedObjects = new FailedObjects(stringsReport);
        statisticBySource = new StatisticBySource(stringsReport);
        int i=0;
    }

    private ArrayList<String> stringsReport = new ArrayList<String>();
    private String reportType;
    private int buildNumber;
    private GeneralStatistic generalStatistic;
    private StatisticByCategories statisticByCategories;
    private FailedObjects failedObjects;
    private StatisticBySource statisticBySource;

    public int ananlyzeBuildNumber(ArrayList<String> reportStrings){
        int buildNumber=0;
        String temp;
        temp=reportStrings.stream().filter((p)->(p.contains(BUILD_NUMBER))).findFirst().get();
        temp=temp.substring(temp.indexOf(COMA)+1).trim();
        buildNumber=Integer.parseInt(temp);
        return buildNumber;
    }

    public String ananlyzeReportType(ArrayList<String> reportStrings){
        String reportType=null;

        if(reportStrings.stream().anyMatch((p)->(p.contains(APPLY_VERIFICATION_FILE))))
        {reportType=APPLY;}
        else if(reportStrings.stream().anyMatch((p)->(p.contains(CONVERSION_VERIFICATION_FILE))))
        {reportType=CONVERSION;}
        else if(reportStrings.stream().anyMatch((p)->(p.contains(ERROR_VERIFICATION_FILE))))
        {reportType=ACTION_ITEMS;}
        else if(reportStrings.stream().anyMatch((p)->(p.contains(ACTION_ITEMS_VERIFICATION_FILE)||p.contains(AI_VERIFICATION_FILE))))
        {reportType=ERROR;}
        else
            {System.out.println(CAN_NOT_FIND_REPORT_TYPE);
            reportType=UNKNOWN;}

        return reportType;
    }

    public int getTotalItems(){
        return generalStatistic.getTotalItems();
    }
    public int getTotalPassed(){
        return generalStatistic.getPassed();
    }
    public int getTotalFailed(){
        return generalStatistic.getFailed();
    }
    public int getBuildNumber(){return buildNumber;}
    public ArrayList<Category> getStatisticByCategories() {
        return statisticByCategories.getArrayStatistic();
    }
    public ArrayList<Category> getStatisticBySource() {return statisticBySource.getStatisticBySource(); }
    public String getPair(){return  "Oracle-PostgreSQL";}
    public String getReportType(){return reportType;}
}

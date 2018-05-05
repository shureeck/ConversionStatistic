package report;

import gui.TabModel;
import settings.Settings;

import java.io.File;
import java.util.ArrayList;

import static constants.Messages.CAN_NOT_FIND_REPORT_TYPE;
import static constants.StringsConstant.*;


public class Report {
    public Report(String path, Settings settings){
        this.settings = settings;
        stringsReport.addAll(FileReader.readFile(path));
        buildNumber=ananlyzeBuildNumber(stringsReport);
        reportType = ananlyzeReportType(stringsReport);
        generalStatistic = new GeneralStatistic(stringsReport);
        statisticByCategories = new StatisticByCategories(stringsReport);
        failedObjects = new FailedObjects(stringsReport);
        statisticBySource = new StatisticBySource(stringsReport);
        this.pair = analyzePair(path);
    }

    private Settings settings;
    private ArrayList<String> stringsReport = new ArrayList<String>();
    private String reportType;
    private int buildNumber;
    private GeneralStatistic generalStatistic;
    private StatisticByCategories statisticByCategories;
    private FailedObjects failedObjects;
    private StatisticBySource statisticBySource;
    private String pair;

    public int ananlyzeBuildNumber(ArrayList<String> reportStrings){
        int buildNumber=0;
        String temp;
        temp=reportStrings.stream().filter((p)->(p.contains(BUILD_NUMBER))).findFirst().get();
        temp=temp.substring(temp.indexOf(COMA)+1).trim();
        buildNumber=Integer.parseInt(temp);
        return buildNumber;
    }

    public String analyzePair(String path){
        String pair=null;
        String reportFolder=new File(path).getParentFile().getName();
        TabModel tempTabModel = settings.getTabModelsList().
                stream().filter((p)->p.getReportFolder().equalsIgnoreCase(reportFolder))
                .findFirst().get();
        pair = tempTabModel.getPair();
        return pair;
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

    public int getBuildNumber(){return buildNumber;}
    public ArrayList<Category> getGeneralStatistic() {return generalStatistic.getStatisticGeneral(); }
    public ArrayList<Category> getStatisticByCategories() {
        return statisticByCategories.getArrayStatistic();
    }
    public ArrayList<Category> getStatisticBySource() {return statisticBySource.getStatisticBySource(); }
    public String getPair() {return pair; }
    public String getReportType(){return reportType;}
}

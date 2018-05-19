package sqlquery;

import report.Report;

import java.util.ArrayList;

import static constants.DatabaseStrings.*;
import static constants.StringsConstant.*;

public class Update_SQL_Query {
    private ArrayList<String> sqlStatement = new ArrayList<>();
    private String type;

    public Update_SQL_Query(Report report){
        sqlStatement.addAll(buildStatement(report));
    }

    private ArrayList<String> buildStatement (Report report){
        ArrayList<String> result = new ArrayList<>();
        String type =report.getReportType();

            if(type.equalsIgnoreCase(APPLY)) {result.addAll(buildApplyStatement(report));}
            else if (type.equalsIgnoreCase(CONVERSION)){ result.addAll(buildConversionStatement(report));}
            else if (type.equalsIgnoreCase(ERROR)) {result.addAll(buildErrorStatement(report));}
            else if (type.equalsIgnoreCase(ACTION_ITEMS)) {result.addAll(buildActionItemsStatement(report));}
        return result;
    }

    private ArrayList<String> buildApplyStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        int i=0;
        int size=report.getGeneralStatistic().size();
        while (i<size) {

            String values[] ={String.valueOf(report.getBuildNumber()), report.getGeneralStatistic().get(i).getName(),
                    String.valueOf(report.getGeneralStatistic().get(i).getCount()), report.getPair(), report.getFolder()};
            String valuesGeneralStatistic = String.format(VALUES_STATISTIC, values);

            String args []={TABLE_APPLY_GENERAL_STATISTIC, FIELDS_APPLY_GENERAL_STATISTIC, valuesGeneralStatistic};
            result.add(String.format(REPLACE_INTO, args));
            i++;
        }

       i=0;
         size=report.getStatisticByCategories().size();
        while (i<size) {
            String values[]={String.valueOf(report.getBuildNumber()), report.getStatisticByCategories().get(i).getName(),
                    String.valueOf(report.getStatisticByCategories().get(i).getPassed()),
                    String.valueOf(report.getStatisticByCategories().get(i).getFailed()), report.getPair(),report.getFolder()};
            String valuesStatisticByCategories = String.format(VALUES_STATISTIC_BY_CATEGORIES, values);

            String args[] = {TABLE_APPLY_BY_CATEGORIES, FIELDS_APPLY_BY_CATEGORIES, valuesStatisticByCategories};
            result.add(String. format(REPLACE_INTO, args));
            i++;
        }

        i=0;
        size=report.getStatisticBySource().size();
        while (i<size) {
            String values[] ={String.valueOf(report.getBuildNumber()),report.getStatisticBySource().get(i).getName(),
                    String.valueOf(report.getStatisticBySource().get(i).getCount()), report.getPair(), report.getFolder()};
            String valuesStatisticBySource =String.format(VALUES_STATISTIC , values);

            String arggs[]= {TABLE_STATISTIC_BY_SOURCE, FIELDS_STATISTIC_BY_SOURCE, valuesStatisticBySource};
            result.add(String.format(REPLACE_INTO, arggs));
            i++;
        }

        i =0;
        size = report.getFailedObjects().size();
        while (size>i){
            String category = report.getFailedObjects().get(i).getCategory();
            String objectName = report.getFailedObjects().get(i).getName();
            String testlistNumber =String.valueOf(report.getFailedObjects().get(i).getTestListNumber());
            String errorReport = report.getFailedObjects().get(i).getReport();
            String [] values ={testlistNumber, category, objectName, String.valueOf(report.getBuildNumber()), report.getFolder(), report.getPair(), FALSE, errorReport};
            String valuesFaileObjects = String.format(VALUES_FAILED_OBJECTS, values);

            String[] args ={APPLY_FAILED_OBJECTS, FIELDS_APPLY_FAILED_OBJECTS, valuesFaileObjects};
            result.add(String.format(REPLACE_INTO, args));
            i++;
        }
        return result;
    }

    private ArrayList<String> buildConversionStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        int i=0;
        int size=report.getGeneralStatistic().size();
        while (i<size) {
            String values[] = {String.valueOf(report.getBuildNumber()), report.getGeneralStatistic().get(i).getName(),
                    String.valueOf(report.getGeneralStatistic().get(i).getCount()), report.getPair(), report.getFolder()};
            String valuesGeneralStatistic =  String.format(VALUES_STATISTIC, values);

            String args[] ={TABLE_CONVERSION_GENERAL_STATISTIC,FIELDS_APPLY_GENERAL_STATISTIC, valuesGeneralStatistic};
            result.add(String.format(REPLACE_INTO, args));
            i++;
        }

        i=0;
        size=report.getStatisticByCategories().size();
        while (i<size) {
            String values[] ={String.valueOf(report.getBuildNumber()), report.getStatisticByCategories().get(i).getName(),
                    String.valueOf(report.getStatisticByCategories().get(i).getPassed()),
                    String.valueOf(report.getStatisticByCategories().get(i).getFailed()), report.getPair(), report.getFolder()};
            String valuesStatisticByCategories = String.format(VALUES_STATISTIC_BY_CATEGORIES, values);

            String args[] = {TABLE_CONVERSION_BY_CATEGORIES, FIELDS_APPLY_BY_CATEGORIES, valuesStatisticByCategories};
            result.add(String.format(REPLACE_INTO, args));
            i++;
        }
        return result;
    }

    private ArrayList<String> buildErrorStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        int i=0;
        int size=report.getStatisticByCategories().size();
        while (i<size) {
            String values[] = { String.valueOf(report.getBuildNumber()), report.getStatisticByCategories().get(i).getName(),
                    String.valueOf(report.getStatisticByCategories().get(i).getFailed()), report.getPair(), report.getFolder()};
            String valuesStatisticByCategories = String.format(VALUES_STATISTIC, values);

            String args[]= {TABLE_ERRORS_BY_CATEGORIES, FIELDS_ERRORS_BY_CATEGORIES, valuesStatisticByCategories};
            result.add(String.format(REPLACE_INTO, args));
            i++;
        }
        return result;
    }

    private ArrayList<String> buildActionItemsStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        int i=0;
        int size=report.getGeneralStatistic().size();
        String valuesGeneralStatistic="";
        while (i<size) {
            String values[] ={String.valueOf(report.getBuildNumber()), report.getGeneralStatistic().get(i).getName(),
                    String.valueOf(report.getGeneralStatistic().get(i).getCount()), report.getPair(), report.getFolder()};
            valuesGeneralStatistic = String.format( VALUES_STATISTIC, values);

            String args[] ={TABLE_ACTION_ITEMS_GENERAL_STATISTIC, FIELDS_APPLY_GENERAL_STATISTIC, valuesGeneralStatistic};
            result.add(String.format(REPLACE_INTO, args));
            i++;
        }
    return result;
    }

    public String getCallStatement(Report report){
        String callStetment = String.format(CALL_CHANGE_TO_TRUE, APPLY_FAILED_OBJECTS, report.getFolder() );
        return callStetment;

    }

    public ArrayList<String> getSqlStatement() {
        return sqlStatement;
    }
}

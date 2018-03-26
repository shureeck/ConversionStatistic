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
        String valuesGeneralStatistic = BRACKET_O +String.valueOf(report.getBuildNumber())+
                COMA+String.valueOf(report.getTotalItems())+
                COMA+String.valueOf(report.getTotalPassed())+
                COMA+String.valueOf(report.getTotalFailed())+
                COMA+ QUOTE + report.getPair()  + QUOTE + BRACKET_C;
        result.add(REPLACE_INTO +TABLE_APPLY_GENERAL_STATISTIC+FIELDS_APPLY_GENERAL_STATISTIC+VALUES+valuesGeneralStatistic);

        int i=0;
        int size=report.getStatisticByCategories().size();
        while (i<size) {
            String valuesStatisticByCategories = BRACKET_O + String.valueOf(report.getBuildNumber()) +
                    COMA + QUOTE + report.getStatisticByCategories().get(i).getName() + QUOTE +
                    COMA + String.valueOf(report.getStatisticByCategories().get(i).getPassed()) +
                    COMA + String.valueOf(report.getStatisticByCategories().get(i).getFailed()) +
                    COMA + QUOTE + report.getPair()  + QUOTE + BRACKET_C;
            result.add(REPLACE_INTO + TABLE_APPLY_BY_CATEGORIES + FIELDS_APPLY_BY_CATEGORIES + VALUES + valuesStatisticByCategories);
            i++;
        }
        i=0;
        size=report.getStatisticBySource().size();
        while (i<size) {
            String valuesStatisticBySource = BRACKET_O + String.valueOf(report.getBuildNumber()) +
                    COMA + QUOTE + report.getStatisticBySource().get(i).getName() + QUOTE +
                    COMA + String.valueOf(report.getStatisticBySource().get(i).getCount()) +
                    COMA + QUOTE + report.getPair() + QUOTE + BRACKET_C;
            result.add(REPLACE_INTO + TABLE_STATISTIC_BY_SOURCE +FIELDS_STATISTIC_BY_SOURCE+VALUES+valuesStatisticBySource);
            i++;
        }
        return result;
    }

    private ArrayList<String> buildConversionStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        String valuesGeneralStatistic = BRACKET_O +String.valueOf(report.getBuildNumber())+
                COMA+String.valueOf(report.getTotalItems())+
                COMA+String.valueOf(report.getTotalPassed())+
                COMA+String.valueOf(report.getTotalFailed())+
                COMA+ QUOTE + report.getPair()  + QUOTE + BRACKET_C;
        result.add(REPLACE_INTO +TABLE_CONVERSION_GENERAL_STATISTIC+FIELDS_APPLY_GENERAL_STATISTIC+VALUES+valuesGeneralStatistic);

        int i=0;
        int size=report.getStatisticByCategories().size();
        while (i<size) {
            String valuesStatisticByCategories = BRACKET_O + String.valueOf(report.getBuildNumber()) +
                    COMA + QUOTE + report.getStatisticByCategories().get(i).getName() + QUOTE +
                    COMA + String.valueOf(report.getStatisticByCategories().get(i).getPassed()) +
                    COMA + String.valueOf(report.getStatisticByCategories().get(i).getFailed()) +
                    COMA + QUOTE + report.getPair()  + QUOTE + BRACKET_C;
            result.add(REPLACE_INTO + TABLE_CONVERSION_BY_CATEGORIES + FIELDS_APPLY_BY_CATEGORIES + VALUES + valuesStatisticByCategories);
            i++;
        }
        return result;
    }

    private ArrayList<String> buildErrorStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        int i=0;
        int size=report.getStatisticByCategories().size();
        while (i<size) {
            String valuesStatisticByCategories = BRACKET_O + String.valueOf(report.getBuildNumber()) +
                    COMA + QUOTE + report.getStatisticByCategories().get(i).getName() + QUOTE +
                    COMA + String.valueOf(report.getStatisticByCategories().get(i).getFailed()) +
                    COMA + QUOTE + report.getPair()  + QUOTE + BRACKET_C;
            result.add(REPLACE_INTO + TABLE_ERRORS_BY_CATEGORIES + FIELDS_ERRORS_BY_CATEGORIES + VALUES + valuesStatisticByCategories);
            i++;
        }
        return result;
    }

    private ArrayList<String> buildActionItemsStatement(Report report){
        ArrayList<String> result = new ArrayList<>();
        String valuesGeneralStatistic = BRACKET_O +String.valueOf(report.getBuildNumber())+
                COMA+String.valueOf(report.getTotalItems())+
                COMA+String.valueOf(report.getTotalPassed())+
                COMA+String.valueOf(report.getTotalFailed())+
                COMA+ QUOTE + report.getPair()  + QUOTE + BRACKET_C;
        result.add(REPLACE_INTO +TABLE_ACTION_ITEMS_GENERAL_STATISTIC+FIELDS_APPLY_GENERAL_STATISTIC+VALUES+valuesGeneralStatistic);
    return result;
    }

    public ArrayList<String> getSqlStatement() {
        return sqlStatement;
    }
}

package report;

import java.util.ArrayList;

import static constants.StringsConstant.COMA;
import static constants.StringsConstant.*;

public class GeneralStatistic {
    private ArrayList<Category> statisticGeneral =new ArrayList();
    private String[] strStatisticGeneral;

    public GeneralStatistic(ArrayList<String> report){
        strStatisticGeneral = findStatisticGeneral(report);
        statisticGeneral.addAll(parseGeneralStatistic(strStatisticGeneral));
    }

    public String[] findStatisticGeneral(ArrayList<String> report){
       ArrayList<String> stringsStatisticByCategory=new ArrayList<>();

        int i=0;

        while (i<report.size()){
            if (report.get(i).toLowerCase().trim().contains(GENERAL_STATISTIC)){
                i=i+1;
                while (!report.get(i).trim().equalsIgnoreCase("")){
                    stringsStatisticByCategory.add(report.get(i));
                    i++;
                }
                break;
            }
            i++;
        }
        String[] result = stringsStatisticByCategory.toArray(new String[stringsStatisticByCategory.size()]);
        return result;
    }

    public ArrayList<Category> parseGeneralStatistic(String[] statisticByCategory){
        ArrayList<Category> result = new ArrayList<>();
        int i=0;
        while (i<statisticByCategory.length){
            String temp[]=statisticByCategory[i].split(COMA);
            Category category = new Category(temp[0].trim(),
                            Integer.parseInt(temp[1].replace(";","").trim()));
            result.add(category);
            i++;
        }
        return result;
    }

    public ArrayList<Category> getStatisticGeneral() {return statisticGeneral;}
    public String[] getStringsStatisticBySource() {return strStatisticGeneral;}
}

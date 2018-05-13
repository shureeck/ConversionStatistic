package report;

import java.util.ArrayList;

import static constants.StringsConstant.*;

public class StatisticBySource {
    private ArrayList<Category> statisticBySource=new ArrayList();
    private String[] strStatisticBySource;

    public StatisticBySource (ArrayList<String> report){
        strStatisticBySource = findStatisticBySource(report);
        statisticBySource.addAll(parseStatisticBycategory(strStatisticBySource));
    }

    public String[] findStatisticBySource (ArrayList<String> report){
       ArrayList<String> stringsStatisticBySource=new ArrayList<>();

        int i=0;
        while (i<report.size()){
            if (report.get(i).toLowerCase().trim().contains(STATISTIC_BY_SOURCE)){
                i=i+2;
                while (!report.get(i).trim().equalsIgnoreCase("")){
                    stringsStatisticBySource.add(report.get(i));
                    i++;
                }
                break;
            }
            i++;
        }
        String[] result = stringsStatisticBySource.toArray(new String[stringsStatisticBySource.size()]);
        return result;
    }

    public ArrayList<Category> parseStatisticBycategory(String[] statisticByCategory){
        ArrayList<Category> result = new ArrayList<>();
        int i=0;
        while (i<statisticByCategory.length) {
            String temp[] =new String[2];
            if (statisticByCategory[i].matches("^\\w+[,][ ]?\\d+$")){
               temp[0] = statisticByCategory[i].split(COMA)[0];
               temp[1] = statisticByCategory[i].split(COMA)[1];
        }
        else
           {
                temp[0]=statisticByCategory[i].split(COMA)[0];
                temp[1] ="0";
            }
            Category category = new Category(temp[0].trim(), Integer.parseInt(temp[1].trim()));
            result.add(category);
            i++;
        }
        return result;
    }

    public ArrayList<Category> getStatisticBySource() {return statisticBySource;}
    public String[] getStringsStatisticBySource() {return strStatisticBySource;}
}

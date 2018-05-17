package report;

import java.util.ArrayList;

import static constants.StringsConstant.*;

public class StatisticByCategories {
    public StatisticByCategories(ArrayList<String> report){
        stringsStatisitc=findStatisticByCategories(report);
        arrayStatistic.addAll(parseStatisticByCategories(stringsStatisitc));
    }

    private String[] stringsStatisitc;
    private ArrayList<Category> arrayStatistic=new ArrayList<>();

    public String[] findStatisticByCategories(ArrayList<String> report){
        int i = 0;
        ArrayList<String> arrayStatisiticByCategories = new ArrayList<>();

        while (i<report.size()){
            if (report.get(i).toLowerCase().trim().contains(STATISTIC_BY_CATEGORIES)){
                i++;
                while (report.size()>i && !report.get(i).trim().equalsIgnoreCase("")){
                    arrayStatisiticByCategories.add(report.get(i));
                    i++;
                }
                break;
            }
            i++;
        }//while
        String[] result = new  String[arrayStatisiticByCategories.size()];
        return arrayStatisiticByCategories.toArray(result);
    }

    public ArrayList parseStatisticByCategories(String[]statisticArray){
        int i=0;
        ArrayList<Category> result = new ArrayList<>();
        while (statisticArray!=null && i<statisticArray.length){
            if(statisticArray[i].matches(REGEXP_STATISTIC_CATEGORIES)){
                String[] temp = statisticArray[i].split(COMA);
                Category category = new Category(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                result.add(category);
            }
            i++;
        }
        return result;
    }

    public ArrayList<Category> getArrayStatistic() {
        return arrayStatistic;
    }
}

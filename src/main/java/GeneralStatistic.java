import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static constants.StringsConstant.*;

public class GeneralStatistic {
    public GeneralStatistic (ArrayList<String> report){
        generalStatisticStrings=findGeneralStatistic(report);
        generalStatistic=new HashMap<>(parseGeneralStatistic(generalStatisticStrings));
    }

    private String[] generalStatisticStrings;
    private Map<String,Integer> generalStatistic;

    public String[] findGeneralStatistic(ArrayList<String> report){
        int i = 0;
        String[] arrayGeneralStatisitic = new String[3];

        while (i<report.size()){
            if (report.get(i).toLowerCase().trim().contains(GENERAL_STATISTIC)){
                arrayGeneralStatisitic[0]=report.get(i+1);
                arrayGeneralStatisitic[1]=report.get(i+2);
                arrayGeneralStatisitic[2]=report.get(i+3);
                break;
            }
            i++;
        }//while
        return arrayGeneralStatisitic;
    }

    public Map<String,Integer> parseGeneralStatistic(String[]generalStatistic){
        int i=0;
        Map<String,Integer> result = new HashMap<>();

        while (i<generalStatistic.length){
            String[] temp = generalStatistic[i].split(REGEXP_SPLIT_GENERAL_STAT);
            String key=null;
            int value=0;

            int j=0;
            while (j<temp.length){
                if (temp[j].matches(REGEXP_KEY_STATISITC)){
                key=temp[j].trim();
                }
                else if(temp[j].matches(REGEXP_NUMBERS)){
                    value=Integer.parseInt(temp[j]);
                }
                j++;
            }

            result.put(key,value);
            i++;
        }
        return result;
    }
    public int getTotalItems(){
        int result = generalStatistic.get(TOTAL_ITEMS);
        return result;
    }

    public int getPassed(){
        int result = generalStatistic.get(PASSED);
        return result;
    }
    public int getFailed(){
        int result = generalStatistic.get(FAILED);
        return result;
    }

    public Map<String, Integer> getGeneralStatistic(){
        return generalStatistic;
    }


}

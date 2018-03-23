package report;

import java.util.ArrayList;

import static constants.StringsConstant.*;

public class FailedObjects {
    public FailedObjects(ArrayList<String> report){
        stringsFailedobjects=findFailedObjects(report);
        failedObjejcts.addAll(parseObjectinfo(stringsFailedobjects));
    }
    private String[] stringsFailedobjects;
    private ArrayList<ObjectInfo>failedObjejcts= new ArrayList<>();

    public String[] findFailedObjects(ArrayList<String> report){
        ArrayList<String>result = new ArrayList<>();

        int i=0;
        while (i<report.size()){
            if (report.get(i).toLowerCase().trim().contains(OBJECT_LIST)){
              i++;
              while (!report.get(i).equalsIgnoreCase("")){
                  if (report.get(i).toLowerCase().contains(FAILED.toLowerCase())){
                      result.add(report.get(i));
                  }
                  i++;
              }
                break;
            }
            i++;
        }//while
        return result.toArray(new String[result.size()]);
    }

    public ArrayList<ObjectInfo> parseObjectinfo(String[] failedObjects){
        ArrayList<ObjectInfo> result = new ArrayList<>();
        int i=0;
        while (i<failedObjects.length){
            String[] temp = failedObjects[i].split(COMA,6);

            int testListNumber;
            if(temp[0].matches(REGEXP_NUMBERS)){
                testListNumber=Integer.parseInt(temp[0].trim());
            }
            else testListNumber=0;

            ObjectInfo object = new ObjectInfo(testListNumber,temp[1],temp[2]);
            result.add(object);
            i++;
        }
        return result;
    }

    public ArrayList<ObjectInfo> getFailedObjects(){
        return failedObjejcts;
    }

}

package sqlquery;

import report.ObjectInfo;

import static constants.DatabaseStrings.*;

public class SelectSQLQuery {

    public String selectBuilds(String tabId){
        String args[]= {tabId, tabId, tabId, tabId};
        String result = String.format(SELECT_BUILDS, args );
        return  result;
    }

    public String selectCategories(String tableName, String tabId){
        String args[]={tableName, tabId};
        return  String.format(SELECT_CATEGORIES, args);
    }

    public String selectObjectInfo(ObjectInfo objectInfo){
        String query=null;
        String objectType = objectInfo.getCategory();
        String objectName = objectInfo.getName();

        return query;
    }

}

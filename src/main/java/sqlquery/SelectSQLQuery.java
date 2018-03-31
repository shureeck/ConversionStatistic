package sqlquery;

import static constants.DatabaseStrings.*;

public class SelectSQLQuery {

    public String selectBuilds(String tableName){
        return  SELECT_BUILDS+ tableName;
    }

    public String selectCategorites(String tableName){
        return  SELECT_CATEGOTIES+ tableName;
    }
}

package constants;

public class DatabaseStrings {
    public static final String BASE = "jdbc:mysql://localhost/testdb";
    public static final String BASE_LOGIN = "gbuser";
    public static final String BASE_PASSWORD = "gbuser";

    public static final String TABLE_APPLY_BY_CATEGORIES = " apply_by_categories ";
    public static final String TABLE_APPLY_GENERAL_STATISTIC = " apply_general_statistic ";
    public static final String TABLE_STATISTIC_BY_SOURCE = " statistic_by_source ";
    public static final String TABLE_ACTION_ITEMS_GENERAL_STATISTIC = " action_items_general_statistic ";
    public static final String TABLE_CONVERSION_GENERAL_STATISTIC = " conversion_general_statistic ";
    public static final String TABLE_CONVERSION_BY_CATEGORIES = " conversion_by_categories ";
    public static final String TABLE_ERRORS_BY_CATEGORIES =  " errors_by_categories ";
    public static final String TABLE_APPLY_FAILED_OBJECTS = "apply_failed_objects";
    public static final String TABLE_ACTION_ITEMS_FAILED_OBJECTS = "actionitems_failed_objects";
    public static final String TABLE_CONVERSION_FAILED_OBJECTS = "conversion_failed_objects";
    public static final String TABLE_ERRORS_FAILED_OBJECTS = "error_failed_objects";

    public static final String VALUES_STATISTIC = "%s, \"%s\", %s,\"%s\",\"%s\"";
    public static final String VALUES_STATISTIC_BY_CATEGORIES = "%s, \"%s\", %s, %s , \"%s\",\"%s\"";
    public static final String VALUES_FAILED_OBJECTS = "%s, \"%s\", \"%s\", %s, \"%s\", \"%s\", %s, \"%s\"";

    public static final String FIELDS_APPLY_GENERAL_STATISTIC = "(build, category, count, pair, tab)";
    public static final String FIELDS_APPLY_BY_CATEGORIES = "(build, category, passed, failed, pair, tab)";
    public static final String FIELDS_ERRORS_BY_CATEGORIES = "(build, category, failed, pair, tab)";
    public static final String FIELDS_STATISTIC_BY_SOURCE = "(build, category, count, pair, tab)";
    public static final String FIELDS_APPLY_FAILED_OBJECTS = "(test_list_number, objecttype, objectname, build, tab, pair, delete_object, report)";


    public static final String REPLACE_INTO = "REPLACE INTO %s %s VALUES (%s)" ;
    public static final String INSERT_INTO = "INSERT INTO %s %s VALUES %s" ;


        public static final String SELECT_BUILDS = "SELECT build FROM apply_general_statistic WHERE tab =\"%s\" UNION \n" +
                "SELECT build FROM conversion_general_statistic WHERE  tab =\"%s\" UNION\n" +
                "SELECT build FROM errors_by_categories WHERE  tab =\"%s\" UNION\n" +
                "SELECT build FROM action_items_general_statistic WHERE  tab =\"%s\" ORDER BY build DESC";
    public static final String SELECT_CATEGORIES = "SELECT DISTINCT category FROM %s WHERE tab = \"%s\"";
    public static final String SELECT_ALL_FROM_WHERE_AND = "SELECT * FROM %s WHERE category =\"%s\" AND tab =\"%s\" ";
    public static final String CALL_CHANGE_TO_TRUE = "CALL changeToTrue(\"%s\", \"%s\")";
    public static final String SELECT_ALL_FROM_FAILED_OBJECT = "SELECT * FROM %s WHERE tab =\"%s\"";
    public static final String SELECT_OBJECT_INFO = "SELECT * FROM %s WHERE objectname = \"%s\" AND objecttype = \"%s\" AND tab = \"%s\";";
    public static final String UPDATE_COMMENT = "UPDATE %s SET comment = \"%s\" WHERE objecttype =\"%s\" AND objectname=\"%s\" AND tab=\"%s\";";
    public static final String ADD_RELEASE_BUILD = "UPDATE %s SET release_build = %s WHERE build = %s;";
    public static final String SELECT_RELEASE_BUILD = "SELECT DISTINCT release_build, build FROM %s WHERE tab =\"%s\"";

}

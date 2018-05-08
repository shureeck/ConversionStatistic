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

    public static final String FIELDS_APPLY_GENERAL_STATISTIC = "(build, category, count, pair, tab)";
    public static final String FIELDS_APPLY_BY_CATEGORIES = "(build, category, passed, failed, pair, tab)";
    public static final String FIELDS_ERRORS_BY_CATEGORIES = "(build, category, failed, pair, tab)";
    public static final String FIELDS_STATISTIC_BY_SOURCE = "(build, category, count, pair, tab)";

    public static final String REPLACE_INTO = "REPLACE INTO " ;
   // public static final String INSERT_INTO = "INSERT INTO " ;
    public static final String VALUES=" VALUES ";
        public static final String SELECT_BUILDS = "SELECT build FROM apply_general_statistic WHERE tab =\"%s\" UNION \n" +
                "SELECT build FROM conversion_general_statistic WHERE  tab =\"%s\" UNION\n" +
                "SELECT build FROM errors_by_categories WHERE  tab =\"%s\" UNION\n" +
                "SELECT build FROM action_items_general_statistic WHERE  tab =\"%s\" ";
    public static final String SELECT_CATEGORIES = "SELECT DISTINCT category FROM %s WHERE tab = \"%s\"";
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE category=\"";
    public static final String SELECT_ALL_FROM_WHERE_AND = "SELECT * FROM %s WHERE category =\"%s\" AND tab =\"%s\" " ;


}

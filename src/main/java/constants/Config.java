package constants;

public class Config {
    private static String tabName;
    private static String reportFolder;
    private static String tabID;

    public static String getReportFolder() {
        return reportFolder;
    }

    public static String getTabName() {
        return tabName;
    }

    public static void setReportFolder(String reportFolder) {
        Config.reportFolder = reportFolder;
    }

    public static void setTabID(String tabID) {
        Config.tabID = tabID;
    }

    public static String getTabID() {
        return tabID;
    }

    public static void setTabName(String tabName) {
        Config.tabName = tabName;
    }
}

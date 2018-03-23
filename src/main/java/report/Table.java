package report;

public class Table {
    private String category;
    private String value;
    private String build;
    private String releaseBuild;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getBuild() {
        return build;
    }

    public void setReleaseBuild(String releaseBuild) {
        this.releaseBuild = releaseBuild;
    }

    public String getReleaseBuild() {
        return releaseBuild;
    }
}
